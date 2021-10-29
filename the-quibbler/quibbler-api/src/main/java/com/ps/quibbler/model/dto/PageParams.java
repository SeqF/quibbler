package com.ps.quibbler.model.dto;

import lombok.Data;

import static com.ps.quibbler.global.Constants.PAGE_NUM;
import static com.ps.quibbler.global.Constants.PAGE_SIZE;

/**
 * @author ps
 */
@Data
public class PageParams {

    private int pageNum = PAGE_NUM;

    private int pageSize = PAGE_SIZE;
}
