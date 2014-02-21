package org.com.sso.service;

import org.com.sso.util.MyException;


/**
 * 
 * @author zhimeiyue
 * @version 1
 */
public interface ITicketService {
	
	/**
	 * 登录认证成功后,为指定用户生成Ticket,认证子系统成员站点保存该用户Ticket，并存储起来。
	 * Ticket生成算法设计：
	 * 		base64(3des(utf8({userName})))+":"+md5(base64(utf8({userName}+":"+{当前时间字符串})))
	 * @param userName 用户登录账号
	 * @param timeout ticket超时时间 单位秒
	 * @return 返回Ticket字符串
	 * @throws SSOException
	 */
	String generateTicket(String userName,int timeout) throws MyException;
	
	/**
	 * 验证ticket有效性，并刷新timeout时间
	 * @param ticket 
	 * @return 返回true：ticket有效 ,false:无效
	 * @throws SSOException 
	 */
	boolean validate(String ticket) throws MyException;
	
	/**
	 * 通过用户名查询该用户的Ticket
	 * @param userName
	 * @return
	 * @throws SSOException
	 */
	String fetchTicket(String userName) throws MyException;
	
	/**
	 * 删除ticket，使其无效
	 * @param ticket
	 * @throws SSOException
	 */
	void removeTicket(String ticket) throws MyException;
	
	/**
	 * 解析USerName
	 * @param ticket
	 * @return
	 * @throws SSOException
	 */
	String trimUserNameFromTicket(String ticket) throws MyException ;
}
