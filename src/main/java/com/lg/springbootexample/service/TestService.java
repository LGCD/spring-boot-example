package com.lg.springbootexample.service;

import com.lg.springbootexample.vo.Test;
import org.springframework.stereotype.Service;

/**
 * @author ligang
 * @date 2020-05-27
 */
@Service
public class TestService {

    public Object test(Test test) {
        return test;
    }
}