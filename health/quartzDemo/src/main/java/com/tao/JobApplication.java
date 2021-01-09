package com.tao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author: Eric
 * @since: 2021/1/8
 */
public class JobApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-job.xml");
        System.in.read();
    }
}
