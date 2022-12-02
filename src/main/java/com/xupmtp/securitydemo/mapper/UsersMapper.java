package com.xupmtp.securitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupmtp.securitydemo.beans.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends BaseMapper<Users> {
}