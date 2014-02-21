/**
 * 
 */
package org.com.sso.util;

import org.com.sso.service.ISiteService;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.HttpStatusView;

/**
 * @author WangPiaoyang
 *
 */
public class SiteAuthorizationFilter implements ActionFilter {

	/* (non-Javadoc)
	 * @see org.nutz.mvc.ActionFilter#match(org.nutz.mvc.ActionContext)
	 */
	public View match(ActionContext context) {
		String date=context.getRequest().getHeader("Date");
		String authorization=context.getRequest().getHeader("Authorization");
		String accessKey="";
		String accessValue="";
		if(authorization!=null && authorization.indexOf(":")>0){
			accessKey=authorization.substring(0,authorization.indexOf(":"));
			ISiteService siteService=context.getIoc().get(ISiteService.class,"siteService");
			accessValue=siteService.fetchAccessKey(accessKey);
			
		}
		if(Strings.isEmpty(accessValue)){
			return new HttpStatusView(401);
		}
		String auth=Lang.md5(date+":"+accessValue);
		if(!auth.equals(authorization.substring(authorization.indexOf(":")+1))){
			return new HttpStatusView(401);
		}
		return null;
	}

}
