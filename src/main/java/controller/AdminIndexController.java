package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.dao.SongDAO;
import model.dao.UsersDAO;
import util.AuthUtil;

public class AdminIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SongDAO songDAO;
	private CategoryDAO categoryDAO;
	private UsersDAO usersDAO;
	
    public AdminIndexController() {
        super();
        songDAO = new SongDAO();
        categoryDAO = new CategoryDAO();
        usersDAO = new UsersDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		int demBH = songDAO.countBH();
		int demDM = categoryDAO.countDM();
		int demUser = usersDAO.countUser();
		
		request.setAttribute("demBH", demBH);
		request.setAttribute("demDM", demDM);
		request.setAttribute("demUser", demUser);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		
	}

}
