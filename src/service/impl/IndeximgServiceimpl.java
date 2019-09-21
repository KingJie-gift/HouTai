package service.impl;

import java.util.List;

import service.IndeximgService;
import dao.BaseDao;
import dao.impl.IndeximgDaoimpl;
import entity.Indeximg;
import entity.Page;

public class IndeximgServiceimpl extends BaseDao implements IndeximgService{
IndeximgDaoimpl idi=new IndeximgDaoimpl();
	@Override
	public List<Indeximg> getAll() {
		// TODO Auto-generated method stub
		return idi.getAll();
	}

	@Override
	public int add(Indeximg i) {
		// TODO Auto-generated method stub
		return idi.add(i);
	}

	@Override
	public int delete(int imgId) {
		// TODO Auto-generated method stub
		return idi.delete(imgId);
	}

	@Override
	public int update(Indeximg i) {
		// TODO Auto-generated method stub
		return idi.update(i);
	}

	@Override
	public Indeximg getById(int imgId) {
		// TODO Auto-generated method stub
		return idi.getById(imgId);
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return idi.getAllCount();
	}

	@Override
	public void get4Page(Page page) {
		// TODO Auto-generated method stub
		idi.get4Page(page);
	}

}
