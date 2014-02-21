/**
 * 
 */
package org.com.sso.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Wang PiaoYang
 * 站点接入�?
 */

@Table("SSO_ACCESS_KEY")
public class AccessKeyInfo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -938418622183220244L;
	
	
	@Id(auto=false)
	private long id;
	
	@Column
	private int siteId;
	
	@Name
	
	private String accessKey;
	
	@Column

	private String value;
	
	@Column
	private int disabled=0;
	
	@Column
	
	private String createdBy;
	
	@Column
	
	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	@Column

	private Timestamp lastUpdateTime;
	
	
	private SiteInfo site;

}
