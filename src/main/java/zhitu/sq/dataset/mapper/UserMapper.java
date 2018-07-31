package zhitu.sq.dataset.mapper;


import org.apache.ibatis.annotations.Mapper;

import zhitu.sq.dataset.model.User;




@Mapper
public interface UserMapper {
	
	/**
	 * @author qwm
	 * 查询user表中用户名 =email的user的个数
	 * @param email
	 * @return
	 */
	Integer selectLoginName(String email);
	/**
	 * @author qwm
	 * 根据email查询user
	 * @param email
	 * @return
	 */
	User selectUserByLoginName(String email);

    
	
	
}