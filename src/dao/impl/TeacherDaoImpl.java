package dao.impl;

import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.TeacherDao;
import entity.Page;
import entity.Subject;
import entity.Teacher;

public class TeacherDaoImpl extends BaseDao implements TeacherDao {

	SubjectDaoImpl sdi = new SubjectDaoImpl();

	@Override
	public List<Teacher> getAll(int isDelete) {
		String sql = "SELECT * FROM teacher WHERE isDelete=0";
		List<Teacher> list = new ArrayList<Teacher>();
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				Teacher t = new Teacher();
				t.setTeacherId(rs.getInt("teacherId"));
				t.setTeacherName(rs.getString("teacherName"));
				t.setSubjectId(rs.getInt("subjectId"));
				t.setIntroduce(rs.getString("introduce"));
				t.setTeacherImg(rs.getString("teacherImg"));
				t.setShortIntroduce(rs.getString("shortIntroduce"));
				t.setSubject(sdi.getById(t.getSubjectId()));
				list.add(t);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return list;
	}

	@Override
	public Teacher getById(int id) {
		String sql = "select * from teacher where teacherId = ?";
		Teacher t = new Teacher();
		this.executeQuery(sql, id);
		try {
			while (rs.next()) {
				t = new Teacher();
				t.setTeacherId(rs.getInt("teacherId"));
				t.setTeacherName(rs.getString("teacherName"));
				t.setSubjectId(rs.getInt("subjectId"));
				t.setIntroduce(rs.getString("introduce"));
				t.setTeacherImg(rs.getString("teacherImg"));
				t.setShortIntroduce(rs.getString("shortIntroduce"));
				t.setSubject(sdi.getById(t.getSubjectId()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return t;
	}

	@Override
	public int add(Teacher t) {
		String sql = "INSERT INTO teacher (teacherName,subjectId,introduce,shortIntroduce,teacherImg,isDelete) VALUES(?,?,?,?,?,0)";
		int result = this.executeUpdate(sql, t.getTeacherName(),
				t.getSubjectId(), t.getIntroduce(), t.getShortIntroduce(),
				t.getTeacherImg());
		return result;
	}

	@Override
	public int delete(int teacherId) {
		int result = 0;
		String sql = "DELETE FROM teacher WHERE teacherId=?";
		result = this.executeUpdate(sql, teacherId);
		return result;
	}

	@Override
	public int update(Teacher t) {
		int result = 0;
		String sql = "UPDATE Teacher SET teacherName=?,subjectId=?,introduce=?,shortIntroduce=?,teacherImg=?,isDelete=? WHERE teacherId=?";
		result = this.executeUpdate(sql, t.getTeacherName(), t.getSubjectId(),
				t.getIntroduce(), t.getShortIntroduce(), t.getTeacherImg(),
				t.getIsDelete(), t.getTeacherId());
		return result;
	}

	@Override
	public void get4Page(Page page, String teacherName, int subjectId) {
		List<Teacher> list = new ArrayList<Teacher>();
		try {
			String sql = "SELECT * FROM Teacher WHERE isDelete=0 and 1=1 ";
			if (teacherName != null && teacherName.length() != 0) {
				sql = sql + " and teacherName LIKE '%" + teacherName + "%'  ";
			}
			if (subjectId != -1) {
				sql = sql + " and subjectId =" + subjectId;
			}
			sql = sql + " limit ?,?";
			this.executeQuery(sql,
					(page.getPageIndex() - 1) * page.getPageSize(),
					page.getPageSize());
			while (rs.next()) {
				Teacher t = new Teacher();
				t.setTeacherId(rs.getInt("teacherId"));
				t.setTeacherName(rs.getString("teacherName"));
				t.setSubjectId(rs.getInt("subjectId"));
				t.setIntroduce(rs.getString("introduce"));
				t.setTeacherImg(rs.getString("teacherImg"));
				t.setShortIntroduce(rs.getString("shortIntroduce"));
				Subject subject = sdi.getById(rs.getInt("subjectId"));
				t.setSubject(subject);
				list.add(t);
			}
			page.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll();
		}

	}

	@Override
	public int getAllCount() {
		int count = 0;
		try {
			String sql = "SELECT count(*) FROM Teacher where isDelete=0";
			this.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return count;
	}

	@Override
	public int checkname(String teacherName) {
		int count = 0;
		try {
			String sql = "SELECT count(*) FROM Teacher where teacherName=?";
			this.executeQuery(sql, teacherName);
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return count;
	}

	@Override
	public int delUpdate(int id) {
		String sql = "UPDATE Teacher SET isDelete=1 WHERE teacherId=?";
		return this.executeUpdate(sql, id);
	}

}
