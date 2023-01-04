package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import model.bean.User;
import util.DBConnectionUtil;
import util.DefineUtil;

public class UsersDAO {
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;
	

	
	public ArrayList<User> getItems() {
		ArrayList<User> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM users ORDER BY id DESC";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String fullname = rs.getString("fullname");
				User item = new User(id, username, password, fullname);
				items.add(item);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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



	public int adduseradmin(User objU) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "INSERT INTO users(username,password,fullname) VALUES(?,?,?)"; 
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, objU.getUsername());
			pst.setString(2, objU.getPassword());
			pst.setString(3, objU.getFullname());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
				try {
					pst.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			
		}
		
		return result;
	}



	public User getItem(int uid) {
		User itemUser = null;
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM users WHERE id = "+uid;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()) {
				itemUser = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"));
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		

		return itemUser;
	}



	public int edituseradmin(User itemUser) {
		int result1 = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "UPDATE users SET password = ?,fullname = ? WHERE id = ?"; 
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, itemUser.getPassword());
			pst.setString(2, itemUser.getFullname());
			pst.setInt(3, itemUser.getId());
			
			result1 = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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



	public int deluseradmin(int uid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "DELETE FROM users WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, uid);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pst.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				
		return result;
	}



	public boolean hasUser(String username) {
		
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM users WHERE username = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username);
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



	public ArrayList<User> getseach(String seach) {
		ArrayList<User> listseach = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM users WHERE username LIKE ? ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,"%"+seach+"%");
			rs = pst.executeQuery();
			while(rs.next()) {
				User objU = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"));
				listseach.add(objU);
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



	public User existUser(String username, String password) {
		User item = null;
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT id, username, password, fullname FROM users WHERE username=? and password=?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				String fullname = rs.getString("fullname");
				item = new User(id, username, password, fullname);
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
					e.printStackTrace();
				}
				
			}
		}

		return item;
	}



	public int countUser() {
		int dem = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM users";
		
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



	public ArrayList<User> getItemsPagination(int offset) {
		ArrayList<User> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM users ORDER BY id DESC LIMIT ?, ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String fullname = rs.getString("fullname");
				User item = new User(id, username, password, fullname);
				items.add(item);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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



	public int numberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT COUNT(*) AS count FROM users";
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



	
	
}
