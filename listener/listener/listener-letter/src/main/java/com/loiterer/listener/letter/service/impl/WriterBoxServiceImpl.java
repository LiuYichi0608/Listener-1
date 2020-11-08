package com.loiterer.listener.letter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loiterer.listener.letter.mapper.RecipientBoxMapper;
import com.loiterer.listener.letter.model.entity.RecipientBox;
import com.loiterer.listener.letter.model.entity.WriterBox;
import com.loiterer.listener.letter.mapper.WriterBoxMapper;
import com.loiterer.listener.letter.model.vo.WriterBoxSaveVO;
import com.loiterer.listener.letter.service.WriterBoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loiterer.listener.user.mapper.UserMapper;
import com.loiterer.listener.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
     * 需要用到userMapper来获取用户信息
     */
    private final UserMapper userMapper;

    /**
     * 构造方法, 对象初始化的时候注入
     * @param writerBoxMapper    与收信表对应的mapper
     * @param recipientBoxMapper 与发信表对应的mapper
     */
    @Autowired
    public WriterBoxServiceImpl(
            WriterBoxMapper writerBoxMapper,
            RecipientBoxMapper recipientBoxMapper,
            UserMapper userMapper
    ) {
        this.writerBoxMapper = writerBoxMapper;
        this.recipientBoxMapper = recipientBoxMapper;
        this.userMapper = userMapper;
    }

    @Override
    public boolean writeLetter(WriterBoxSaveVO writerBoxSaveVO, String openid) {

        // 1.获取user的id和nick_name
        // 1.1 封装条件
        QueryWrapper<User> writerQueryWrapper = new QueryWrapper<>();
        writerQueryWrapper.select("id", "nick_name");
        writerQueryWrapper.eq("openid", openid);
        // 1.2 根据条件查找信息
        User writerUser = userMapper.selectOne(writerQueryWrapper);

        // 2.根据逻辑查找到几个要接收信件的用户的信息
        // 2.1 查找所有用户的数量
        int userTotal = userMapper.selectCount(null);
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
        List<User> recipientUsers = userMapper.selectBatchIds(recipientUserSet);

        // 3.组装要保存的信件
        // 3.1 封装发送的信件信息
        // 3.1.1 封装信件信息
        WriterBox writerBox = new WriterBox();
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
            // 3.1.1 封装新建信息
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

}
