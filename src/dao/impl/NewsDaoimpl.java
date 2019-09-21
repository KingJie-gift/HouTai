package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.NewsDao;
import entity.News;
import entity.Page;
import entity.Subject;

public class NewsDaoimpl extends BaseDao implements NewsDao {
	SubjectDaoImpl sdi = new SubjectDaoImpl();

	@Override
	public List<News> getAll() {
		List<News> list = new ArrayList<News>();
		String sql = "select * from news";
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				News n = new News();
				n.setDate(rs.getString("date"));
				n.setTitle(rs.getString("title"));
				n.setNewsId(rs.getInt("newsId"));
				n.setContent(rs.getString("content"));
				n.setNewsImg(rs.getString("newsImg"));
				n.setSmallContent(rs.getString("smallContent"));
				n.setSubjectId(rs.getInt("subjectId"));
				Subject subject = sdi.getById(n.getSubjectId());
				n.setSubject(subject);
				list.add(n);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return list;
	}

	@Override
	public int add(News n) {
		String sql = "INSERT INTO news(DATE,title,content,newsImg,smallContent,subjectId)VALUE(?,?,?,?,?,?)";
		int result = this.executeUpdate(sql, n.getDate(), n.getTitle(),
				n.getContent(), n.getNewsImg(), n.getSmallContent(),
				n.getSubjectId());
		return result;
	}

	@Override
	public int delete(int newsId) {
		String sql = "delete from news where newsId=?";
		int result = this.executeUpdate(sql, newsId);
		return result;
	}

	@Override
	public int update(News n) {
		String sql = "UPDATE news SET title=?,content=?,smallContent=?,newsImg=?,subjectId=? WHERE newsId=?";
		int result = this.executeUpdate(sql, n.getTitle(), n.getContent(),
				n.getSmallContent(), n.getNewsImg(), n.getSubjectId(),
				n.getNewsId());
		return result;
	}

	@Override
	public News getById(int newsId) {
		News n = null;
		String sql = "select * from news where newsId=?";
		this.executeQuery(sql, newsId);
		try {
			while (rs.next()) {
				n = new News();
				n.setDate(rs.getString("date"));
				n.setTitle(rs.getString("title"));
				n.setNewsId(rs.getInt("newsId"));
				n.setContent(rs.getString("content"));
				n.setNewsImg(rs.getString("newsImg"));
				n.setSmallContent(rs.getString("smallContent"));
				n.setSubjectId(rs.getInt("subjectId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return n;
	}

	@Override
	public int getAllCount() {
		int count = 0;
		String sql = "select count(*) from news";
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return count;
	}

	@Override
	public void get4Page(Page page, String title, int subjectId) {
		List<News> list = new ArrayList<News>();
		String sql = "select * from news where 1=1";
		if (title != null && title.length() != 0) {
			sql = sql + "  and title like '%" + title + "%'  ";
		}
		if (subjectId != -1) {
			sql = sql + "  and subjectId=" + subjectId;
		}
		sql = sql + "  limit ?,?";
		this.executeQuery(sql, (page.getPageIndex() - 1) * page.getPageSize(),
				page.getPageSize());
		try {
			while (rs.next()) {
				News n = new News();
				n.setDate(rs.getString("date"));
				n.setTitle(rs.getString("title"));
				n.setNewsId(rs.getInt("newsId"));
				n.setContent(rs.getString("content"));
				n.setNewsImg(rs.getString("newsImg"));
				n.setSmallContent(rs.getString("smallContent"));
				n.setSubjectId(rs.getInt("subjectId"));
				Subject subject = sdi.getById(n.getSubjectId());
				n.setSubject(subject);
				list.add(n);
			}
			page.setList(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
	}

}
