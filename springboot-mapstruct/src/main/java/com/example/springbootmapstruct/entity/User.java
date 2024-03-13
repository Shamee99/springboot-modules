package com.example.springbootmapstruct.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class User implements Serializable {

    private String name;
    private int age;
    private Date createTime;

    private String password;

    private String memo;
}
