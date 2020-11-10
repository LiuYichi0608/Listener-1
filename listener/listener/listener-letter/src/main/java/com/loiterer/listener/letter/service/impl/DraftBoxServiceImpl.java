package com.loiterer.listener.letter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loiterer.listener.letter.mapper.EnvelopeStyleMapper;
import com.loiterer.listener.letter.model.entity.DraftBox;
import com.loiterer.listener.letter.mapper.DraftBoxMapper;
import com.loiterer.listener.letter.model.entity.EnvelopeStyle;
import com.loiterer.listener.letter.model.vo.DraftBoxVO;
import com.loiterer.listener.letter.model.vo.ReplyDraftVO;
import com.loiterer.listener.letter.service.DraftBoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loiterer.listener.letter.util.UserUtil;
import com.loiterer.listener.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
@Slf4j
@Service
public class DraftBoxServiceImpl extends ServiceImpl<DraftBoxMapper, DraftBox> implements DraftBoxService {

    /**
     * 与草稿对应的mapper
     */
    private final DraftBoxMapper draftBoxMapper;

    /**
     * 获取信件样式url的mapper
     */
    private final EnvelopeStyleMapper envelopeStyleMapper;

    /**
     * 需要用到userUtil来获取用户信息
     */
    private final UserUtil userUtil;

    @Autowired
    public DraftBoxServiceImpl(
            DraftBoxMapper draftBoxMapper,
            EnvelopeStyleMapper envelopeStyleMapper,
            UserUtil userUtil
    ) {
        this.draftBoxMapper = draftBoxMapper;
        this.envelopeStyleMapper = envelopeStyleMapper;
        this.userUtil = userUtil;
    }

    @Override
    public boolean addDraft(DraftBoxVO draftBoxVO, String openid) {

        // 1.获取user的id和nick_name
        User writerUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.组装要存到数据库的草稿数据
        // 2.1 先保证不会存入不需要的数据
        draftBoxVO.setIsReply(0);
        // 2.2 将用户信息和草稿信息组装起来
        DraftBox draftBox = new DraftBox();
        BeanUtils.copyProperties(draftBoxVO, draftBox);
        draftBox.setWriterId(writerUser.getId());
        draftBox.setWriterNickName(writerUser.getNickName());

        // 3.保存草稿
        int insert = draftBoxMapper.insert(draftBox);

        return insert != 0;
    }

    @Override
    public List<DraftBoxVO> getAllDrafts(String openid) {

        // 1.获取user的id
        User writerUser = userUtil.getUserInfo(openid, "id");

        // 2.通过用户id查找用户所有的草稿信息
        // 2.1 封装查询条件
        QueryWrapper<DraftBox> draftBoxQueryWrapper = new QueryWrapper<>();
        draftBoxQueryWrapper.eq("writer_id", writerUser.getId());
        // 2.2 根据查询条件查询
        List<DraftBox> originDraftBoxList = draftBoxMapper.selectList(draftBoxQueryWrapper);

        // 3.封装成vo返回
        // 3.1 创建需要返回的草稿信息的列表
        List<DraftBoxVO> returnDraftBoxList = new ArrayList<>();
        for (DraftBox draftBox : originDraftBoxList) {
            // 3.2 设置样式查询条件
            QueryWrapper<EnvelopeStyle> envelopeStyleQueryWrapper = new QueryWrapper<>();
            // 3.3 查询信件的样式url
            envelopeStyleQueryWrapper.eq("id", draftBox.getEnvelopeId());
            EnvelopeStyle envelopeStyle = envelopeStyleMapper.selectOne(envelopeStyleQueryWrapper);
            // 3.4 封装为vo对象
            DraftBoxVO draftBoxVO = new DraftBoxVO();
            BeanUtils.copyProperties(envelopeStyle, draftBoxVO);
            BeanUtils.copyProperties(draftBox, draftBoxVO);
            returnDraftBoxList.add(draftBoxVO);
        }

        return returnDraftBoxList;
    }

    @Override
    public boolean updateDraft(DraftBoxVO draftBoxVO, String openid) {

        // 1.获取user的id
        User writerUser = userUtil.getUserInfo(openid, "id");

        // 2.封装更新条件
        QueryWrapper<DraftBox> draftBoxQueryWrapper = new QueryWrapper<>();
        draftBoxQueryWrapper.eq("id", draftBoxVO.getId());
        draftBoxQueryWrapper.eq("writer_id", writerUser.getId());

        // 3.封装要更新的信息(为null的不会更新)
        DraftBox draftBox = new DraftBox();
        // 不允许更新样式
        draftBoxVO.setEnvelopeId(null);
        BeanUtils.copyProperties(draftBoxVO, draftBox);

        // 4.更新草稿
        int update = draftBoxMapper.update(draftBox, draftBoxQueryWrapper);

        // 5.如果更新条数为0, 则代表没更新, 返回false
        return update != 0;
    }

    @Override
    public boolean deleteDraft(Integer id, String openid) {

        // 1.获取user的id
        User writerUser = userUtil.getUserInfo(openid, "id");

        // 2.封装删除条件
        QueryWrapper<DraftBox> draftBoxQueryWrapper = new QueryWrapper<>();
        draftBoxQueryWrapper.eq("id", id);
        draftBoxQueryWrapper.eq("writer_id", writerUser.getId());

        // 3.删除草稿
        int delete = draftBoxMapper.delete(draftBoxQueryWrapper);

        // 4.删除行数为0则返回false
        return delete != 0;
    }

    @Override
    public boolean addReplyDraft(ReplyDraftVO replyDraftVO, String openid) {

        // 1.获取user的id和nick_name
        User writerUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.组装要存到数据库的草稿数据
        // 2.1 设置当前草稿为回信草稿
        replyDraftVO.setIsReply(1);
        DraftBox draftBox = new DraftBox();
        // 2.2 组装用户信息
        draftBox.setWriterId(writerUser.getId());
        draftBox.setWriterNickName(writerUser.getNickName());
        // 2.3 组装草稿信息
        BeanUtils.copyProperties(replyDraftVO, draftBox);

        // 3.保存回信草稿
        int insert = draftBoxMapper.insert(draftBox);

        // 为0则返回false
        return insert != 0;
    }

}
