package com.ps.quibbler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ps.quibbler.model.entity.Tag;
import com.ps.quibbler.model.vo.TagVO;

import java.util.List;

/**
 * @author Paksu
 */
public interface TagService extends IService<Tag> {

    /**
     * getTagsByArticleId
     * @param articleId
     * @return TagList
     */
    List<TagVO> getTagListByArticleId(String articleId);

}
