package com.xupmtp.securitydemo.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
// 預設tableName會小寫, 要加標籤指定
@TableName("Users")
public class Users {
    private Integer id;

    private String name;

    private String password;

    private String date;

    private String email;

    private static final long serialVersionUID = 1L;
}