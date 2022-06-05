package com.cxp.demo5;

import com.cxp.demo5.demo.Demo1;
import com.cxp.demo5.demo.Demo2;
import com.cxp.demo5.demo.Demo3;
import com.cxp.demo5.demo.Demo4;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:application-beans.xml"})
public class Demo05Application {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication();
		application.addListeners(new ReadyEventListener());
		application.run(Demo05Application.class, args);
	}

}
