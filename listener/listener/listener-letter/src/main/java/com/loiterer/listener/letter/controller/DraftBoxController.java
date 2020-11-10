package com.loiterer.listener.letter.controller;


import com.loiterer.listener.common.result.ResultEntity;
import com.loiterer.listener.common.util.JwtUtil;
import com.loiterer.listener.letter.model.vo.DraftBoxVO;
import com.loiterer.listener.letter.model.vo.ReplyDraftVO;
import com.loiterer.listener.letter.service.DraftBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
@RestController
@RequestMapping("/letter/draft-box")
public class DraftBoxController {

    /**
     * 草稿相关操作的逻辑service类
     */
    private final DraftBoxService draftBoxService;

    /**
     * 使用jwt工具类从token中获取用户信息
     */
    private final JwtUtil jwtUtil;

    /**
     * 构造方法注入该Controller需要用到的类
     * @param draftBoxService 写信相关操作的逻辑service类
     * @param jwtUtil         使用jwt工具类从token中获取用户信息
     */
    @Autowired
    public DraftBoxController(
            DraftBoxService draftBoxService,
            JwtUtil jwtUtil
    ) {
        this.draftBoxService = draftBoxService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 插入草稿信息
     * @param draftBoxVO 要保存的信件信息
     * @param request    从request中获取token并获取到用户的openid
     * @return           返回保存草稿是否成功的信息
     */
    @PostMapping("/add/draft")
    public ResultEntity addDraft(
            @RequestBody DraftBoxVO draftBoxVO,
            HttpServletRequest request
    ) {

        // 1.从request中获取到用户的token并使用jwt工具类获取到用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.使用service类来保存草稿
        boolean flag = draftBoxService.addDraft(draftBoxVO, openid);

        // 3.判断是否保存草稿成功
        if (!flag) {
            return ResultEntity.fail().message("保存草稿失败!");
        }
        return ResultEntity.success().message("保存草稿成功!");
    }

    /**
     * 返回属于当前用户的所有草稿
     * @param request 从request中获取token并获取到用户的openid
     * @return        返回当前用户的所有草稿
     */
    @GetMapping("/get/all/drafts")
    public ResultEntity getAllDrafts(
            HttpServletRequest request
    ) {

        // 1.从request中获取到用户的token并使用jwt工具类获取到用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.获取用户所有草稿
        List<DraftBoxVO> draftBoxVOList = draftBoxService.getAllDrafts(openid);

        // 3.获取失败则返回失败信息, 否则返回成功信息以及信件列表
        if (draftBoxVOList == null) {
            return ResultEntity.fail().message("获取草稿失败!");
        }
        return ResultEntity.success().data("draftBoxList", draftBoxVOList);
    }

    /**
     * 更新草稿信息
     * @param draftBoxVO 要更新的草稿信息
     * @param request    从request中获取token并获取到用户的openid
     * @return           返回是否更新成功
     */
    @PutMapping("/update/draft")
    public ResultEntity updateDraft(
            @RequestBody DraftBoxVO draftBoxVO,
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.更新草稿信息
        boolean flag = draftBoxService.updateDraft(draftBoxVO, openid);

        // 3.判断是否更新该草稿成功
        if (!flag) {
            return ResultEntity.fail().message("更新草稿失败!");
        }
        return ResultEntity.success().message("更新草稿成功!");
    }

    /**
     * 根据草稿id和用户信息删除草稿
     * @param id      草稿id
     * @param request 从request中获取token并获取到用户的openid
     * @return        返回是否删除成功
     */
    @DeleteMapping("/delete/draft/{id}")
    public ResultEntity deleteDraft(
            @PathVariable("id") Integer id,
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.删除草稿
        boolean flag = draftBoxService.deleteDraft(id, openid);

        // 3.判断是否删除该草稿成功
        if (!flag) {
            return ResultEntity.fail().message("删除草稿失败!");
        }
        return ResultEntity.success().message("删除草稿成功!");
    }

    /**
     * 保存回信草稿
     * @param replyDraftVO 要保存的回信草稿信息
     * @param request      从request中获取token并获取到用户的openid
     * @return             返回保存草稿是否成功的信息
     */
    @PostMapping("/add/reply/draft")
    public ResultEntity addReplyDraft(
            @RequestBody ReplyDraftVO replyDraftVO,
            HttpServletRequest request
    ) {

        // 1.获取用户的openid
        String openid = jwtUtil.getOpenid(request.getHeader("token"));

        // 2.使用service类来保存草稿
        boolean flag = draftBoxService.addReplyDraft(replyDraftVO, openid);

        // 3.判断是否保存草稿成功
        if (!flag) {
            return ResultEntity.fail().message("保存草稿失败!");
        }
        return ResultEntity.success().message("保存草稿成功!");

    }

}

