package service.impl;

import java.util.List;

import service.ClassimgService;
import dao.BaseDao;
import dao.impl.ClassimgDaoimpl;
import entity.Classimg;
import entity.Page;

public class ClassimgServiceimpl extends BaseDao implements ClassimgService{
ClassimgDaoimpl cdi=new ClassimgDaoimpl();
	@Override
	public List<Classimg> getAll() {
		// TODO Auto-generated method stub
		return cdi.getAll();
	}

	@Override
	public int add(Classimg c) {
		// TODO Auto-generated method stub
		return cdi.add(c);
	}

	@Override
	public int delete(int imgId) {
		// TODO Auto-generated method stub
		return cdi.delete(imgId);
	}

	@Override
	public int update(Classimg c) {
		// TODO Auto-generated method stub
		return cdi.update(c);
	}

	@Override
	public Classimg getById(int imgId) {
		// TODO Auto-generated method stub
		return cdi.getById(imgId);
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return cdi.getAllCount();
	}

	@Override
	public void get4Page(Page page) {
		// TODO Auto-generated method stub
		cdi.get4Page(page);
	}

}
