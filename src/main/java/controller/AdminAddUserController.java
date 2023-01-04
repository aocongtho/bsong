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

public class AdminAddUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsersDAO usersDAO;
    public AdminAddUserController() {
        super();
        usersDAO = new UsersDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		// chỉ có admin mới có quyền thêm người dùng
		if(!"admin".equals(userLogin.getUsername())) {
			// không được phép
			response.sendRedirect(request.getContextPath()+ "/admin/users?error=4");
			return;
		}
		RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		// chỉ có admin mới có quyền thêm người dùng
		if(!"admin".equals(userLogin.getUsername())) {
			// không được phép
			response.sendRedirect(request.getContextPath()+ "/admin/users?error=4");
			return;
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		String username = request.getParameter("username");
		
		// kiểm tra username đã tồn tại hay chưas
		if(usersDAO.hasUser(username)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp?error=2");
			rd.forward(request, response);
			return;
		}
		// mã hóa password
		password = StringUtil.md5(password);
		
		
		
		User objU = new User(0, username, password, fullname);
		
		if(usersDAO.adduseradmin(objU) > 0) {
			response.sendRedirect(request.getContextPath()+ "/admin/users?msg=1");
			return;
			
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp?error=1");
			rd.forward(request, response);
			
		}
		
		
	}

}
