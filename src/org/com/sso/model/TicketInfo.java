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
 * @author Wang Piaoyang
 *
 */

@Table("sso_ticket")
public class TicketInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4343627965141024922L;
	
	@Id(auto=false)

	private long id;
	
	@Name

	private String userName;
	
	@Column

	private String ticket;
	
	@Column

	private int timeout;
	

	@Column

	private int disabled=0;
	

	@Column

	private String createdBy;
	
	@Column

	private Timestamp createTime=new Timestamp(System.currentTimeMillis());
	
	@Column
	private Timestamp lastUpdateTime;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	
	
	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
