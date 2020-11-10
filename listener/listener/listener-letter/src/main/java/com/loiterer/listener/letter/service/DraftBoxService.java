package com.loiterer.listener.letter.service;

import com.loiterer.listener.letter.model.entity.DraftBox;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loiterer.listener.letter.model.vo.DraftBoxVO;
import com.loiterer.listener.letter.model.vo.ReplyDraftVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
public interface DraftBoxService extends IService<DraftBox> {

    /**
     * 保存非回信草稿的逻辑
     * @param draftBoxVO 要保存的草稿信息
     * @param openid     用户信息
     * @return           返回插入草稿信息是否成功, 插入成功返回true, 否则返回false
     */
    boolean addDraft(DraftBoxVO draftBoxVO, String openid);

    /**
     * 获取用户的所有草稿
     * @param openid 用户的openid
     * @return       返回用户的所有草稿
     */
    List<DraftBoxVO> getAllDrafts(String openid);

    /**
     * 更新草稿信息
     * @param draftBoxVO 要更新的草稿信息
     * @param openid     用户的信息
     * @return           更新成功返回true, 否则返回false
     */
    boolean updateDraft(DraftBoxVO draftBoxVO, String openid);

    /**
     * 根据草稿id和用户信息删除属于该用户的草稿
     * @param id     草稿id
     * @param openid 用户的信息
     * @return       删除成功返回true, 否则返回false
     */
    boolean deleteDraft(Integer id, String openid);

    /**
     * 保存回信草稿
     * @param replyDraftVO 要保存的回信草稿信息
     * @param openid       用户的信息
     * @return             保存成功返回true, 否则返回false
     */
    boolean addReplyDraft(ReplyDraftVO replyDraftVO, String openid);

}
