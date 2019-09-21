package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.CommunicateDao;
import entity.Communicate;
import entity.Page;
import entity.Subject;

public class CommunicateDaoImpl extends BaseDao implements CommunicateDao {
	SubjectDaoImpl i = new SubjectDaoImpl();

	@Override
	public int add(Communicate c) {
		String sql = "INSERT INTO communicate(DATE,title,content,smallContent,img,subjectId)VALUES(?,?,?,?,?,?)";
		int result = this.executeUpdate(sql,c.getDate(), c.getTitle(), c.getContent(),
				c.getSmallContent(), c.getImg(), c.getSubjectId());
		return result;
	}

	@Override
	public Communicate getById(int id) {
		Communicate c = null;
		String sql = "select * from Communicate where id=?";
		this.executeQuery(sql, id);
		try {
			while (rs.next()) {
				c = new Communicate();
				c.setId(rs.getInt("id"));
				c.setDate(rs.getString("date"));
				c.setTitle(rs.getString("title"));
				c.setContent(rs.getString("content"));
				c.setSmallContent(rs.getString("smallcontent"));
				c.setSubjectId(rs.getInt("subjectId"));
				c.setImg(rs.getString("img"));
				c.setSubject(i.getById(c.getSubjectId()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return c;
	}

	@Override
	public int update(Communicate c) {
		String sql = "update communicate set title=?,content=?,smallcontent=?,subjectId=? where id=?";
		int  result=this.executeUpdate(sql,c.getTitle(),c.getContent(),c.getSmallContent(),c.getSubjectId(),c.getId());
		return result;
	}

	@Override
	public int delete(int id) {
		String sql = "delete from Communicate where id=?";
		int result = this.executeUpdate(sql, id);
		return result;
	}

	@Override
	public int getAllcount(String title) {
		int count = 0;
		String sql = "select count(*) from Communicate where 1=1";
		if(title!=null&&title.length()!=0){
			sql=sql+"   and title like '%"+title+"%'";
		}
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
		return count;
	}

	@Override
	public void get4page(Page page,String title,int subjectId) {
		List<Communicate> list = new ArrayList<Communicate>();
		String sql = "select * from Communicate where 1=1";
		if(title!=null&&title.length()!=0){
			sql=sql+"   and title like '%"+title+"%'";
		}
		if(subjectId!=-1){
			sql=sql+" and subjectId="+subjectId;
		}
		sql=sql+"   limit ?,?";
		this.executeQuery(sql, (page.getPageIndex() - 1) * page.getPageSize(),
				page.getPageSize());
		try {
			while (rs.next()) {
				Communicate o = new Communicate();
				o.setId(rs.getInt("id"));
				o.setDate(rs.getString("date"));
				o.setTitle(rs.getString("title"));
				o.setContent(rs.getString("content"));
				o.setSmallContent(rs.getString("smallcontent"));
				o.setImg(rs.getString("img"));
				o.setSubjectId(rs.getInt("subjectId"));
				Subject subject = i.getById(o.getSubjectId());
				o.setSubject(subject);
				list.add(o);
			}
			page.setList(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.closeAll();
		}
	}
}
