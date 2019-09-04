package com.service.sso;

import com.qyt.utils.E3Result;

public interface tokenService {
	
	public E3Result getUserByToken(String token);

}
