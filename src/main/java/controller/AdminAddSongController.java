package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
public class AdminAddSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;
       
    public AdminAddSongController() {
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
		
		ArrayList<Category> listcat = categoryDAO.getItems();
		request.setAttribute("listcat", listcat);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/song/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		request.setCharacterEncoding("UTF-8");
		
		int catId = 0;
		try {
			catId = Integer.parseInt(request.getParameter("category"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/admin/songs?error=1");
			return;
		}
		String name = request.getParameter("name");
		String previewText = request.getParameter("preview_text");
		String detailText = request.getParameter("detail_text");
		
		Part filePart = request.getPart("picture");
		
		// CÓ THỂ CÓ HOẶC KHÔNG CÓ DATECREATE, NẾU dateCreate = null thì csdl sẽ tự động lấy
		Timestamp dateCreate = new Timestamp(new Date().getTime());

		// tạo thư mục lưu ảnh
		final String dirPathName = request.getServletContext().getRealPath("/files");
		File dirFile = new File(dirPathName);
		if(!dirFile.exists()) {
			dirFile.mkdir();
		}
		// lấy tên file từ Part
		String fileName = FileUtil.getName(filePart);
		// đổi tên file
		String picture  = FileUtil.rename(fileName);
		// đường dẫn file 
		String filePathName = dirPathName + File.separator + picture;
		
		Category category = new Category(catId,null);
		Song item = new Song(0, name, previewText, detailText, dateCreate, picture, 0, category);
		
		if(songDAO.addSong(item) > 0) {
			// ghi file, lưu ảnh vào ổ đĩa
			if(!fileName.isEmpty()) {
				filePart.write(filePathName);
			}
			response.sendRedirect(request.getContextPath()+ "/admin/songs?msg=1");
			return;
		}else {
			ArrayList<Category> listcat = categoryDAO.getItems();
			request.setAttribute("listcat", listcat);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/song/add.jsp?error=1");
			rd.forward(request, response);
			return;
		}
		
	}
	
}
