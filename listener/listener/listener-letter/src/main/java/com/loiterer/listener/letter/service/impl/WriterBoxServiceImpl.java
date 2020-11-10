package com.loiterer.listener.letter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loiterer.listener.letter.mapper.EnvelopeStyleMapper;
import com.loiterer.listener.letter.mapper.RecipientBoxMapper;
import com.loiterer.listener.letter.model.entity.EnvelopeStyle;
import com.loiterer.listener.letter.model.entity.RecipientBox;
import com.loiterer.listener.letter.model.entity.WriterBox;
import com.loiterer.listener.letter.mapper.WriterBoxMapper;
import com.loiterer.listener.letter.model.vo.ReplyLetterVO;
import com.loiterer.listener.letter.model.vo.WriterBoxVO;
import com.loiterer.listener.letter.service.WriterBoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loiterer.listener.letter.util.UserUtil;
import com.loiterer.listener.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
public class WriterBoxServiceImpl extends ServiceImpl<WriterBoxMapper, WriterBox> implements WriterBoxService {

    /**
     * 随机收信用户数
     */
    private final static int RANDOM_RECIPIENT_USER_COUNT = 3;

    /**
     * 与写信表对应的mapper
     */
    private final WriterBoxMapper writerBoxMapper;

    /**
     * 与发信表对应的mapper
     */
    private final RecipientBoxMapper recipientBoxMapper;

    /**
     * 获取信件样式url的mapper
     */
    private final EnvelopeStyleMapper envelopeStyleMapper;

    /**
     * 需要用到userUtil来获取用户信息
     */
    private final UserUtil userUtil;

    /**
     * 构造方法, 对象初始化的时候注入
     * @param writerBoxMapper     与收信表对应的mapper
     * @param recipientBoxMapper  与发信表对应的mapper
     * @param envelopeStyleMapper 获取信件样式url的mapper
     * @param userUtil            userUtil来获取用户信息
     */
    @Autowired
    public WriterBoxServiceImpl(
            WriterBoxMapper writerBoxMapper,
            RecipientBoxMapper recipientBoxMapper,
            EnvelopeStyleMapper envelopeStyleMapper,
            UserUtil userUtil
    ) {
        this.writerBoxMapper = writerBoxMapper;
        this.recipientBoxMapper = recipientBoxMapper;
        this.envelopeStyleMapper = envelopeStyleMapper;
        this.userUtil = userUtil;
    }

    @Override
    public boolean writeLetter(WriterBoxVO writerBoxSaveVO, String openid) {

        // 1.获取user的id和nick_name
        User writerUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.根据逻辑查找到几个要接收信件的用户的信息
        // 2.1 查找所有用户的数量
        int userTotal = userUtil.selectCount(null);
        // 2.2 设置要随机发送的用户的数量为总用户数量减一
        int recipientUserTotal = userTotal - 1;
        // 2.3 设置最多接收用户, 超过这个数则等于最多接受用户数
        if (RANDOM_RECIPIENT_USER_COUNT < recipientUserTotal) {
            recipientUserTotal = RANDOM_RECIPIENT_USER_COUNT;
        }
        // 2.4 随机找到[1, recipientUserTotal]个用户发送信件
        Random random = new Random(System.currentTimeMillis());
        int randomValue = random.nextInt(recipientUserTotal) + 1;
        // 2.5 使用集合获取随机用户
        Set<Integer> recipientUserSet = new HashSet<>();
        int i = 0;
        while (i < randomValue) {
            // 2.5.1 随机获取一个用户id
            int randomUserId = random.nextInt(userTotal) + 1;
            // 2.5.2 如果随机抽到的用户id和写信人id相同, 则跳过
            if (randomUserId == writerUser.getId()) {
                continue;
            }
            // 2.5.3 如果随机抽到的用户id已经在set中, 则跳过
            if (recipientUserSet.contains(randomUserId)) {
                continue;
            }
            recipientUserSet.add(randomUserId);
            i++;
        }

        // 2.6 查找出这些要接受信件的用户的信息
        List<User> recipientUsers = userUtil.selectBatchIds(recipientUserSet);

        // 3.组装要保存的信件
        // 3.1 封装发送的信件信息
        // 3.1.1 封装信件信息
        WriterBox writerBox = new WriterBox();
        // 防止传入id导致数据库id随着传入的id而被修改
        writerBoxSaveVO.setId(null);
        // 防止传入nickname导致数据库nickname随着传入的nickname而被修改
        writerBoxSaveVO.setWriterNickName(null);
        BeanUtils.copyProperties(writerBoxSaveVO, writerBox);
        // 3.1.2 封装用户信息
        writerBox.setWriterId(writerUser.getId());
        writerBox.setWriterNickName(writerUser.getNickName());
        // 3.1.3 因为这是非回信的方法, 所有isReply属性都为0
        writerBox.setIsReply(0);
        // 3.1.4 保存信件到writerBox中
        writerBoxMapper.insert(writerBox);

        // 3.2 封装接收的信件信息
        for (User recipientUser : recipientUsers) {
            // 3.1.1 封装信件信息
            RecipientBox recipientBox = new RecipientBox();
            BeanUtils.copyProperties(writerBoxSaveVO, recipientBox);
            // 3.1.2 封装用户信息
            recipientBox.setWriterId(writerUser.getId());
            recipientBox.setWriterNickName(writerUser.getNickName());
            // 3.1.3 封装接收人信息
            recipientBox.setRecipientId(recipientUser.getId());
            recipientBox.setRecipientNickName(recipientUser.getNickName());
            // 3.1.4 保存信件到recipientBox中
            recipientBoxMapper.insert(recipientBox);
        }

        return true;
    }

    @Override
    public List<WriterBoxVO> getAllWriterLettersByOpenid(String openid) {

        // 1.获取user的id
        User writerUser = userUtil.getUserInfo(openid, "id");

        // 2.通过用户id查找用户所有的信件信息
        // 2.1 封装查询条件
        QueryWrapper<WriterBox> writerBoxQueryWrapper = new QueryWrapper<>();
        writerBoxQueryWrapper.eq("writer_id", writerUser.getId());
        // 2.2 根据查询条件查询
        List<WriterBox> originWriterBoxList = writerBoxMapper.selectList(writerBoxQueryWrapper);

        // 3.封装成vo返回
        // 3.1 创建需要返回的信件信息的列表
        List<WriterBoxVO> returnWriterBoxList = new ArrayList<>();
        for (WriterBox writerBox : originWriterBoxList) {
            // 3.2 设置样式查询条件
            QueryWrapper<EnvelopeStyle> envelopeStyleQueryWrapper = new QueryWrapper<>();
            // 3.3 查询信件的样式url
            envelopeStyleQueryWrapper.eq("id", writerBox.getEnvelopeId());
            EnvelopeStyle envelopeStyle = envelopeStyleMapper.selectOne(envelopeStyleQueryWrapper);
            // 3.4 封装vo对象
            WriterBoxVO writerBoxVO = new WriterBoxVO();
            BeanUtils.copyProperties(envelopeStyle, writerBoxVO);
            BeanUtils.copyProperties(writerBox, writerBoxVO);
            returnWriterBoxList.add(writerBoxVO);
        }

        return returnWriterBoxList;
    }

    @Override
    public boolean deleteLetterById(Integer id, String openid) {

        // 1.获取user的id
        User writerUser = userUtil.getUserInfo(openid, "id");

        // 2.通过用户的id和信件id删除信件
        // 2.1 封装删除条件
        QueryWrapper<WriterBox> writerBoxQueryWrapper = new QueryWrapper<>();
        writerBoxQueryWrapper.eq("id", id);
        writerBoxQueryWrapper.eq("writer_id", writerUser.getId());
        // 2.2 根据删除条件删除
        int delete = writerBoxMapper.delete(writerBoxQueryWrapper);

        // 3.判断是否删除成功
        return delete > 0;
    }

    @Override
    public boolean saveReplyLetter(ReplyLetterVO replyLetterVO, String openid) {

        // 1.获取user的id和nickname
        User writerUser = userUtil.getUserInfo(openid, "id", "nick_name");

        // 2.封装要保存的信件
        WriterBox writerBox = new WriterBox();
        // 2.1 放入写信人信息
        writerBox.setWriterId(writerUser.getId());
        writerBox.setWriterNickName(writerUser.getNickName());
        // 2.2 放入信件信息和收信人信息
        BeanUtils.copyProperties(replyLetterVO, writerBox);
        // 2.3 设置为回信
        writerBox.setIsReply(1);
        // 2.4 设置接收信件的信息
        RecipientBox recipientBox = new RecipientBox();
        BeanUtils.copyProperties(writerBox, recipientBox);

        // 3.保存写信的信息, 与保存收信的信息
        int insertWriter = writerBoxMapper.insert(writerBox);
        int insertRecipient = recipientBoxMapper.insert(recipientBox);

        // 4.写与收保存成功才返回
        return insertWriter != 0 && insertRecipient != 0;
    }

}
