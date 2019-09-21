package service;

import java.util.List;

import entity.News;
import entity.Page;

public interface NewsService {
	List<News>getAll();
	int add(News n);
	int delete(int newsId);
	int update(News n);
	News getById(int newsId);

	int getAllCount();
	void get4Page(Page page,String title,int subjectId);
}
