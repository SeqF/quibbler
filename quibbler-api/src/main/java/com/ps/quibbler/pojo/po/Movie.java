package com.ps.quibbler.pojo.po;

import com.ps.quibbler.base.BaseEntity;
import com.ps.quibbler.enums.MovieTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Movie extends BaseEntity {

    /**
     * 电影名称
     */
    private String name;

    /**
     * 导演
     */
    private String director;

    /**
     * 主演
     */
    private List<String> stars;

    /**
     * 编剧
     */
    private List<String> writers;

    /**
     * 类型
     */
    private MovieTypeEnum type;

    /**
     * 发行时间
     */
    private Date releaseDate;
}
