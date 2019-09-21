package dao;

import java.util.List;

import entity.Indeximg;
import entity.Page;

public interface IndeximgDao {

	List<Indeximg>getAll();
	int add(Indeximg i);
	int delete(int imgId);
	int update(Indeximg i);
	Indeximg getById(int imgId);
	
	int getAllCount();
	void get4Page(Page page);
}
