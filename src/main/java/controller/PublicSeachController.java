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

public class PublicSeachController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SongDAO songDAO;  
    public PublicSeachController() {
        super();
        songDAO = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		  
		  String editbox_search = request.getParameter("editbox_search"); 
		  ArrayList<Song> items = songDAO.getseachsong(editbox_search);
		  
		  if(items != null && items.size() > 0) {
			  request.setAttribute("items", items); 
			  RequestDispatcher rd = request.getRequestDispatcher("/public/seach.jsp?msg=1");
			  rd.forward(request, response); 
			  return; 
		  }else {
			  response.sendRedirect(request.getContextPath()+ "/public/noseach.jsp?error=1"); 
			  return; 
		  }
		
		
	}

}
