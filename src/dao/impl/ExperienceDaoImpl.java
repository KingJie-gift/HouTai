package dao.impl;

import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.ExperienceDao;
import entity.Experience;
import entity.Page;
import entity.Subject;

public class ExperienceDaoImpl extends BaseDao implements ExperienceDao {
	SubjectDaoImpl sdi = new SubjectDaoImpl();

	@Override
	public int add(Experience e) {
		int result = 0;
		String sql = "INSERT INTO experience(DATE,title,content,smallContent,img,subjectId)VALUES(?,?,?,?,?,?)";
		result = this.executeUpdate(sql, e.getDate(), e.getTitle(),
				e.getContent(), e.getSmallContent(), e.getImg(),
				e.getSubjectId());
		return result;
	}

	@Override
	public int delete(int id) {
		int result = 0;
		String sql = "DELETE FROM experience WHERE id=?";
		result = this.executeUpdate(sql, id);
		return result;
	}

	@Override
	public List<Experience> getAll() {
		List<Experience> list = new ArrayList<Experience>();
		try {
			String sql = "SELECT * FROM experience";
			this.executeQuery(sql);
			while (rs.next()) {
				Experience e = new Experience();
				e.setId(rs.getInt("id"));
				e.setDate(rs.getString("date"));
				e.setTitle(rs.getString("title"));
				e.setContent(rs.getString("content"));
				e.setSmallContent(rs.getString("smallContent"));
				e.setSubjectId(rs.getInt("subjectId"));
				e.setImg(rs.getString("img"));
				Subject subject = sdi.getById(e.getSubjectId());
				e.setSubject(subject);
				list.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return list;
	}

	@Override
	public Experience getById(int id) {
		Experience e = null;
		try {
			String sql = "SELECT * FROM experience where id=?";
			this.executeQuery(sql, id);
			while (rs.next()) {
				e = new Experience();
				e.setId(rs.getInt("id"));
				e.setDate(rs.getString("date"));
				e.setTitle(rs.getString("title"));
				e.setContent(rs.getString("content"));
				e.setSmallContent(rs.getString("smallContent"));
				e.setImg(rs.getString("img"));
				e.setSubjectId(rs.getInt("subjectId"));
				Subject subject = sdi.getById(e.getSubjectId());
				e.setSubject(subject);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.closeAll();
		}
		return e;
	}

	@Override
	public int update(Experience e) {
		int result = 0;
		String sql = "UPDATE experience SET title=?,content=?,smallContent=?,subjectId=? WHERE id=?";
		result = this.executeUpdate(sql, e.getTitle(), e.getContent(),
				e.getSmallContent(), e.getSubjectId(), e.getId());
		return result;
	}

	@Override
	public void get4Page(Page page, int subjectId) {
		List<Experience> list = new ArrayList<Experience>();
		try {
			String sql = "SELECT * FROM experience where 1=1";
			if (subjectId != -1) {
				sql = sql + " and subjectId=" + subjectId;
			}
			sql = sql + " limit ?,?";
			this.executeQuery(sql,
					(page.getPageIndex() - 1) * page.getPageSize(),
					page.getPageSize());
			while (rs.next()) {
				Experience e = new Experience();
				e.setId(rs.getInt("id"));
				e.setDate(rs.getString("date"));
				e.setTitle(rs.getString("title"));
				e.setContent(rs.getString("content"));
				e.setSmallContent(rs.getString("smallContent"));
				e.setImg(rs.getString("img"));
				e.setSubjectId(rs.getInt("subjectId"));
				Subject subject = sdi.getById(e.getSubjectId());
				e.setSubject(subject);
				list.add(e);
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
			String sql = "SELECT count(*) FROM experience";
			this.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.closeAll();
		}
		return count;
	}

}
