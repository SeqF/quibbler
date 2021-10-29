package com.ps.quibbler.model.vo.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Paksu
 */
@Data
public class BaseVO {

    private String id;

    private LocalDateTime createdTime;
}
