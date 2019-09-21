package service.impl;

import dao.impl.AdminDaoImpl;
import entity.Admin;
import service.AdminService;

public class AdminServiceImpl implements AdminService {

	AdminDaoImpl adi=new AdminDaoImpl();
	
	@Override
	public int check(Admin a) {
		// TODO Auto-generated method stub
		return adi.check(a);
	}

}
