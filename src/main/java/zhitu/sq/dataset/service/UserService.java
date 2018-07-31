package zhitu.sq.dataset.service;


import zhitu.sq.dataset.model.User;


public interface UserService {
	
	/**
	 * @author qwm
	 * 检查用户名是否存在
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int checkLoginName(User user) throws Exception;
	/**
	 * @author qwm
	 * 检查密码是否正确
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User checkPwd(User user) throws Exception;
	
	
}
