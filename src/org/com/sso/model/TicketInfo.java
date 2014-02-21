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

}
