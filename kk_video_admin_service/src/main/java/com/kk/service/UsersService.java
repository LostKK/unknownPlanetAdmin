package com.kk.service;

import com.kk.pojo.Users;
import com.kk.utils.PagedResult;

public interface UsersService {
	
	public PagedResult queryUsers(Users user, Integer page, Integer pageSize);

}
