package service;

import java.util.List;

import entity.Indeximg;
import entity.Page;

public interface IndeximgService {

	List<Indeximg>getAll();
	int add(Indeximg i);
	int delete(int imgId);
	int update(Indeximg i);
	Indeximg getById(int imgId);
	
	int getAllCount();
	void get4Page(Page page);
}
