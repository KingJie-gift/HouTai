package dao;

import java.util.List;

import entity.Page;
import entity.Subject;

public interface SubjectDao {
	List<Subject> getAll();

	Subject getById(int id);

	int add(Subject s);

	int update(Subject s);

	int delete(int id);

	int getAllcount(String subjectName);

	void get4page(Page page,String subjectName);
	
	
}
