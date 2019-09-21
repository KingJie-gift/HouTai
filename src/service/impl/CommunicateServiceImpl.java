package service.impl;

import java.util.List;

import dao.impl.CommunicateDaoImpl;
import entity.Communicate;
import entity.Page;
import service.CommunicateService;

public class CommunicateServiceImpl implements CommunicateService{
CommunicateDaoImpl i=new CommunicateDaoImpl();
	@Override
	public int add(Communicate c) {
		// TODO Auto-generated method stub
		return i.add(c);
	}

	@Override
	public Communicate getById(int id) {
		// TODO Auto-generated method stub
		return i.getById(id);
	}

	@Override
	public int update(Communicate c) {
		// TODO Auto-generated method stub
		return i.update(c);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return i.delete(id);
	}

	@Override
	public int getAllcount(String title) {
		// TODO Auto-generated method stub
		return i.getAllcount(title);
	}

	@Override
	public void get4page(Page page,String title,int subjectId) {
		// TODO Auto-generated method stub
		i.get4page(page,title,subjectId);
	}



}
