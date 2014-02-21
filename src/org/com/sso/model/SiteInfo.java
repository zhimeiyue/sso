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
 *
 */

@Table("SSO_SITE")
public class SiteInfo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id(auto=false)

	private int siteId;
	
	@Name

	private String name;
	
	@Column

	private int disabled=0;
	
	@Column

	private String description;
	
	@Column

	private String createdBy;

	@Column

	private String hostUrl;
	
	@Column

	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	@Column

	private Timestamp lastUpdateTime;
	
	
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
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
	

}
