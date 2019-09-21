package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.ClassimgDao;
import entity.Classimg;
import entity.Page;

public class ClassimgDaoimpl extends BaseDao implements ClassimgDao {

	@Override
	public List<Classimg> getAll() {
		List<Classimg> list = new ArrayList<Classimg>();
		String sql = "SELECT * FROM classimg";
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				Classimg c = new Classimg();
				c.setImgId(rs.getInt("imgId"));
				c.setImgSrc(rs.getString("imgSrc"));
				c.setContent(rs.getString("content"));
				list.add(c);
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
	public int add(Classimg c) {
		String sql = "INSERT INTO classimg(imgSrc,content)VALUE(?,?)";
		int result = this.executeUpdate(sql, c.getImgSrc(),c.getContent());
		return result;
	}

	@Override
	public int delete(int imgId) {
		String sql = "DELETE FROM classimg WHERE imgId=?";
		int result = this.executeUpdate(sql, imgId);
		return result;
	}

	@Override
	public int update(Classimg c) {
		String sql = "UPDATE classimg SET content=? WHERE imgId=?";
		int result = this.executeUpdate(sql, c.getContent(), c.getImgId());
		return result;
	}

	@Override
	public Classimg getById(int imgId) {
		Classimg c = null;
		String sql = "SELECT * FROM classimg where imgId=?";
		this.executeQuery(sql, imgId);
		try {
			while (rs.next()) {
				c = new Classimg();
				c.setImgId(rs.getInt("imgId"));
				c.setImgSrc(rs.getString("imgSrc"));
				c.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return c;
	}

	@Override
	public int getAllCount() {
		int count = 0;
		String sql = "SELECT count(*) FROM classimg";
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
	public void get4Page(Page page) {
		List<Classimg> list = new ArrayList<Classimg>();
		String sql = "SELECT * FROM classimg limit ?,?";
		this.executeQuery(sql, (page.getPageIndex() - 1) * page.getPageSize(),
				page.getPageSize());
		try {
			while (rs.next()) {
				Classimg c = new Classimg();
				c.setImgId(rs.getInt("imgId"));
				c.setImgSrc(rs.getString("imgSrc"));
				c.setContent(rs.getString("content"));
				list.add(c);
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
