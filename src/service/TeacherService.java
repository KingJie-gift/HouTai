package service;

import java.util.List;

import entity.Page;
import entity.Teacher;


public interface TeacherService {
	List<Teacher> getAll(int isdelete);

	Teacher getById(int id);

	int add(Teacher t);

	int delete(int teacherId);

	int update(Teacher t);
	
	void get4Page(Page page,String teacherName,int subjectId);
	int getAllCount();

	int checkname(String teacherName);
	
	int delUpdate(int id);
}
