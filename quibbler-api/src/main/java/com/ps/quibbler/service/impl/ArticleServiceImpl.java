package com.ps.quibbler.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.Converter;
import cn.hutool.core.util.CharsetUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.pojo.dao.mapper.ArticleMapper;
import com.ps.quibbler.pojo.dto.PageParams;
import com.ps.quibbler.pojo.po.Article;
import com.ps.quibbler.pojo.vo.ArticleVO;
import com.ps.quibbler.service.ArticleService;
import com.ps.quibbler.service.SysUserService;
import com.ps.quibbler.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Paksu
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public IPage<ArticleVO> getArticlePageList(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // order by weight(top),createdTime(default)
        queryWrapper.orderByDesc(Arrays.asList(Article::getWeight, Article::getCreatedTime));
        Page<Article> articlePage = baseMapper.selectPage(page, queryWrapper);
        return articlePage.convert(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            articleVO.setTagList(tagService.getTagListByArticleId(article.getId()));
            articleVO.setAuthor(sysUserService.getById(article.getAuthorId()).getUsername());
            return articleVO;
        });
    }

    @Override
    public List<ArticleVO> getHotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCount);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select id,title from article order by view_counts desc limit 5
        List<Article> articleList = baseMapper.selectList(queryWrapper);
        return articleList.stream().map(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            return articleVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ArticleVO> getNewArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreatedTime);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        //select id,title from article order by created_time desc limit 5
        List<Article> articleList = baseMapper.selectList(queryWrapper);
        return articleList.stream().map(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            return articleVO;
        }).collect(Collectors.toList());
    }
}
