package dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.IndeximgDao;
import entity.Indeximg;
import entity.Page;

public class IndeximgDaoimpl extends BaseDao implements IndeximgDao {

	@Override
	public List<Indeximg> getAll() {
		List<Indeximg> list = new ArrayList<Indeximg>();
		String sql = "SELECT * FROM indeximg";
		this.executeQuery(sql);
		try {
			while (rs.next()) {
				Indeximg i = new Indeximg();
				i.setImgId(rs.getInt("imgId"));
				i.setImgSrc(rs.getString("imgSrc"));
				list.add(i);
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
	public int add(Indeximg i) {
		String sql = "INSERT INTO indeximg(imgSrc)VALUE(?)";
		int result = this.executeUpdate(sql, i.getImgSrc());
		return result;
	}

	@Override
	public int delete(int imgId) {
		String sql = "DELETE FROM indeximg WHERE imgId=?";
		int result = this.executeUpdate(sql, imgId);
		return result;
	}

	@Override
	public int update(Indeximg i) {
		String sql = "UPDATE indeximg SET imgSrc=? WHERE imgId=?";
		int result = this.executeUpdate(sql, i.getImgSrc(), i.getImgId());
		return result;
	}

	@Override
	public Indeximg getById(int imgId) {
		Indeximg i = null;
		String sql = "SELECT * FROM indeximg where imgId=?";
		this.executeQuery(sql, imgId);
		try {
			while (rs.next()) {
				i = new Indeximg();
				i.setImgId(rs.getInt("imgId"));
				i.setImgSrc(rs.getString("imgSrc"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return i;
	}

	@Override
	public int getAllCount() {
		int count = 0;
		String sql = "SELECT count(*) FROM indeximg";
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
		List<Indeximg> list = new ArrayList<Indeximg>();
		String sql = "SELECT * FROM indeximg limit ?,?";
		this.executeQuery(sql, (page.getPageIndex() - 1) * page.getPageSize(),
				page.getPageSize());
		try {
			while (rs.next()) {
				Indeximg i = new Indeximg();
				i.setImgId(rs.getInt("imgId"));
				i.setImgSrc(rs.getString("imgSrc"));
				list.add(i);
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
