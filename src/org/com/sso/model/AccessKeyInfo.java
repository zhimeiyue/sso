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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	
	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	
	
	public SiteInfo getSite() {
		return site;
	}

	public void setSite(SiteInfo site) {
		this.site = site;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;	
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
