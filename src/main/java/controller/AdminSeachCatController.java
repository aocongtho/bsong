package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.dao.CategoryDAO;

@WebServlet("/AdminSeachCatController")
public class AdminSeachCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;   
    public AdminSeachCatController() {
        super();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String seach = request.getParameter("seach");
		
		ArrayList<Category> listseach = categoryDAO.getseachcat(seach);
		
		if(listseach != null && listseach.size() > 0) {
			request.setAttribute("listseach", listseach);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/indexseach.jsp?msg=1");
			rd.forward(request, response);
			return;
			
		}else {
			response.sendRedirect(request.getContextPath()+"/admin/cat/noseach.jsp?error=1");
			return;
		}
		
	}

}
