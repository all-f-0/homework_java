package com.cxp.demo5.demo;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Data
public class Demo2 {
    @Resource
    private Demo1 demo1;
}
