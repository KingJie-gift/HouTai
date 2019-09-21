package service.impl;

import java.util.List;

import service.NewsService;
import dao.BaseDao;
import dao.impl.NewsDaoimpl;
import entity.News;
import entity.Page;

public class NewsServiceimpl extends BaseDao implements NewsService{
NewsDaoimpl ndi=new NewsDaoimpl();
	@Override
	public List<News> getAll() {
		// TODO Auto-generated method stub
		return ndi.getAll();
	}

	@Override
	public int add(News n) {
		// TODO Auto-generated method stub
		return ndi.add(n);
	}

	@Override
	public int delete(int newsId) {
		// TODO Auto-generated method stub
		return ndi.delete(newsId);
	}

	@Override
	public int update(News n) {
		// TODO Auto-generated method stub
		return ndi.update(n);
	}

	@Override
	public News getById(int newsId) {
		// TODO Auto-generated method stub
		return ndi.getById(newsId);
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return ndi.getAllCount();
	}

	@Override
	public void get4Page(Page page,String title,int subjectId) {
		ndi.get4Page(page,title,subjectId);
	}

}
