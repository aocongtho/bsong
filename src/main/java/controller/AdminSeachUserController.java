package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.UsersDAO;

@WebServlet("/AdminSeachUserController")
public class AdminSeachUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminSeachUserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String seach = request.getParameter("seach");
		UsersDAO usersDAO = new UsersDAO();
		
		ArrayList<User> listU = usersDAO.getseach(seach);
		
		if(listU != null && listU.size() > 0) {
			request.setAttribute("listU", listU);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/seach.jsp?msg=1");
			rd.forward(request, response);
			return;
			
		}else {
			response.sendRedirect(request.getContextPath()+"/admin/user/noseach.jsp?error=1");
			return;
		}
		
		
	}

}
