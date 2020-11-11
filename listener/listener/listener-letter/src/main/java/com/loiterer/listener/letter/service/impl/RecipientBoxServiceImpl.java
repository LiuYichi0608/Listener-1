package com.loiterer.listener.letter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loiterer.listener.letter.mapper.EnvelopeStyleMapper;
import com.loiterer.listener.letter.model.entity.EnvelopeStyle;
import com.loiterer.listener.letter.model.entity.RecipientBox;
import com.loiterer.listener.letter.mapper.RecipientBoxMapper;
import com.loiterer.listener.letter.model.vo.RecipientContentVO;
import com.loiterer.listener.letter.model.vo.RecipientEnvelopeVO;
import com.loiterer.listener.letter.service.RecipientBoxService;
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
 * @author lyc
 * @since 2020-11-08
 */
@Slf4j
@Service
public class RecipientBoxServiceImpl extends ServiceImpl<RecipientBoxMapper, RecipientBox> implements RecipientBoxService {

    /**
     * 与用户收信表对应的mapper
     */
    private final RecipientBoxMapper recipientBoxMapper;

    /**
     * 与信件样式表对应的mapper
     */
    private final EnvelopeStyleMapper envelopeStyleMapper;

    /**
     * userUtil来获取用户信息
     */
    private final UserUtil userUtil;

    /**
     * 构造方法, 主要用于实现对象初始化注入
     * @param recipientBoxMapper 与用户收信表对应的mapper
     * @param envelopeStyleMapper 与信件样式表对应的mapper
     * @param userUtil userUtil来获取用户信息
     */
    @Autowired
    public RecipientBoxServiceImpl(RecipientBoxMapper recipientBoxMapper, EnvelopeStyleMapper envelopeStyleMapper, UserUtil userUtil) {
        this.recipientBoxMapper = recipientBoxMapper;
        this.envelopeStyleMapper = envelopeStyleMapper;
        this.userUtil = userUtil;
    }

    @Override
    public List<RecipientEnvelopeVO> getAllRecipientLettersByOpenid(String openid) {

        // 1.获取当前用户的id和昵称
        User recipientUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.通过用户id查找用户所有的信件信息
        // 2.1 封装查询条件
        QueryWrapper<RecipientBox> recipientBoxQueryWrapper = new QueryWrapper<>();
        recipientBoxQueryWrapper.eq("recipient_id",recipientUser.getId());
        // 2.2 根据查询条件查询
        List<RecipientBox> recipientBoxList = recipientBoxMapper.selectList(recipientBoxQueryWrapper);

        // 3.封装成vo返回
        return getRecipientBoxVO(recipientBoxList);
    }

    @Override
    public boolean deleteRecipientLetter(Integer id, String openid) {
        // 1.获取当前用户的id和昵称
        User recipientUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.通过用户id和信件id删除收件箱信件
        // 2.1 封装查询条件
        QueryWrapper<RecipientBox> recipientBoxQueryWrapper = new QueryWrapper<>();
        recipientBoxQueryWrapper.eq("recipient_id", recipientUser.getId());
        recipientBoxQueryWrapper.eq("id", id);
        int deleteRow = recipientBoxMapper.delete(recipientBoxQueryWrapper);

        // 3.根据deleteRow的值判断删除的结果
        return deleteRow > 0;
    }

    @Override
    public List<RecipientEnvelopeVO> getPageRecipientLettersByOpenid(String openid) {
        // 1.获取当前用户的id和昵称
        User recipientUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.通过用户id查找用户所有的信件信息
        // 2.1 封装查询条件
        QueryWrapper<RecipientBox> recipientBoxQueryWrapper = new QueryWrapper<>();
        recipientBoxQueryWrapper.eq("recipient_id", recipientUser.getId());
        recipientBoxQueryWrapper.orderByDesc("gmt_create");
        // 参数current是当前页, 参数size是每页个数
        IPage<RecipientBox> recipientBoxUserPage = new Page<>(1, 10);
        // 2.2 根据查询条件查询
        recipientBoxUserPage = recipientBoxMapper.selectPage(recipientBoxUserPage, recipientBoxQueryWrapper);
        // 获取收件箱信件集合
        List<RecipientBox> recipientBoxList = recipientBoxUserPage.getRecords();

        return getRecipientBoxVO(recipientBoxList);
    }

    @Override
    public RecipientContentVO getRecipientLetterById(Integer id, String openid) {
        // 1.获取当前用户的id和昵称
        User recipientUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.通过用户id查找用户所有的信件信息
        // 2.1 封装查询条件
        QueryWrapper<RecipientBox> recipientBoxQueryWrapper = new QueryWrapper<>();
        recipientBoxQueryWrapper.eq("recipient_id", recipientUser.getId());
        recipientBoxQueryWrapper.eq("id", id);
        // 2.2 执行查询条件, 找到与用户id和信件id相匹配的收信箱信件
        RecipientBox recipientBox = recipientBoxMapper.selectOne(recipientBoxQueryWrapper);

        // 3.判断是否获取信件成功
        if(recipientBox != null){
            // 4.判断信件已读状态
            if(recipientBox.getIsRead() == 0){
                // 把未读状态改为已读
                recipientBox.setIsRead(1);
                // 执行更新操作, 对用户id和信件id相匹配的收信箱信件的阅读状态进行更改
                recipientBoxMapper.update(recipientBox,recipientBoxQueryWrapper);
                // 通过判断更新记录数目, 来确定是否完成更新
            }

            // 5.通过信件中的信封样式id获取相应的url
            QueryWrapper<EnvelopeStyle> envelopeStyleQueryWrapper = new QueryWrapper<>();
            // 5.1封装信件样式查询条件
            envelopeStyleQueryWrapper.eq("id",recipientBox.getEnvelopeId());
            // 5.2根据信件样式查询条件进行查询
            EnvelopeStyle envelopeStyle = envelopeStyleMapper.selectOne(envelopeStyleQueryWrapper);

            // 6.生成recipientBoxVO类进行数据存放
            RecipientContentVO recipientContentVO = new RecipientContentVO();
            // 6.1复制envelopeStyle内容到recipientBoxVO
            BeanUtils.copyProperties(envelopeStyle, recipientContentVO);
            // 6.2复制recipientBox内容到recipientBoxVO
            BeanUtils.copyProperties(recipientBox, recipientContentVO);

            return recipientContentVO;
        }
        return null;
    }


    /**
     * 把recipientBoxList集合中的相同的内容复制到List<RecipientBoxVO>
     * @param recipientBoxList 收件箱信件集合
     * @return 收件箱信件VO集合
     */
    public List<RecipientEnvelopeVO> getRecipientBoxVO(List<RecipientBox> recipientBoxList){
        List<RecipientEnvelopeVO> recipientEnvelopeVOList = new ArrayList<>();
        for (RecipientBox recipientBox : recipientBoxList) {
            // 通过信件中的信封样式id获取相应的url
            QueryWrapper<EnvelopeStyle> envelopeStyleQueryWrapper = new QueryWrapper<>();
            // 封装信件样式查询条件
            envelopeStyleQueryWrapper.eq("id",recipientBox.getEnvelopeId());
            // 根据信件样式查询条件进行查询
            EnvelopeStyle envelopeStyle = envelopeStyleMapper.selectOne(envelopeStyleQueryWrapper);
            // 生成recipientBoxVO类进行数据存放
            RecipientEnvelopeVO recipientEnvelopeVO = new RecipientEnvelopeVO();
            // 复制envelopeStyle内容到recipientBoxVO
            BeanUtils.copyProperties(envelopeStyle, recipientEnvelopeVO);
            // 复制recipientBox内容到recipientBoxVO
            BeanUtils.copyProperties(recipientBox, recipientEnvelopeVO);
            // 把recipientBox添加到集合
            recipientEnvelopeVOList.add(recipientEnvelopeVO);
        }
        return recipientEnvelopeVOList;

    }


}
