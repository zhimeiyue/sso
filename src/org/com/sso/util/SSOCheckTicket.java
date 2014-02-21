/**
 * 
 */
package org.com.sso.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;

import org.com.sso.service.TicketService;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ServerRedirectView;


/**
 * @author Wang Piaoyang
 *
 */
public class SSOCheckTicket implements ActionFilter {

	private String path="/login";
	private static  Log logger=Logs.getLog(SSOCheckTicket.class);
	
	public SSOCheckTicket(){
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.nutz.mvc.ActionFilter#match(org.nutz.mvc.ActionContext)
	 */
	public View match(ActionContext actioncontext) {
		if(actioncontext.getRequest()!=null){
			String ticket=(String)actioncontext.getRequest().getAttribute(SSOConstant.GD_ATTR_TICKET);
			
			
			/////取ticket
			if(Strings.isEmpty(ticket)){
				ticket=actioncontext.getRequest().getHeader(SSOConstant.HD_GD_EHRSSO_TICKET);
				logger.debug("************ticket from header:"+ticket);
			}
			if(Strings.isEmpty(ticket)){
				ticket=actioncontext.getRequest().getParameter(SSOConstant.GD_CK_TICKET);
				if(Strings.isEmpty(ticket)==false){
					try {
						ticket=URLDecoder.decode(ticket, "utf-8");
					} catch (UnsupportedEncodingException e) {
						logger.warn(e);
					}
					logger.debug("************ticket from param:"+ticket);
				}
			}
			if(Strings.isEmpty(ticket)){
				Cookie[] cookies=actioncontext.getRequest().getCookies();
				if(cookies!=null){
					for (Cookie c : cookies) {
						if(SSOConstant.GD_CK_TICKET.equals(c.getName())){
							ticket=c.getValue();
							logger.debug("************ticket from cookie:"+ticket);
							break;
						}
					}
				}
			}
			logger.info("---------ticket:"+ticket);
			//////把cookie设置ticket
			String returnUrl=actioncontext.getRequest().getParameter("returnUrl");
			if(Strings.isEmpty(ticket)){
				logger.info("SSO ticket is empty.");
				if(false==Strings.isEmpty(returnUrl)){
					
					return new ServerRedirectView(path+"?returnUrl="+returnUrl);
				}
				return new ServerRedirectView(path);
			}
			TicketService ticketService=actioncontext.getIoc().get(TicketService.class,"ticketService");
			try {
				if(ticketService.validate(ticket)){
					if(actioncontext.getResponse()!=null){
						Cookie cookie=new Cookie(SSOConstant.GD_CK_TICKET,ticket);
						cookie.setMaxAge(1200*1000);
						actioncontext.getResponse().addCookie(cookie);
						actioncontext.getResponse().addHeader(SSOConstant.HD_GD_EHRSSO_TICKET, ticket);
						String userName=ticketService.trimUserNameFromTicket(ticket);
						actioncontext.getResponse().addHeader(SSOConstant.HD_GD_EHRSSO_USERNAME, userName);
					}
					
					return null;
				}
				else{
					Cookie cookie=new Cookie(SSOConstant.GD_CK_TICKET,"");
					cookie.setMaxAge(0);
					actioncontext.getResponse().addCookie(cookie);
					if(false==Strings.isEmpty(returnUrl)){
						
						return new ServerRedirectView(path+"?returnUrl="+returnUrl);
					}
					return new ServerRedirectView(path);
				}
			} catch (MyException e) {
				logger.info(e.getCode()+"------ssoe------"+e.getMessage());
				Cookie cookie=new Cookie(SSOConstant.GD_CK_TICKET,"");
				cookie.setMaxAge(0);
				actioncontext.getResponse().addCookie(cookie);
				if(false==Strings.isEmpty(returnUrl)){
					
					return new ServerRedirectView(path+"?returnUrl="+returnUrl);
				}
				return new ServerRedirectView(path);
			}
			
		}
		
		return null;
	}

}
