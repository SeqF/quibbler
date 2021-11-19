package com.ps.quibbler.model.vo;

import com.ps.quibbler.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Paksu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleVO extends BaseVO {

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;

    private String author;

    private List<TagVO> tagList;

    private LocalDateTime createdTime;
}
