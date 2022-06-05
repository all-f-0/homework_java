package com.cxp.demo05.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "school")
@Data
public class StudentProperties {
    private List<String> students;
}
