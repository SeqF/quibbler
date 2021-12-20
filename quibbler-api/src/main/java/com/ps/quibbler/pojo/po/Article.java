package com.ps.quibbler.pojo.po;

import com.ps.quibbler.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.ps.quibbler.global.Constants.ARTICLE_COMMON;

/**
 * @author ps
 */
@EqualsAndHashCode(callSuper = true)
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
