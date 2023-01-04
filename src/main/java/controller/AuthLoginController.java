package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UsersDAO;
import util.AuthUtil;
import util.StringUtil;


public class AuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAO usersDAO; 
       
    public AuthLoginController() {
        super();
        usersDAO = new UsersDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/auth/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		// chuyển hướng đến trang chủ admin khi đã login
		if(userLogin != null) {
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = StringUtil.md5(password);
		
		
		User user = usersDAO.existUser(username, password);
		// check login với user và password
		
		if(user != null) {
			session.setAttribute("userLogin", user);
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/login?error=1");
			return;
		}
		
		
	}

}
