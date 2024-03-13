package com.example.springbootmapstruct.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    private String fullName;
    private int age;
    private String createTime;

    private List<String> memo;
}
