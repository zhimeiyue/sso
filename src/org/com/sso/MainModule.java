package org.com.sso;



import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.sso.service.AccountService;
import org.com.sso.service.TicketService;
import org.com.sso.util.MyException;
import org.com.sso.util.SSOConstant;
import org.nutz.ioc.Ioc;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;




/**
 * @author zhimeiyue
 *
 */

@Modules(scanPackage=true)
@Localization(defaultLang="zh_CN",value="local")
@Views({})
@Encoding(input="UTF-8",output="UTF-8")
@Fail("fm:/WEB-INF/views/fail.html")
@Filters({})
@IocBy(args = {	//配置Ioc容器
		"*org.nutz.ioc.loader.json.JsonLoader","ioc/", //扫描ioc文件夹中的js文件,作为JsonLoader的配置文件
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","org.com.sso.service"}, 
		type = ComboIocProvider.class)
public class MainModule {
	
	
	private static final String DAO_NAME = "dao";
	Log log=Logs.getLog(MainModule.class);
	
	
	
	@Ok("fm:/WEB-INF/views/index.html")
	@At("/index")
	public Object index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> result=new Hashtable<String, Object>();
      
		return result;
	}

	@Ok("fm:")
	@At("/login")
	@Filters({})
	public Object login(HttpServletRequest request) throws Exception{
		Map<String,Object> result=new Hashtable<String, Object>();
	
		return result;
	}
	@Ok("redirect:/index")
	@At("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response,Ioc ioc){
		//String ticket=(String)request.getAttribute(SSOConstant.GD_ATTR_TICKET);

		return "index";
	}
	@Ok("redirect:/index")
	@At("/doLogin")
	@Filters()
	public Object doLogin(@Param("userName") String userName,@Param("password")String password,@Param("refererUrl")String refererUrl,HttpServletRequest request,HttpServletResponse response,Ioc ioc) throws MyException {
		log.info("Do login for user:"+userName);
		log.info("Do login for user:"+userName);
		Map<String,Object> result=new Hashtable<String,Object>();
		result.put("userName", userName);
		AccountService accountService=ioc.get(AccountService.class,"accountSvr");
		
		TicketService ticketService=ioc.get(TicketService.class,"ticketService");
		try {
			if(!accountService.validate(userName, password, null)){//验证是否由此客户
				throw new MyException("USER_OR_PWD_INVALID","用户名或密码错误");
			}
			String ticket=ticketService.generateTicket(userName, 600);//生成ticket 并插入数据库
			Cookie cookie=new Cookie(SSOConstant.GD_CK_TICKET,ticket);//给用户返回cookie
			cookie.setMaxAge(1200*1000);
			response.addCookie(cookie);
			response.addHeader(SSOConstant.HD_GD_EHRSSO_TICKET, ticket);
			response.addHeader(SSOConstant.HD_GD_EHRSSO_USERNAME, userName);
			//验证成功后就跳到原来页面，并且url携带着ticket
			if(Strings.isEmpty(refererUrl)==false){
				refererUrl=URLDecoder.decode(refererUrl, "utf-8");
				if(refererUrl.indexOf("?")>0 && refererUrl.indexOf(SSOConstant.GD_CK_TICKET+"=")<0){
					refererUrl=refererUrl+"&"+SSOConstant.GD_CK_TICKET+"="+URLEncoder.encode(ticket,"utf-8");
				}
				else if(refererUrl.indexOf(SSOConstant.GD_CK_TICKET+"=")<0){
					refererUrl=refererUrl+"?"+SSOConstant.GD_CK_TICKET+"="+URLEncoder.encode(ticket,"utf-8");
				}
				log.info("(returnUrl)Send redirect:"+refererUrl);
				response.sendRedirect(refererUrl);
				return null;
			}
			
			log.info("Response ticket for user->"+userName+":"+ticket);
			
			result.put("ticket", ticket);
			return  result;//返回
			
		} catch (MyException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			return e;
		}

	}
	
	
	
	
	@Ok("json")
	@At("/validate/?")
	@Fail("json")
	@POST
	@Filters({})
	public Map<String,Object> validate(String accessKey,Ioc ioc,HttpServletRequest request){
		String remoteHost= request.getRemoteHost();
		log.info("Do validate for accessKey:"+accessKey+" from host "+remoteHost);
		
		String ticket=request.getHeader("");
		Map<String,Object> result=new Hashtable<String,Object>();

		return result;
	}
	@Ok("json")
	@At("/doLogout/?")
	@Fail("json")
	@POST
	@Filters({})
	public Map<String,Object> doLogout(String accessKey,Ioc ioc,HttpServletRequest request){
		String remoteHost= request.getRemoteHost();
		log.info("Do validate for accessKey:"+accessKey+" from host "+remoteHost);
		
		String ticket=request.getHeader("");
		
		Map<String,Object> result=new Hashtable<String,Object>();

		return result;
	}
	

	

	


	
	
	
}
