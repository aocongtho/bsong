package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.bean.Category;
import model.bean.Song;
import util.DBConnectionUtil;
import util.DefineUtil;

public class SongDAO {
	private PreparedStatement pst;
	private Statement st;
	private Connection conn;
	private ResultSet rs;
	
	

	public ArrayList<Song> getItems() {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT s.id AS sid, s.name AS sname, preview_text, detail_text,date_create,picture,counter,cat_id,c.name"
				+ " AS cname FROM songs AS  s"
				+ " INNER JOIN categories AS c ON s.cat_id = c.id"
				+ " ORDER BY s.id DESC";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("cname"));
				
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
				e.printStackTrace();
		} finally {
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
		
		return items;
	}

	public ArrayList<Song> getItems(int number) {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT s.id AS sid, s.name AS sname, preview_text, detail_text,date_create,picture,counter,cat_id,c.name"
				+ " AS cname FROM songs AS  s"
				+ " INNER JOIN categories AS c ON s.cat_id = c.id"
				+ " ORDER BY s.id DESC LIMIT ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, number);
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("cname"));
				
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return items;
	}




	public Song getItem(int sid) {
		Song item = null;
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT id, name, preview_text, detail_text, date_create, picture, counter, cat_id  FROM songs WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, sid);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), null);
				
				item = new Song(sid, name, previewText, detailText, dateCreate, picture, counter, category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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

	public int editSong(Song item) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "UPDATE songs SET name = ?, preview_text = ?, detail_text = ?, picture = ?, cat_id = ? WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getName());
			pst.setString(2, item.getPreviewText());
			pst.setString(3, item.getDetailText());
			pst.setString(4, item.getPicture());
			pst.setInt(5, item.getCategory().getId());
			pst.setInt(6, item.getId());
			
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		
		return result;
	}



	public int delsong(int sid) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "DELETE FROM songs WHERE id = ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, sid);
			result = pst.executeUpdate();
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
		
		return result;
	}


	public int addSong(Song item) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String query = "INSERT INTO songs(name, preview_text, detail_text, date_create, picture, counter, cat_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, item.getName());
			pst.setString(2, item.getPreviewText());
			pst.setString(3, item.getDetailText());
			pst.setTimestamp(4, item.getDateCreate());
			pst.setString(5, item.getPicture());
			pst.setInt(6, item.getCounter());
			pst.setInt(7, item.getCategory().getId());
			result = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return result;
	}

	public ArrayList<Song> getItemsByCategory(int catId) {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT s.id AS sid, s.name AS sname, preview_text, detail_text,date_create,picture,counter,cat_id,c.name"
				+ " AS cname FROM songs AS  s"
				+ " INNER JOIN categories AS c ON s.cat_id = c.id"
				+ " WHERE cat_id = ?"
				+ " ORDER BY s.id DESC";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, catId);
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("cname"));
				
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return items;
	}

	public ArrayList<Song> getRelatedItems(Song song, int number) {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT s.id AS sid, s.name AS sname, preview_text, detail_text,date_create,picture,counter,cat_id,c.name"
				+ " AS cname FROM songs AS  s"
				+ " INNER JOIN categories AS c ON s.cat_id = c.id"
				+ " WHERE cat_id = ? && s.id != ?"
				+ " ORDER BY s.id DESC LIMIT ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, song.getCategory().getId());
			pst.setInt(2, song.getId());
			pst.setInt(3, number);
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("cname"));
				
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return items;
	}

	public void increaseView(int id) {
		conn = DBConnectionUtil.getConnection();
		String query = "UPDATE songs SET counter =counter + 1 WHERE id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		
	}



	public ArrayList<Song> getseachsong(String seach) {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM songs WHERE name LIKE ? ";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1,"%"+seach+"%");
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("name"));
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
			
		} catch (SQLException e) {
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
		
		return items;
	}

	public int countBH() {
		int dem = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM songs";
		
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

	public int numberOfItems() {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT COUNT(*) AS count FROM songs";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			
			if(rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
				e.printStackTrace();
		} finally {
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
	
	public int numberOfItems(int catId) {
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT COUNT(*) AS count FROM songs WHERE cat_id = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, catId);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return 0;
	}

	public ArrayList<Song> getItemsPagination(int offset) {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT s.id AS sid, s.name AS sname, preview_text, detail_text,date_create,picture,counter,cat_id,c.name"
				+ " AS cname FROM songs AS  s"
				+ " INNER JOIN categories AS c ON s.cat_id = c.id"
				+ " ORDER BY s.id DESC LIMIT ?, ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			
			
			while(rs.next()) {
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("cname"));
				
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return items;
	}

	public ArrayList<Song> getItemsByCategoryPagination(int offset, int catId) {
		ArrayList<Song> items = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String query = "SELECT s.id AS sid, s.name AS sname, preview_text, detail_text,date_create,picture,counter,cat_id,c.name"
				+ " AS cname FROM songs AS  s"
				+ " INNER JOIN categories AS c ON s.cat_id = c.id"
				+ " WHERE cat_id = ?"
				+ " ORDER BY s.id DESC LIMIT ?, ?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, catId);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			
			
			while(rs.next()) {
				int id = rs.getInt("sid");
				String name = rs.getString("sname");
				String previewText = rs.getString("preview_text");
				String detailText =  rs.getString("detail_text");
				Timestamp dateCreate = rs.getTimestamp("date_create");
				String picture = rs.getString("picture");
				int counter = rs.getInt("counter");
				Category category = new Category(rs.getInt("cat_id"), rs.getString("cname"));
				
				Song item = new Song(id, name, previewText, detailText, dateCreate, picture, counter, category);
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
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
		
		return items;
	}

}
