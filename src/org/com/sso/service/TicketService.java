package org.com.sso.service;

import org.com.sso.util.MyException;

public class TicketService implements ITicketService {

	@Override
	public String generateTicket(String userName, int timeout)
			throws MyException {
		// TODO Auto-generated method stub
		return null;
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
