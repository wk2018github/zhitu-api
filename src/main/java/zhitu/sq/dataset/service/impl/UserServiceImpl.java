package zhitu.sq.dataset.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import zhitu.sq.dataset.mapper.UserMapper;
import zhitu.sq.dataset.model.User;
import zhitu.sq.dataset.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int checkLoginName(User user) throws Exception {
		int flag = 0;
		flag = userMapper.selectLoginName(user.getEmail());
		if(0 == flag){
			flag = -1;
		} else if (1 < flag){
			flag = -2;
		}
		return flag;
	}

	@Override
	public User checkPwd(User user) throws Exception {
		
		User u = userMapper.selectUserByLoginName(user.getEmail());
		if(!(user.getPassword().equals(u.getPassword()))){
			return null;
		}
		
		return u;
	}
	
}
