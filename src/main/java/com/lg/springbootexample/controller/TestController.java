package com.lg.springbootexample.controller;

import com.lg.springbootexample.service.TestService;
import com.lg.springbootexample.vo.Test;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ligang
 * @date 2020-05-27
 */
@RestController
@RequestMapping("test")
@Log4j2
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/test", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    public Object test(@Valid Test test, BindingResult result) {
        return testService.test(test);
    }

}