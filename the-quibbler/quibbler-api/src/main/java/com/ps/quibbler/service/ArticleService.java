package com.ps.quibbler.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ps.quibbler.model.dto.PageParams;
import com.ps.quibbler.model.entity.Article;
import com.ps.quibbler.model.vo.ArticleVO;

import java.util.List;

/**
 * @author Paksu
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页获取 Article 列表
     * @param pageParams
     * @return
     */
    IPage<ArticleVO> getArticlePageList(PageParams pageParams);

    List<ArticleVO> getHotArticles(int limit);

    List<ArticleVO> getNewArticles(int limit);
}
