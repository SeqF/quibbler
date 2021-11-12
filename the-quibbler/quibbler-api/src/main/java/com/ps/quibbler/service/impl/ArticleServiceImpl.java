package com.ps.quibbler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.model.dao.mapper.ArticleMapper;
import com.ps.quibbler.model.dto.PageParams;
import com.ps.quibbler.model.entity.Article;
import com.ps.quibbler.base.BaseEntity;
import com.ps.quibbler.model.vo.ArticleVO;
import com.ps.quibbler.service.ArticleService;
import com.ps.quibbler.service.SysUserService;
import com.ps.quibbler.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author Paksu
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;

    @Resource
    ArticleMapper articleMapper;

    @Override
    public IPage<ArticleVO> getArticlePageList(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPageNum(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // order by weight(top),createdTime(default)
        queryWrapper.orderByDesc(Arrays.asList(Article::getWeight, BaseEntity::getCreatedTime));
        Page<Article> articlePage = baseMapper.selectPage(page, queryWrapper);
        return articlePage.convert(article -> {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(article, articleVO);
            articleVO.setTagList(tagService.getTagListByArticleId(article.getId()));
            articleVO.setAuthor(sysUserService.getById(article.getAuthorId()).getUsername());
            return articleVO;
        });
    }

}
