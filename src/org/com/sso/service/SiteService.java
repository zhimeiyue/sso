/**
 * 
 */
package org.com.sso.service;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.com.sso.model.AccessKeyInfo;
import org.com.sso.model.SiteInfo;
import org.com.sso.util.MyException;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.StringGenerator;



/**
 * @author Wang Piaoyang
 *
 */
@IocBean(name="siteService")
public class SiteService implements ISiteService {

	@Inject
	private Dao dao=null;
	
	
	@Override
	public void add(SiteInfo site) throws MyException {
		site=dao.insert(site);
	}

	@Override
	public void update(SiteInfo site) throws MyException {
		int ret=dao.updateIgnoreNull(site);
		if(ret!=0){
			throw new MyException("FAILED_SITE_UPDATE", "更新字典错误！");
		}
	}

//	@Override
//	public void approval(AuditInfo audit) throws SSOException {
//		final AuditInfo newAudit=audit;
//		final SiteInfo site=dao.fetch(SiteInfo.class, newAudit.getSiteId());
//		site.setDisabled(newAudit.getApproved()>0?0:1);
//		site.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
//		//事务模板
//		Trans.exec(new Atom(){
//			public void run() {
//				dao.insert(newAudit);
//				dao.update(site);
//				newAudit.setId(newAudit.getId());
//			}
//		});
//	}

	@Override
	public void addAccessKey(AccessKeyInfo accessKey) throws MyException {
		StringGenerator strGen=new StringGenerator(20,20);
		if(Strings.isEmpty(accessKey.getAccessKey())){
			String key=strGen.next();
			key=Lang.md5(key);
			accessKey.setAccessKey(key);
			
		}
		String keyValue=accessKey.getAccessKey()+"-"+strGen.next();
		keyValue=Lang.md5(keyValue);
		accessKey.setValue(keyValue);
		dao.insert(accessKey);
	}

	@Override
	public void updateAccessKey(AccessKeyInfo accessKey) throws MyException {
		StringGenerator strGen=new StringGenerator(20,20);
		String keyValue=accessKey.getAccessKey()+"-"+strGen.next();
		keyValue=Lang.md5(keyValue);
		accessKey.setValue(keyValue);
		int ret=dao.update(accessKey);
		if(ret!=0){
			throw new MyException("FAILED_ACCESSKEY_UPDATE", "更新接入码错误！");
		}
	}

	@Override
	public boolean validateAccessKey(String accessKey, String secretValue)
			throws MyException {
		AccessKeyInfo access= dao.fetch(AccessKeyInfo.class, accessKey);
		if(access==null){
			return false;
		}
		return access.getValue().equals(secretValue);
	}
	@Override
	public String fetchAccessKey(String accessKey) {
		AccessKeyInfo access= dao.fetch(AccessKeyInfo.class, accessKey);
		if(access==null){
			return "";
		}
		return access.getValue();
	}
	@Override
	public SiteInfo fetchSiteByAccessKey(String accessKey) {
		AccessKeyInfo access= dao.fetch(AccessKeyInfo.class, accessKey);
		if(access==null){
			return null;
		}
		access=dao.fetchLinks(access, "site");
		return access.getSite();
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Override
	public Map<String, Object> siteList(int pageNumber, int pageSize) {
		Pager pager= dao.createPager(pageNumber, pageSize);
		List<SiteInfo> lsRet=dao.query(SiteInfo.class, Cnd.orderBy().desc("lastUpdateTime"), pager);
		if(lsRet!=null && lsRet.size()>0){
			pager.setRecordCount(dao.count(SiteInfo.class));
		}
		Map<String, Object> ret=new Hashtable<String, Object>();
		ret.put("pager", pager);
		ret.put("list", lsRet);
		return ret;
	}
	
}
