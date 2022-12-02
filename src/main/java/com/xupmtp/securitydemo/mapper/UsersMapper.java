package com.xupmtp.securitydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xupmtp.securitydemo.beans.Users;
import org.springframework.stereotype.Repository;

@Repository
// 繼承BaseMapper,內包含許多sql預設功能
public interface UsersMapper extends BaseMapper<Users> {
}