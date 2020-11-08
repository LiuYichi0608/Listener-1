package com.loiterer.listener.letter.service;

import com.loiterer.listener.letter.model.entity.WriterBox;
import com.baomidou.mybatisplus.extension.service.IService;
import com.loiterer.listener.letter.model.vo.WriterBoxSaveVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
public interface WriterBoxService extends IService<WriterBox> {

    /**
     * 保存非回信信件的逻辑
     * @param writerBoxSaveVO 需要保存的信件的信息
     * @param openid          用户的openid
     * @return                如果保存成功则返回true, 否则返回false
     */
    boolean writeLetter(WriterBoxSaveVO writerBoxSaveVO, String openid);

}
