package com.xupmtp.securitydemo.beans;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data // 省略get/set
@TableName("Users") // 預設tableName會小寫, 要加標籤指定
public class Users {
    private Integer id;

    private String name;

    private String password;

    private String date;

    private String email;

    private static final long serialVersionUID = 1L;
}