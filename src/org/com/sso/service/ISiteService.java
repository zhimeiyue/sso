/**
 * 
 */
package org.com.sso.service;

import java.util.Map;

import org.com.sso.util.MyException;

import com.gd.ehrsso.po.AccessKeyInfo;
import com.gd.ehrsso.po.AuditInfo;
import com.gd.ehrsso.po.SiteInfo;

/**
 * @author Wang PiaoYang
 * SSO鎴愬憳绔欑偣淇℃伅绠＄悊锛屽寘鎷?
 * 1.鎴愬憳绔欑偣娉ㄥ唽
 * 2.鎴愬憳绔欑偣瀹℃牳
 * 3.鎴愬憳绔欑偣鎺ュ叆鐮佺鐞嗭紙鎺ュ叆鐮佺敤浜庢垚鍛樼珯鐐逛笌SSO閫氳鍚堟硶鎬ч獙璇侊級
 * 4.鎴愬憳绔欑偣淇℃伅缂栬緫
 */
public interface ISiteService {
	
	/**
	 * 绔欑偣绠＄悊鍛樻坊鍔犳垚鍛樼珯鐐?
	 * @param site
	 * @throws SSOException
	 */
	void add(SiteInfo site) throws MyException;
	
	/**
	 * 绔欑偣绠＄悊鍛樼紪杈戞垚鍛樼珯鐐?
	 * @param site
	 * @throws SSOException
	 */
	void update(SiteInfo site) throws MyException;
	
	/**
	 * 绔欑偣绠＄悊鍛樺鏍革紝鎴愬憳绔欑偣瀹℃牳淇℃伅锛岃褰曞鏍告搷浣滐紝骞舵洿鏂扮珯鐐逛俊鎭?
	 * @param audit
	 * @throws SSOException
	 */
	void approval(AuditInfo audit) throws MyException;
	
	/**
	 * 绔欑偣绠＄悊浜哄憳鐢熸垚鎺ュ叆鐮?
	 * @param accessKey
	 * @throws SSOException
	 */
	void addAccessKey(AccessKeyInfo accessKey)throws MyException;
	
	/**
	 * 绔欑偣绠＄悊鍛橀噸鏂扮敓鎴愭帴鍏ョ爜锛屾帴鍏ョ爜涓簁ey/value妯″瀷
	 * @param accessKey
	 * @throws SSOException
	 */
	void updateAccessKey(AccessKeyInfo accessKey)throws MyException;
	
	/**
	 * 鎺ュ叆鐮侀獙璇?
	 * @param accessKey
	 * @param secretValue
	 * @return
	 * @throws SSOException
	 */
	boolean  validateAccessKey(String accessKey,String secretValue) throws MyException;
	
	Map<String,Object> siteList(int start ,int count);
	
	String fetchAccessKey(String accessKey);
	
	SiteInfo fetchSiteByAccessKey(String accessKey);
}
