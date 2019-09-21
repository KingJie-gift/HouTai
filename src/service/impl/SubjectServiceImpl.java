package service.impl;

import java.util.List;

import dao.impl.SubjectDaoImpl;
import entity.Page;
import entity.Subject;
import service.SubjectService;

public class SubjectServiceImpl implements SubjectService {

	SubjectDaoImpl sdi = new SubjectDaoImpl();

	@Override
	public List<Subject> getAll() {
		// TODO Auto-generated method stub
		return sdi.getAll();
	}

	@Override
	public Subject getById(int id) {
		// TODO Auto-generated method stub
		return sdi.getById(id);
	}

	@Override
	public int add(Subject s) {
		// TODO Auto-generated method stub
		return sdi.add(s);
	}

	@Override
	public int update(Subject s) {
		// TODO Auto-generated method stub
		return sdi.update(s);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return sdi.delete(id);
	}

	@Override
	public int getAllcount(String subjectName) {
		// TODO Auto-generated method stub
		return sdi.getAllcount(subjectName);
	}

	@Override
	public void get4page(Page page, String subjectName) {
		// TODO Auto-generated method stub
		sdi.get4page(page, subjectName);
	}

}
