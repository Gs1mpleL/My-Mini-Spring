package com.wanfeng.myminispring.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person{
    private String name;
    private int age;
    private Car car;
}