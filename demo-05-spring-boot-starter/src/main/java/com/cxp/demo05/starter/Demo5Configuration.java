package com.cxp.demo05.starter;

import com.cxp.demo05.starter.demo.Klass;
import com.cxp.demo05.starter.demo.School;
import com.cxp.demo05.starter.demo.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class Demo5Configuration {

    @Bean
    public Student student100() {
        Student student = new Student();

        student.setId(0);
        student.setName("001");

        return student;
    }

    @Bean
    public List<Student> students(StudentProperties properties) {
        AtomicInteger ix = new AtomicInteger(1);
        List<Student> students = Stream.concat(
                Stream.of(student100()),
                properties.getStudents()
                        .stream()
                        .map(name -> new Student(ix.getAndAdd(1), name, null, null))
        ).collect(Collectors.toList());

        return students;
    }

    @Bean
    public Klass class1(StudentProperties properties) {
        Klass klass = new Klass();

        klass.setStudents(students(properties));

        return klass;
    }

    @Bean
    public School school() {
        return new School();
    }
}
