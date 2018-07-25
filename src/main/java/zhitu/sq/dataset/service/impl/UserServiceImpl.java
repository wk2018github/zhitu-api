package zhitu.sq.dataset.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhitu.sq.dataset.mapper.UserMapper;
import zhitu.sq.dataset.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
}
