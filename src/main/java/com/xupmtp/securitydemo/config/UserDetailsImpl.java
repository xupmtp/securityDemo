package com.xupmtp.securitydemo.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xupmtp.securitydemo.beans.Users;
import com.xupmtp.securitydemo.mapper.UsersMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userDetailService")
public class UserDetailsImpl implements UserDetailsService {

	@Resource
	UsersMapper usersMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		QueryWrapper<Users> query = new QueryWrapper<>();
		query.eq("name", username);
		Users res = usersMapper.selectOne(query);
		if (res == null) {
			throw new UsernameNotFoundException("使用者不存在");
		}

		List<GrantedAuthority> authList = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
		return new User(res.getName(), new BCryptPasswordEncoder().encode(res.getPassword()), authList);
	}
}
