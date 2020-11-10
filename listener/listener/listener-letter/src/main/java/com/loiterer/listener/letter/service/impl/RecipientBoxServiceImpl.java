package com.loiterer.listener.letter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.loiterer.listener.letter.mapper.EnvelopeStyleMapper;
import com.loiterer.listener.letter.model.entity.EnvelopeStyle;
import com.loiterer.listener.letter.model.entity.RecipientBox;
import com.loiterer.listener.letter.mapper.RecipientBoxMapper;
import com.loiterer.listener.letter.model.vo.RecipientBoxVO;
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
    public List<RecipientBoxVO> getAllRecipientLettersByOpenid(String openid) {

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
    public boolean updateRecipientLetter(Integer id, String openid) {
        // 1.获取当前用户的id和昵称
        User recipientUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.通过用户id和信件id删除收件箱信件
        // 2.1 封装查询条件
        QueryWrapper<RecipientBox> recipientBoxQueryWrapper = new QueryWrapper<>();
        recipientBoxQueryWrapper.eq("recipient_id", recipientUser.getId());
        recipientBoxQueryWrapper.eq("id", id);
        // 2.2 执行查询条件, 找到与用户id和信件id相匹配的收信箱信件
        RecipientBox recipientBox = recipientBoxMapper.selectOne(recipientBoxQueryWrapper);

        // 3.判断查找到的信件是否为null, 以确定用户id和信件id是否正确以及信件是否存在
        if(recipientBox == null){
            return false;
        }
        // 4.判断信件的已读未读状态, 如果recipientBox.getIsRead()为0则更改为1,否则直接返回true
        if(recipientBox.getIsRead() == 0){
            // 把未读状态改为已读
            recipientBox.setIsRead(1);
            // 执行更新操作, 对用户id和信件id相匹配的收信箱信件的阅读状态进行更改
            int updateRow = recipientBoxMapper.update(recipientBox,recipientBoxQueryWrapper);
            // 通过判断更新记录数目, 来确定是否完成更新
            return updateRow == 1;
        }
        return true;
    }

    @Override
    public List<RecipientBoxVO> getPageRecipientLettersByOpenid(String openid) {
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


    /**
     * 把recipientBoxList集合中的相同的内容复制到List<RecipientBoxVO>
     * @param recipientBoxList 收件箱信件集合
     * @return 收件箱信件VO集合
     */
    public List<RecipientBoxVO> getRecipientBoxVO(List<RecipientBox> recipientBoxList){
        List<RecipientBoxVO> recipientBoxVOList = new ArrayList<>();
        for (RecipientBox recipientBox : recipientBoxList) {
            // 通过信件中的信封样式id获取相应的url
            QueryWrapper<EnvelopeStyle> envelopeStyleQueryWrapper = new QueryWrapper<>();
            // 封装信件样式查询条件
            envelopeStyleQueryWrapper.eq("id",recipientBox.getEnvelopeId());
            // 根据信件样式查询条件进行查询
            EnvelopeStyle envelopeStyle = envelopeStyleMapper.selectOne(envelopeStyleQueryWrapper);
            // 生成recipientBoxVO类进行数据存放
            RecipientBoxVO recipientBoxVO = new RecipientBoxVO();
            // 复制envelopeStyle内容到recipientBoxVO
            BeanUtils.copyProperties(envelopeStyle,recipientBoxVO);
            // 复制recipientBox内容到recipientBoxVO
            BeanUtils.copyProperties(recipientBox, recipientBoxVO);
            // 把recipientBox添加到集合
            recipientBoxVOList.add(recipientBoxVO);
        }
        return recipientBoxVOList;

    }


}
