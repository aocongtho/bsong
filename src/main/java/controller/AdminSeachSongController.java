package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Song;
import model.dao.SongDAO;


public class AdminSeachSongController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SongDAO songDAO;
    public AdminSeachSongController() {
        super();
        songDAO = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  request.setCharacterEncoding("UTF-8");
		  
		  String seach = request.getParameter("seach"); 
		  ArrayList<Song> items = songDAO.getseachsong(seach);
		  
		  if(items != null && items.size() > 0) {
			  request.setAttribute("items", items); 
			  
			  RequestDispatcher rd = request.getRequestDispatcher("/admin/song/seach.jsp?msg=1");
			  rd.forward(request, response); 
			  return; 
		  }else {
			  response.sendRedirect(request.getContextPath()+ "/admin/song/noseach.jsp?error=1"); 
			  return; 
		  }
	}

}
