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

public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAO usersDAO;
    public AdminEditUserController() {
        super();
        usersDAO = new UsersDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check login
		if(!AuthUtil.checkLogin(request, response)) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		int uid = 0;
		try {
			uid = Integer.parseInt (request.getParameter("uid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath()+"/admin/users?error=2");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if("admin".equals(usersDAO.getItem(userLogin.getId()).getUsername()) || (uid == userLogin.getId() )) {
			User item = usersDAO.getItem(uid);
			if (item != null) {
				request.setAttribute("user", item);
				RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp");
				rd.forward(request, response);
			}else {
				response.sendRedirect(request.getContextPath()+"/admin/users?error=2");
				return;
			}
			
		}else {
			// không có quyền
			response.sendRedirect(request.getContextPath()+"/admin/users?error=5");
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
		
		int uid = 0;
		try {
			uid = Integer.parseInt (request.getParameter("uid"));
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+"/admin/users?error=1");
			return;
		}
		
		

		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		if("admin".equals(usersDAO.getItem(userLogin.getId()).getUsername()) || (uid == userLogin.getId() )) {
			// lấy lại dữ liệu cũ
			User itemUser = usersDAO.getItem(uid);
			if(itemUser == null) {
				response.sendRedirect(request.getContextPath()+"/admin/users?error=1");
				return;
			}
			
			String password = request.getParameter("password");
			
			// kiểm tra password
			if("".equals(password)) {
				password = itemUser.getPassword();
			}else {
				password = StringUtil.md5(password);
			}
			
			String fullname = request.getParameter("fullname");
			
			// sét lại dữ liệu mới
			
			itemUser.setPassword(password);
			itemUser.setFullname(fullname);
				
			
			if(usersDAO.edituseradmin(itemUser) > 0) {
				response.sendRedirect(request.getContextPath()+ "/admin/users?msg=2");
				return;
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp?error=1");
				rd.forward(request, response);
				return;
			}
			
		}else {
			// không có quyền
			response.sendRedirect(request.getContextPath()+"/admin/users?error=5");
			return;
		}
		
		
		
		
		
	}

}
