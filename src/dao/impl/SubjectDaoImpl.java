package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.SubjectDao;
import entity.Page;
import entity.Subject;

public class SubjectDaoImpl extends BaseDao implements SubjectDao {

	@Override
	public List<Subject> getAll() {
		String sql = "SELECT * FROM subject";
		this.executeQuery(sql);
		List<Subject> list = new ArrayList<Subject>();
		try {
			while (rs.next()) {
				Subject s = new Subject();
				s.setSubjectId(rs.getInt("subjectId"));
				s.setSubjectName(rs.getString("subjectName"));
				s.setSubjectTitle(rs.getString("subjectTitle"));
				s.setSmallImg(rs.getString("smallImg"));
				s.setBackgroundImg(rs.getString("backgroundImg"));
				s.setSubjectContent(rs.getString("subjectContent"));
				list.add(s);
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
	public Subject getById(int id) {
		String sql = "SELECT * FROM subject where subjectId = ?";
		this.executeQuery(sql, id);
		Subject s = null;
		try {
			while (rs.next()) {
				s = new Subject();
				s.setSubjectId(rs.getInt("subjectId"));
				s.setSubjectName(rs.getString("subjectName"));
				s.setSubjectTitle(rs.getString("subjectTitle"));
				s.setSmallImg(rs.getString("smallImg"));
				s.setBackgroundImg(rs.getString("backgroundImg"));
				s.setSubjectContent(rs.getString("subjectContent"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return s;
	}

	@Override
	public int add(Subject s) {
		String sql = "insert into subject(subjectTitle,smallImg,backgroundImg,subjectContent,subjectName)values(?,?,?,?,?)";
		int result = this.executeUpdate(sql, s.getSubjectTitle(),
				s.getSmallImg(), s.getBackgroundImg(), s.getSubjectContent(),
				s.getSubjectName());
		return result;
	}

	@Override
	public int update(Subject s) {
		String sql = "UPDATE SUBJECT SET subjectName=?,subjectTitle=?,subjectContent=? WHERE subjectId= ? ";
		int result = this.executeUpdate(sql, s.getSubjectName(),s.getSubjectTitle(), s.getSubjectContent(),s.getSubjectId());
		return result;
	}

	@Override
	public int delete(int id) {
		String sql = "delete  from subject  where subjectId=?";
		int result = this.executeUpdate(sql, id);
		return result;
	}

	@Override
	public int getAllcount(String subjectName) {
		int count = 0;
		String sql = "select count(*) from Subject where 1=1";
		if(subjectName!=null&&subjectName.length()!=0){
			sql=sql+" and subjectName like '%"+subjectName+"%'";
		}
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void get4page(Page page,String subjectName) {
		List<Subject> list = new ArrayList<Subject>();
		String sql = "select * from Subject where 1=1";
		if(subjectName!=null&&subjectName.length()!=0){
			sql=sql+" and subjectName like '%"+subjectName+"%'";
		}
		sql=sql+" limit ?,?";
		this.executeQuery(sql, (page.getPageIndex() - 1) * page.getPageSize(),
				page.getPageSize());
		try {
			while (rs.next()) {
				Subject s = new Subject();
				s.setSubjectId(rs.getInt("subjectId"));
				s.setSubjectName(rs.getString("subjectName"));
				s.setSubjectTitle(rs.getString("subjectTitle"));
				s.setSmallImg(rs.getString("smallImg"));
				s.setBackgroundImg(rs.getString("backgroundImg"));
				s.setSubjectContent(rs.getString("subjectContent"));
				list.add(s);
			}
			page.setList(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
