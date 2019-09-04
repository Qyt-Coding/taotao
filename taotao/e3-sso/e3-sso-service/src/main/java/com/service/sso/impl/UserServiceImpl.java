package com.service.sso.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.qyt.pojo.TbUser;
import com.qyt.pojo.TbUserExample;
import com.qyt.pojo.TbUserExample.Criteria;
import com.qyt.redis.JedisClient;
import com.qyt.utils.E3Result;
import com.qyt.utils.JsonUtils;
import com.service.sso.UserService;
import com.taotao.mapper.TbUserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	public E3Result checkData(String param, int type) {

		// 1、从tb_user表中查询数据
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 2、查询条件根据参数动态生成。
		// 1、2、3分别代表username、phone、email
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return E3Result.build(400, "非法的参数");
		}
		// 执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		// 3、判断查询结果，如果查询到数据返回false。
		if (list == null || list.size() == 0) {
			// 4、如果没有返回true。
			return E3Result.ok(true);
		}
		// 5、使用e3Result包装，并返回。
		return E3Result.ok(false);
	}

	@Override
	public E3Result registerUser(TbUser user) {
		// 首先判断是否为空
		if ("".equals(user.getUsername()) || "".equals(user.getPhone()) || "".equals(user.getPassword())) {
			E3Result.build(400, "信息错误", null);
		}
		// 1、2、3分别代表username、phone、email
		E3Result i1 = checkData(user.getUsername(), 1);
		if (!(boolean) i1.getData()) {
			E3Result.build(400, "信息错误", null);
		}
		E3Result i2 = checkData(user.getPhone(), 2);
		if (!(boolean) i2.getData()) {
			E3Result.build(400, "信息错误", null);
		}

		user.setCreated(new Date());
		user.setUpdated(new Date());

		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);

		userMapper.insert(user);

		return E3Result.ok();
	}

	@Autowired
	JedisClient jedisClient;
	
	
	int SESSION_EXPIRE=1800;
	
	@Override
	public E3Result loginUser(String username, String password) {
		
//		1、判断用户名密码是否正确。
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUsernameEqualTo(username);
		
		List<TbUser>list= userMapper.selectByExample(example);
		
		if(list==null || list.size()==0) {
			return E3Result.build(400, "用户名或密码错误");
		}
		
		TbUser user=list.get(0);
		
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes())))
		{
			return E3Result.build(400, "用户名或密码错误");
		}
		String token =UUID.randomUUID().toString();
		
		user.setPassword(null);
		jedisClient.set("SESSION:"+ token, JsonUtils.objectToJson(user));
		
		jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
//		2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
//		3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
//		4、使用String类型保存Session信息。可以使用“前缀:token”为key
//		5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
//		6、返回e3Result包装token。
		return E3Result.ok(token);
	}
}
