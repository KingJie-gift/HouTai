package service;

import java.util.List;

import entity.Communicate;
import entity.Page;

public interface CommunicateService {
	int add(Communicate c);
	Communicate getById(int id);
	int update(Communicate c);
	int delete(int id);
	int getAllcount(String title);

	void get4page(Page page,String title,int subjectId);
	
}
