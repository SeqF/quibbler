package com.ps.quibbler.pojo.po;

import com.ps.quibbler.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ps
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Tag extends BaseEntity {

    private String tagName;
}
