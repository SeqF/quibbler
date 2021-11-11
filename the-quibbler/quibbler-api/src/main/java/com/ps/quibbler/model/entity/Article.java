package com.ps.quibbler.model.entity;

import com.ps.quibbler.model.entity.base.BaseEntity;
import lombok.Data;

import static com.ps.quibbler.global.Constants.ARTICLE_COMMON;

/**
 * @author ps
 */
@Data
public class Article extends BaseEntity {

    private String title;

    private String summary;

    private Integer commentCount;

    private Integer viewCount;

    private Long authorId;

    private Long bodyId;

    private Long categoryId;

    private Integer weight = ARTICLE_COMMON;



}
