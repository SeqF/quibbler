package com.ps.quibbler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ps.quibbler.pojo.po.Tag;
import com.ps.quibbler.pojo.vo.TagVO;

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

    /**
     * getHotTags
     * @param limit
     * @return
     */
    List<TagVO> getHotTags(int limit);
}
