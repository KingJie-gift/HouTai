package dao.impl;

import dao.AdminDao;
import dao.BaseDao;
import entity.Admin;

public class AdminDaoImpl extends BaseDao implements AdminDao {

	@Override
	public int check(Admin a) {
		String sql = "SELECT COUNT(*) FROM admin WHERE NAME = ? AND pwd = ?";
		this.executeQuery(sql, a.getName(), a.getPwd());
		int count = 0;
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			this.closeAll();
		}
		return count;
	}

}
