package dao;

import java.util.List;

import entity.Experience;
import entity.Page;

public interface ExperienceDao {
	int add(Experience e);

	int delete(int id);

	List<Experience> getAll();

	Experience getById(int id);

	int update(Experience e);

	void get4Page(Page page,int subjectId);

	int getAllCount();
}
