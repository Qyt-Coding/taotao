package com.service.sso.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qyt.pojo.TbUser;
import com.qyt.redis.JedisClient;
import com.qyt.utils.E3Result;
import com.qyt.utils.JsonUtils;
import com.service.sso.tokenService;
@Service
public class tokenServiceImp implements tokenService{


	@Autowired
	JedisClient jedisClient;
	
	@Override
	public E3Result getUserByToken(String token) {
		//获得token的value值
		String tokenValue=jedisClient.get("SESSION:"+token);
		//判断过期没有
		if("".equals(tokenValue)||tokenValue==null)
		{
			return  E3Result.build(400,"身份过期");
		}
		jedisClient.expire("SESSION:" + token, 1800);
		//返回结果，E3Result其中包含TbUser对象
		TbUser user = JsonUtils.jsonToPojo(tokenValue, TbUser.class);
		System.out.println(user+"***************************");
		return E3Result.ok(user);
	}
	
	

}
