package org.com.sso.util;
/**
 * @author zhimeiyue
 */
public class MyException extends Exception {

	
	
	//http://www.zihou.me/html/2010/03/16/1864.html
	private String code;
	
	private String message;

	private static final long serialVersionUID = 2232248967528502175L;
	
	public MyException(String code,String message){
		super(message);
		this.message=message;
		this.code=code;
	}
	
	@Override
	public String getLocalizedMessage() {
		return code+":"+message;
	}
}
