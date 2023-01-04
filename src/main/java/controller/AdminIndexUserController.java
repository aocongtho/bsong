package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.UsersDAO;
import util.AuthUtil;
import util.DefineUtil;

public class AdminIndexUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsersDAO usersDAO;   
    public AdminIndexUserController() {
        super();
        usersDAO = new UsersDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		
		int numberOfItems = usersDAO.numberOfItems();
		int numberOfPages = (int) Math.ceil((float) numberOfItems/ DefineUtil.NUMBER_PER_PAGE);
		
		
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		
		if(currentPage > numberOfPages || currentPage < 1) {
			currentPage = 1;
		}
			
		int numberPage = DefineUtil.NUMBER_PER_PAGE;
		int offset = (currentPage - 1 ) * numberPage;
		
		
		ArrayList<User> listUser = usersDAO.getItemsPagination(offset);
		request.setAttribute("listUser", listUser);
		
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("numberOfItems", numberOfItems);
		request.setAttribute("numberPage", numberPage);
		RequestDispatcher rd =request.getRequestDispatcher("/admin/user/index.jsp");
		rd.forward(request, response);
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
