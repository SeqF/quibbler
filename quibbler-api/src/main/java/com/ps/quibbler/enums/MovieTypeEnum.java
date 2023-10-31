package com.ps.quibbler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovieTypeEnum {

    ACTION("动作");
    private final String type;
}
