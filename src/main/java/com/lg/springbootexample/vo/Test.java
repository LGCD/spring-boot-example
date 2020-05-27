package com.lg.springbootexample.vo;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ligang
 * @date 2020-05-27
 */
@Data
public class Test {


    @NotNull(message = "id not be null")
    private int id;
    private String name;
}