package com.ps.quibbler.controller;

import com.ps.quibbler.enums.ErrorCodeEnum;
import com.ps.quibbler.exception.QuibblerException;
import com.ps.quibbler.global.Result;
import com.ps.quibbler.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

/**
 * @author ps
 */
@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result getHotTag() {
        int limit = 6;
        return Result.successWithData(tagService.getHotTags(limit));
    }

}
