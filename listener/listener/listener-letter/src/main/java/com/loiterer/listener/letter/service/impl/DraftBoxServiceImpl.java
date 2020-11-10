package com.loiterer.listener.letter.service.impl;

import com.loiterer.listener.letter.mapper.EnvelopeStyleMapper;
import com.loiterer.listener.letter.model.entity.DraftBox;
import com.loiterer.listener.letter.mapper.DraftBoxMapper;
import com.loiterer.listener.letter.model.vo.DraftBoxVO;
import com.loiterer.listener.letter.service.DraftBoxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loiterer.listener.letter.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



        return false;
    }

}
