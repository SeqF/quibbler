package com.ps.quibbler.model.vo;

import com.ps.quibbler.base.BaseVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Paksu
 */
@Data
public class TagVO extends BaseVO {

    private String tagName;

    private LocalDateTime createdTime;
}
