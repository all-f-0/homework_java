package com.cxp.demo5.config;

import com.cxp.demo5.demo.Demo1;
import com.cxp.demo5.demo.Demo2;
import com.cxp.demo5.demo.Demo4;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Demo4Config {

    @Bean
    public Demo4 demo4(Demo2 demo2, Demo1 demo1) {
        Demo4 demo4 = new Demo4();

        demo4.setDemo1(demo1);
        demo4.setDemo2(demo2);

        return demo4;
    }
}
