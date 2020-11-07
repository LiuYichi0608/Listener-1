package com.loiterer.listener.letter.service;

import com.loiterer.listener.letter.model.entity.Letter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loiterer.listener.letter.model.vo.LetterVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzj
 * @since 2020-11-06
 */
public interface LetterService extends IService<Letter> {

    /**
     * 保存用户信件信息
     * @param letterVO 需要保存的信件的主题内容
     * @param openid   根据openid获取用户的信息然后与信件信息一起保存
     * @return         信件保存成功则返回false, 否则返回true
     */
    boolean saveLetter(LetterVO letterVO, String openid);

}
