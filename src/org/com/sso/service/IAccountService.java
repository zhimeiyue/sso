package org.com.sso.service;

import org.com.sso.util.MyException;

/**
 * 
 * 
 * 
 * @author zhimeiyue
 *
 */
public interface IAccountService {

	
	
	/**
	 * 验证账号密码是否合法
	 * @param userName 登录账号
	 * @param password 登录密码
	 * @param userType 用户类型
	 * @return 返回值为true：合法 ，false：非法，其他不合法信息抛出异常，并告知错误信息。
	 * @throws SSOException
	 */
	boolean validate(String userName,String password,String userType) throws MyException; 
	
	/**
	 * 支持账号注册接口
	 * @param userName 登录账号
	 * @param password 登录密码
	 * @param userType 用户类型
	 * @param email
	 * @throws SSOException
	 */
	void register(String userName,String password,String userType,String email) throws MyException;
}
