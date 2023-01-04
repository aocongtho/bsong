package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Category;
import util.DBConnectionUtil;
import util.DefineUtil;

public class CategoryDAO {
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;
	

	public ArrayList<Category> getItems() {
		ArrayList<Category> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM categories ORDER BY id DESC";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category item = new Category(id, name);
				items.add(item);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(rs != null && st != null && conn != null) {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return items;
	}


	public int addCatAdmin(Category objcat) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "INSERT INTO categories(name) VALUES(?)";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, objcat.getName());
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst != null && conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return result;
	}


	public Category getItem(int cid) {
		Category objcat =  null;
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM categories WHERE id= "+cid;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()) {
				objcat = new Category(rs.getInt("id"), rs.getString("name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(st != null && conn != null) {
				try {
					st.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return objcat;
	}


	public int editCatAdmin(Category itemCat) {
		int result1 = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "UPDATE categories SET name = ? WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, itemCat.getName());
			pst.setInt(2, itemCat.getId());
			result1 = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pst != null && conn != null) {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return result1;
	}


	public int delCatAdmin(int cid) {
		int result2 = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "DELETE FROM categories WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, cid);
			result2 = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
		}
		return result2;
	}


	public ArrayList<Category> getseachcat(String seach) {
		ArrayList<Category> listseach = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM categories WHERE name LIKE ? ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,"%"+seach+"%");
			rs = pst.executeQuery();
			while(rs.next()) {
				Category objc = new Category(rs.getInt("id"), rs.getString("name"));
				listseach.add(objc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pst != null && conn != null) {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		
		return listseach;
	}


	public int countDM() {
		int dem = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM categories";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				
				dem = rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(rs != null && st != null && conn != null) {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dem;
	}


	public boolean hasCat(String nhap) {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM categories WHERE name = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, nhap);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(rs != null && pst != null && conn != null) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}


	public int numberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT COUNT(*) AS count FROM categories";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null && st != null && conn != null) {
				try {
					rs.close();
					st.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}


	public ArrayList<Category> getItemsPagination(int offset) {
		ArrayList<Category> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM categories ORDER BY id DESC LIMIT ?, ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Category item = new Category(id, name);
				items.add(item);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null && pst != null && conn != null) {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return items;
	}




}
