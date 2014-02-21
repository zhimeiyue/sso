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
import org.com.sso.util.SSOCheckTicket;
import org.com.sso.util.SSOConstant;
import org.com.sso.util.SiteAuthorizationFilter;
import org.nutz.ioc.Ioc;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.mvc.view.FreemarkerViewMaker;











/**
 * @author zhimeiyue
 *
 */

@Modules(scanPackage=true)
@Localization(defaultLang="zh_CN",value="local")
@Views({FreemarkerViewMaker.class})
@Encoding(input="UTF-8",output="UTF-8")
@Fail("fm:/WEB-INF/views/fail.html")
@Filters({@By(type=SSOCheckTicket.class)})
@IocBy(args = {
		"*org.nutz.ioc.loader.json.JsonLoader","ioc/", 
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","org.com.sso.service"}, 
		type = ComboIocProvider.class)
public class MainModule {
	
	
	private static final String DAO_NAME = "dao";
	Log log=Logs.getLog(MainModule.class);
	
	/***
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@Ok("fm:/WEB-INF/views/index.html")
	@At("/index")
	public Object index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> result=new Hashtable<String, Object>();
		String refererUrl=request.getParameter("returnUrl");
		
		log.info("-----------refererUrl----------"+refererUrl);
		if(Strings.isEmpty(refererUrl)==false){
			refererUrl=URLDecoder.decode(refererUrl, "utf-8");
			String ticket="";
			Cookie[] cookies=request.getCookies();
			for (Cookie c : cookies) {
				if(SSOConstant.GD_CK_TICKET.equals(c.getName())){
					ticket=c.getValue();
					log.debug("************ticket from cookie:"+ticket);
					break;
				}
			}
			if(refererUrl.indexOf("?")>0 && refererUrl.indexOf(SSOConstant.GD_CK_TICKET+"=")<0){
				refererUrl=refererUrl+"&"+SSOConstant.GD_CK_TICKET+"="+URLEncoder.encode(ticket,"utf-8");
			}
			else if(refererUrl.indexOf(SSOConstant.GD_CK_TICKET+"=")<0){
				refererUrl=refererUrl+"?"+SSOConstant.GD_CK_TICKET+"="+URLEncoder.encode(ticket,"utf-8");
			}
			log.info("(returnUrl)Send redirect:"+refererUrl);
			response.sendRedirect(refererUrl);
			return result;
		}
		else{
			
			result.put("refererUrl", "");
		}
		return result;
	}
    /*****
     * 
     * @param request
     * @return
     * @throws Exception
     */
	@Ok("fm:")
	@At("/login")
	@Filters({})
	public Object login(HttpServletRequest request) throws Exception{
		Map<String,Object> result=new Hashtable<String, Object>();
		String refererUrl=request.getParameter("returnUrl");
		
		log.info("-----------refererUrl----------"+refererUrl);
		if(Strings.isEmpty(refererUrl)==false){
			result.put("refererUrl", refererUrl);
		}
		else{
			result.put("refererUrl", "");
		}
		return result;
	}
	@Ok("redirect:/index")
	@At("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response,Ioc ioc){
		String ticket=(String)request.getAttribute(SSOConstant.GD_ATTR_TICKET);
		if(Strings.isEmpty(ticket)){
			ticket=request.getHeader(SSOConstant.HD_GD_EHRSSO_TICKET);
		}
		if(Strings.isEmpty(ticket)){
			ticket=request.getParameter(SSOConstant.GD_CK_TICKET);
		}
		if(Strings.isEmpty(ticket)){
			Cookie[] cookies=request.getCookies();
			for (Cookie c : cookies) {
				if(SSOConstant.GD_CK_TICKET.equals(c.getName())){
					ticket=c.getValue();
					Cookie cookie=new Cookie(SSOConstant.GD_CK_TICKET,"");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
		}
		if(!Strings.isEmpty(ticket)){
			TicketService ticketService=ioc.get(TicketService.class,"ticketService");
			try {
				ticketService.removeTicket(ticket);
				log.info("removeTicket:"+ticket);
			} catch (MyException e) {
				log.error("removeTicket error:"+ticket, e);
			}
		}
		else{
			
		}
		return "index";
	}
	/*****
	 * 
	 * @param userName
	 * @param password
	 * @param refererUrl
	 * @param request
	 * @param response
	 * @param ioc
	 * @return
	 * @throws MyException
	 */
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
			if(!accountService.validate(userName, password, null)){//��֤�Ƿ��ɴ˿ͻ�
				throw new MyException("USER_OR_PWD_INVALID","�û�����������");
			}
			String ticket=ticketService.generateTicket(userName, 600);//���ticket ��������ݿ�
			Cookie cookie=new Cookie(SSOConstant.GD_CK_TICKET,ticket);//���û�����cookie
			cookie.setMaxAge(1200*1000);
			response.addCookie(cookie);
			response.addHeader(SSOConstant.HD_GD_EHRSSO_TICKET, ticket);
			response.addHeader(SSOConstant.HD_GD_EHRSSO_USERNAME, userName);
			//��֤�ɹ������ԭ��ҳ�棬����urlЯ����ticket
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
			return  result;//����
			
		} catch (MyException e) {
			log.error(e);
			throw e;
		} catch (Exception e) {
			log.error(e);
			return e;
		}

	}
	
	/******
	 * 
	 * @param accessKey
	 * @param ioc
	 * @param request
	 * @return
	 */
	
	
	@Ok("json")
	@At("/validate/?")
	@Fail("json")
	@POST
	@Filters({@By(type=SiteAuthorizationFilter.class)})
	public Map<String,Object> validate(String accessKey,Ioc ioc,HttpServletRequest request){
		String remoteHost= request.getRemoteHost();
		log.info("Do validate for accessKey:"+accessKey+" from host "+remoteHost);
		
		String ticket=request.getHeader(SSOConstant.HD_GD_EHRSSO_TICKET);
		Map<String,Object> result=new Hashtable<String,Object>();
		if(Strings.isEmpty(ticket)){
			result.put("code", "failed");
			result.put("message", "The ticket is required.");
			log.warn("The ticket is required.");
			return result;
		}
		TicketService ticketService=ioc.get(TicketService.class,"ticketService");
		ticket=ticket.replace(' ', '+');
		log.info("---v---t---"+ticket);
		try {
			if(!ticketService.validate(ticket)){
				
				result.put("code", "failed");
				result.put("message", "The ticket is invalid.");
				log.warn("The ticket is invalid."+ticket);
				return result;
			}
			String userName=ticketService.trimUserNameFromTicket(ticket);
			result.put("userName", userName);
		} catch (MyException e) {
			result.put("code", "failed");
			result.put("message", e.getLocalizedMessage());
			log.warn("failed...",e);
			return result;
		}
		result.put("code", "success");
		result.put("message","titcket is valided.");
		log.info("validate successfully!");
		return result;
	}
	
	
	/***
	 * 
	 * @param accessKey
	 * @param ioc
	 * @param request
	 * @return
	 */
	@Ok("json")
	@At("/doLogout/?")
	@Fail("json")
	@POST
	@Filters({@By(type=SiteAuthorizationFilter.class)})
	public Map<String,Object> doLogout(String accessKey,Ioc ioc,HttpServletRequest request){
		String remoteHost= request.getRemoteHost();
		log.info("Do validate for accessKey:"+accessKey+" from host "+remoteHost);
		
		String ticket=request.getHeader(SSOConstant.HD_GD_EHRSSO_TICKET);
		
		Map<String,Object> result=new Hashtable<String,Object>();
		if(Strings.isEmpty(ticket)){
			result.put("code", "failed");
			result.put("message", "The ticket is required.");
			log.warn("The ticket is required.");
			return result;
		}
		TicketService ticketService=ioc.get(TicketService.class,"ticketService");
		try {
			ticketService.removeTicket(ticket);
			String userName=ticketService.trimUserNameFromTicket(ticket);
			log.info("The ticket of "+userName+" is removed");
			result.put("userName", userName);
		} catch (MyException e) {
			result.put("code", "failed");
			result.put("message", e.getLocalizedMessage());
			log.warn("failed...",e);
			return result;
		}
		result.put("code", "success");
		result.put("message","titcket is removed.");
		log.info("doLogout successfully!");
		return result;
	}
	

	

	


	
	
	
}
