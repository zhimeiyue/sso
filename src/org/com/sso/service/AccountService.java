package org.com.sso.service;

import org.com.sso.util.MyException;

public class AccountService implements IAccountService {

	@Override
	public boolean validate(String userName, String password, String userType)
			throws MyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void register(String userName, String password, String userType,
			String email) throws MyException {
		// TODO Auto-generated method stub

	}

}
