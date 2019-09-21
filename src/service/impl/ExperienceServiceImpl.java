package service.impl;

import java.util.List;

import dao.impl.ExperienceDaoImpl;
import entity.Experience;
import entity.Page;
import service.ExperienceService;

public class ExperienceServiceImpl implements ExperienceService {
	ExperienceDaoImpl edi = new ExperienceDaoImpl();

	@Override
	public int add(Experience e) {
		// TODO Auto-generated method stub
		return edi.add(e);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return edi.delete(id);
	}

	@Override
	public List<Experience> getAll() {
		// TODO Auto-generated method stub
		return edi.getAll();
	}

	@Override
	public Experience getById(int id) {
		// TODO Auto-generated method stub
		return edi.getById(id);
	}

	@Override
	public int update(Experience e) {
		// TODO Auto-generated method stub
		return edi.update(e);
	}

	@Override
	public void get4Page(Page page,int subjectId) {
		edi.get4Page(page,subjectId);

	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return edi.getAllCount();
	}

}
