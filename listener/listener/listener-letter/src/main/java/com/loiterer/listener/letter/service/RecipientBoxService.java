package com.loiterer.listener.letter.service;

import com.loiterer.listener.letter.model.entity.RecipientBox;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loiterer.listener.letter.model.vo.RecipientContentVO;
import com.loiterer.listener.letter.model.vo.RecipientEnvelopeVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyc
 * @since 2020-11-08
 */
public interface RecipientBoxService extends IService<RecipientBox> {

    /**
     * 根据用户的openid去获取当前用户收件箱里的所有未删除信件
     * @param openid 用户的openid
     * @return List<RecipientBoxVO>收信箱信件集合
     */
    List<RecipientEnvelopeVO> getAllRecipientLettersByOpenid(String openid);

    /**
     * 根据用户的openid和信件id去删除用户收件箱中的指定信件
     * @param id 收件箱信件id
     * @param openid 用户的openid
     * @return 判断是否删除成功
     */
    boolean deleteRecipientLetter(Integer id, String openid);

    /**
     * 根据用户的openid去获取当前用户收件箱里的10条最新的未删除信件
     * @param openid 用户的openid
     * @return List<RecipientBoxVO> 收信箱信件集合
     */
    List<RecipientEnvelopeVO> getPageRecipientLettersByOpenid(String openid);

    /**
     * 根据用户的openid和收件箱信件的id去获取当前信件的内容
     * @param id 收件箱信件id
     * @param openid 用户的openid
     * @return RecipientEnvelopeVO 用户信件内容
     */
    RecipientContentVO getRecipientLetterById(Integer id, String openid);
}
