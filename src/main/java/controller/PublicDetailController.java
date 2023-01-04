package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.Song;
import model.dao.SongDAO;

public class PublicDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SongDAO songDAO;
    public PublicDetailController() {
        super();
        songDAO = new SongDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/404");
			return;
		}
		
		Song song = songDAO.getItem(id);
		if(song == null) {
			response.sendRedirect(request.getContextPath()+"/404");
			return;
		}
		// tăng view
		HttpSession session = request.getSession();
		String hasVisited =(String) session.getAttribute("hasVisited : "+ id); // mục đích tạo ra 1 Attribute tron session và phân biệt giữa các bài viết qua id
		if(hasVisited == null) { // người đó chưa xem bài viết bao giờ
			session.setAttribute("hasVisited : "+ id, "yes");
			session.setMaxInactiveInterval(3600);
			
			// sau một thời gian quy định, sẽ tự động mất session và tăng lượt view
			songDAO.increaseView(id);
		}
		ArrayList<Song> relatedSongs = songDAO.getRelatedItems(song, 2);
		
		request.setAttribute("relatedSongs", relatedSongs);
		request.setAttribute("song", song);
		RequestDispatcher rd = request.getRequestDispatcher("/public/detail.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
