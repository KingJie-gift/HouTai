package service.impl;

import java.util.List;

import dao.impl.TeacherDaoImpl;
import entity.Page;
import entity.Teacher;
import service.TeacherService;

public class TeacherServiceImpl implements TeacherService {

	TeacherDaoImpl tdi=new TeacherDaoImpl();
	
	@Override
	public List<Teacher> getAll(int isdelete) {
		// TODO Auto-generated method stub
		return tdi.getAll(isdelete);
	}

	@Override
	public Teacher getById(int id) {
		// TODO Auto-generated method stub
		return tdi.getById(id);
	}

	@Override
	public int add(Teacher t) {
		// TODO Auto-generated method stub
		return tdi.add(t);
	}

	@Override
	public int delete(int teacherId) {
		// TODO Auto-generated method stub
		return tdi.delete(teacherId);
	}

	@Override
	public int update(Teacher t) {
		// TODO Auto-generated method stub
		return tdi.update(t);
	}

	@Override
	public void get4Page(Page page, String teacherName, int subjectId) {
		tdi.get4Page(page, teacherName, subjectId);
		
	}

	@Override
	public int getAllCount() {
		// TODO Auto-generated method stub
		return tdi.getAllCount();
	}

	@Override
	public int checkname(String teacherName) {
		// TODO Auto-generated method stub
		return tdi.checkname(teacherName);
	}

	@Override
	public int delUpdate(int id) {
		// TODO Auto-generated method stub
		return tdi.delUpdate(id);
	}

}
