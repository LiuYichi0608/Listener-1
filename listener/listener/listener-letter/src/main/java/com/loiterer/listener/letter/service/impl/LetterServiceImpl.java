package com.loiterer.listener.letter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.loiterer.listener.letter.model.entity.Letter;
import com.loiterer.listener.letter.mapper.LetterMapper;
import com.loiterer.listener.letter.model.vo.LetterVO;
import com.loiterer.listener.letter.service.LetterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loiterer.listener.user.mapper.UserMapper;
import com.loiterer.listener.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzj
 * @since 2020-11-06
 */
@Slf4j
@Service
public class LetterServiceImpl extends ServiceImpl<LetterMapper, Letter> implements LetterService {

    private final LetterMapper letterMapper;

    private final UserMapper userMapper;

    @Autowired
    public LetterServiceImpl(LetterMapper letterMapper, UserMapper userMapper) {
        this.letterMapper = letterMapper;
        this.userMapper = userMapper;
    }

    @Override
    public boolean saveLetter(LetterVO letterVO, String openid) {

        // 1.根据openid从数据库中获取用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);
        // 获取用户id和昵称
        userQueryWrapper.select("id", "nick_name");

        User user = userMapper.selectOne(userQueryWrapper);

        log.debug("用户的id信息为: {}, 用户的昵称为: {}", user.getId(), user.getNickName());

        // 2.整理好要保存的信息
        Letter letter = new Letter();
        // 复制用户信息
        letter.setWriterId(user.getId());
        letter.setWriterNickName(user.getNickName());
        // 复制信件信息
        BeanUtils.copyProperties(letterVO, letter);

        log.debug("要保存的信件信息: {}", letter);

        // 3.将信件信息以及用户信息保存到表中
        letterMapper.insert(letter);

        log.debug("信件id: {}", letter.getId());

        // 当信件id不为null时返回false(暂时不知道要不要返回信件id)
        return letter.getId() != null;
    }

}
