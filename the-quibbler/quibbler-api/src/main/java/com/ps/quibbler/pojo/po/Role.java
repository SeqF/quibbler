package com.ps.quibbler.pojo.po;

import com.ps.quibbler.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author paksu
 */
@Data
public class Role extends BaseEntity {

    private String name;
    private String code;
    private Integer status;
}
