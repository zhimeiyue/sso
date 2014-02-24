package org.com.sso.service;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Hex;
import org.com.sso.model.TicketInfo;
import org.com.sso.util.MyException;
import org.com.sso.util.TripleDES;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;



public class TicketService implements ITicketService {
	 Log log=Logs.getLog(TicketService.class);
	
	@Inject
	private Dao dao;
	@Override
	public String generateTicket(String userName, int timeout)
			throws MyException {
			//算法：Hex(3des(utf8({userName})))+":"+md5(Hex(utf8({userName}+":"+{当前时间字符串})))
			StringBuilder sb=new StringBuilder();
			log.info("generate Ticket for user:"+userName);
			TripleDES tdes=new TripleDES();
			try {
				byte[] buff=tdes.encrypt(userName);
				
				sb.append(Hex.encodeHexString(buff)+":");
				
			} catch (Exception e) {
				throw new MyException("FAILED_TDES_ENCRYPT",e.getMessage());
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String ticketBody=userName+":"+sdf.format(new Date(System.currentTimeMillis()));
			try {
				ticketBody=Hex.encodeHexString(ticketBody.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				new MyException("UNSUPPORTED_ENCODING_UTF8",e.getMessage());
			}
			sb.append(Lang.md5(ticketBody));
			if(timeout<=0){
				timeout=600;
			}
			
			TicketInfo oldTicket=dao.fetch(TicketInfo.class, userName);
			if(oldTicket!=null){
				dao.delete(oldTicket);
			}
			
			TicketInfo ticket=new TicketInfo();
			ticket.setDisabled(0);
			ticket.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			ticket.setCreateTime(ticket.getLastUpdateTime());
			ticket.setticket(sb.toString());
			ticket.setTimeout(timeout);
			ticket.setUserName(userName);
			dao.insert(ticket);
			log.info("generated Ticket:"+sb.toString());
			return sb.toString();
	}

	@Override
	public boolean validate(String ticket) throws MyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String fetchTicket(String userName) throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeTicket(String ticket) throws MyException {
		// TODO Auto-generated method stub

	}

	@Override
	public String trimUserNameFromTicket(String ticket) throws MyException {
		// TODO Auto-generated method stub
		return null;
	}

}
