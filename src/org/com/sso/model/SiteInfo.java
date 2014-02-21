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

}
