package com.service.sso;

import com.qyt.pojo.TbUser;
import com.qyt.utils.E3Result;

public interface UserService {

	public E3Result checkData(String param, int type);
	
	public E3Result  registerUser(TbUser user);
	
	public E3Result  loginUser(String username,String password);
}
