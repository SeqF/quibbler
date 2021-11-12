package com.ps.quibbler.model.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ps.quibbler.model.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Paksu
 */
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * getTagListByArticleId
     * @param articleId
     * @return TagList
     */
    List<Tag> getTagListByArticleId(@Param("articleId") String articleId);

    /**
     * getHotTagIds
     * @param limit
     * @return
     */
    List<String> getHotTagIds(int limit);
}
