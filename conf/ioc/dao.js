var ioc={
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{depose:"close"},
		fields:{
			driverClassName:"com.mysql.jdbc.Driver",
			url:"jdbc:mysql://127.0.0.1:3306/zhimeiyue?useUnicode=true&characterEncoding=utf8",
			username:"root",
			password:"1369",
			initialSize:10,
			maxActive:100,
			testOnReturn:true,
			validationQuery:"select 1"
		}
	},
	dao:{
		type:"org.nutz.dao.impl.NutDao",
		args:[{refer:"dataSource"}]
	}
	
}
