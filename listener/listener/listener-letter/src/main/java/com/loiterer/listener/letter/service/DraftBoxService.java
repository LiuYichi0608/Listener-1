package com.loiterer.listener.letter.service;

import com.loiterer.listener.letter.model.entity.DraftBox;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loiterer.listener.letter.model.vo.DraftBoxVO;

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

}
