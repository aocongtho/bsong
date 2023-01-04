package controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Song;
import model.dao.SongDAO;
import util.AuthUtil;

public class AdminDelSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;
       
    public AdminDelSongController() {
        super();
        songDAO = new SongDAO();
        // TODO Auto-generated constructor stub
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
			response.sendRedirect(request.getContextPath()+"/admin/songs?error=1");
			return;
		}
		
		if(songDAO.delsong(sid) > 0) {
			
			// xóa lun ảnh
			final String dirPathName = request.getServletContext().getRealPath("/files");
			String picture = song.getPicture();
			if(!picture.isEmpty()) {
				String filePathName = dirPathName + File.separator + picture;
				File file = new File(filePathName);
				if(file.exists()) {
					file.delete();
					
				}
				
			}
			
			response.sendRedirect(request.getContextPath()+ "/admin/songs?msg=3");
			return;
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin/songs?error=3");
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
