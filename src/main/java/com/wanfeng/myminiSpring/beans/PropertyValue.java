package com.wanfeng.myminiSpring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyValue {
    private final String name;
    private final Object value;
}
