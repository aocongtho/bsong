package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Category;
import model.bean.Song;
import model.dao.CategoryDAO;
import model.dao.SongDAO;
import util.AuthUtil;
import util.FileUtil;

@MultipartConfig
public class AdminEditSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;
       
    public AdminEditSongController() {
        super();
        songDAO = new SongDAO();
        categoryDAO = new CategoryDAO();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		int sid = 0;
		try {
			sid = Integer.parseInt(request.getParameter("sid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/admin/songs?error=1");
			return;
		}
		
		Song song = songDAO.getItem(sid);
		if(song == null) {
			response.sendRedirect(request.getContextPath()+ "/admin/songs?error=1");
			return;
		}
		request.setAttribute("song", song);
		
		
		ArrayList<Category> listcat = categoryDAO.getItems();
		request.setAttribute("listcat", listcat);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		
		int catId = 0;
		int sid = 0;
		try {
			catId = Integer.parseInt(request.getParameter("category"));
			sid = Integer.parseInt(request.getParameter("sid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/admin/songs?error=1");
			return;
		}
		String name = request.getParameter("name");
		String previewText = request.getParameter("preview_text");
		String detailText = request.getParameter("detail_text");
		
		
		
		
		Part filePart = request.getPart("picture");
	
		// get dữ liệu cũ
		Song song = songDAO.getItem(sid);
		if(song == null) {
			response.sendRedirect(request.getContextPath()+ "/admin/songs?error=1");
			return;
		}
		// tạo thư mục lưu ảnh
		final String dirPathName = request.getServletContext().getRealPath("files");
		File dirFile = new File(dirPathName);
		if(!dirFile.exists()) {
			dirFile.mkdir();
		}
		// lấy tên file từ Part
		String fileName = FileUtil.getName(filePart);
		
		// đổi tên file
		String picture  = "";
		if(fileName.isEmpty()) {
			picture = song.getPicture();
		}else {
			picture  = FileUtil.rename(fileName);
		}
		
		// đường dẫn file 
		String filePathName = dirPathName + File.separator + picture;
		
		Category category = new Category(catId, null);
		Song item = new Song(sid, name, previewText, detailText, null, picture, 0, category);
		
		if(songDAO.editSong(item) > 0) {
			// ghi file, lưu ảnh vào ổ đĩa
			if(!fileName.isEmpty()) {
				// xóa file cũ
				String oldFilePathName = dirPathName + File.separator + song.getPicture();
				File oldFile = new File(oldFilePathName);
				if(oldFile.exists()) {
					oldFile.delete();
				}
				// ghi file mới
				filePart.write(filePathName);
			}
			response.sendRedirect(request.getContextPath()+ "/admin/songs?msg=2");
			return;
		}else {
			ArrayList<Category> listcat = categoryDAO.getItems();
			request.setAttribute("listcat", listcat);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/edit.jsp?error=1");
			rd.forward(request, response);
			return;
		}
		
	}
	
}
