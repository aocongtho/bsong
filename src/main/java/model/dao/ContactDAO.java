package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Contact;
import util.DBConnectionUtil;
import util.DefineUtil;

public class ContactDAO {
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;

	public ArrayList<Contact> getItems() {
		ArrayList<Contact> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM contacts ORDER BY id DESC";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String webside = rs.getString("webside");
				String message = rs.getString("message");
				
				Contact item = new Contact(id, name, email, webside, message);
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

	public int addContact(Contact objC) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "INSERT INTO contacts(name,email,message) VALUES(?,?,?)";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, objC.getName());
			pst.setString(2, objC.getEmail());
			pst.setString(3, objC.getMessage());
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

	public int delcontact(int did) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "DELETE FROM contacts WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, did);
			
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

	public ArrayList<Contact> getseach(String seach) {
		ArrayList<Contact> listseach = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM contacts WHERE name LIKE ? ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,"%"+seach+"%");
			rs = pst.executeQuery();
			while(rs.next()) {
				Contact objc = new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("webside"), rs.getString("message"));
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

	public int addItem(Contact item) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "INSERT INTO contacts(name, email, webside, message) VALUES(?, ?, ?, ?)";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getName());
			pst.setString(2, item.getEmail());
			pst.setString(3, item.getWebside());
			pst.setString(4, item.getMessage());
			
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

	public int numberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT COUNT(*) AS count FROM contacts";
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

	public ArrayList<Contact> getItemsPagination(int offset) {
		ArrayList<Contact> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT * FROM contacts ORDER BY id DESC LIMIT ?, ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String webside = rs.getString("webside");
				String message = rs.getString("message");
				
				Contact item = new Contact(id, name, email, webside, message);
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

}
