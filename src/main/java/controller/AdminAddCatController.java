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

public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;   
    public AdminAddCatController() {
        super();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		
		String nhap = request.getParameter("nhap");
		if(categoryDAO.hasCat(nhap)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/add.jsp?error=2");
			rd.forward(request, response);
			return;
		}
		
		
		
		Category item = new Category(0, nhap);
		
		if(categoryDAO.addCatAdmin(item) > 0) {
			response.sendRedirect(request.getContextPath()+ "/admin/cats?msg=1");
			return;
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/add.jsp?error=1");
			rd.forward(request, response);
		}
	}

}
