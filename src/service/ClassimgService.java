package service;

import java.util.List;

import entity.Classimg;
import entity.Page;

public interface ClassimgService {
	List<Classimg>getAll();
	int add(Classimg c);
	int delete(int imgId);
	int update(Classimg c);
	Classimg getById(int imgId);
	
	int getAllCount();
	void get4Page(Page page);
}
