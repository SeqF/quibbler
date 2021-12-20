package com.ps.quibbler.pojo.po;

import com.ps.quibbler.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Paksu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Permission extends BaseEntity {

    private String name;

    private String uri;

    private String method;

    private String status;
}
