package com.ps.quibbler.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ByteUtil;
import cn.hutool.core.util.HexUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ps.quibbler.pojo.dao.mapper.TagMapper;
import com.ps.quibbler.pojo.po.Tag;
import com.ps.quibbler.pojo.vo.TagVO;
import com.ps.quibbler.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Paksu
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * getTagListByArticleId
     *
     * @param articleId
     * @return
     */
    @Override
    public List<TagVO> getTagListByArticleId(String articleId) {
        List<Tag> tagList = baseMapper.getTagListByArticleId(articleId);
        return tagList.stream().map(tag -> {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            return tagVO;
        }).collect(Collectors.toList());
    }

    /**
     * getHotTags
     *
     * @param limit
     * @return
     */
    @Override
    public List<TagVO> getHotTags(int limit) {
        List<String> hotTagIds = baseMapper.getHotTagIds(limit);
        if (hotTagIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Tag> tagList = baseMapper.selectBatchIds(hotTagIds);
        return tagList.stream().map(tag -> {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            return tagVO;
        }).collect(Collectors.toList());
    }
}
