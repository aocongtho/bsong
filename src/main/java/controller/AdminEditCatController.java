package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.dao.CategoryDAO;
import util.AuthUtil;

public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;
    public AdminEditCatController() {
        super();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
	
		int cid = 0;
		try {
			cid =Integer.parseInt(request.getParameter("cid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/admin/cats?error=1");
			return;
		}
		
		Category itemCat = categoryDAO.getItem(cid);
		if(itemCat != null) {
			request.setAttribute("itemCat", itemCat);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/edit.jsp");
			rd.forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath()+ "/admin/cats?error=2");
			return;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		int cid = 0;
		try {
			cid =Integer.parseInt(request.getParameter("cid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/admin/cats?error=1");
			return;
		}
		
		String name = request.getParameter("name");
		
		Category itemCat = new Category(cid, name);
		
		if(categoryDAO.editCatAdmin(itemCat) > 0) {
			response.sendRedirect(request.getContextPath()+ "/admin/cats?msg=2");
			return;
		} else {
			
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/edit.jsp?error=1");
			rd.forward(request, response);
			return;
		}
		
	}

}
