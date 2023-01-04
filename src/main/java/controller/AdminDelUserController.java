package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UsersDAO;
import util.AuthUtil;

public class AdminDelUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UsersDAO usersDAO;
    public AdminDelUserController() {
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
			uid = Integer.parseInt(request.getParameter("uid"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/users?error=2");
			return;
		}
		
		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("userLogin");
		
		// lấy user hiện tại muốn xóa
		User user = usersDAO.getItem(uid);
		if("admin".equals(user.getUsername())) {
			response.sendRedirect(request.getContextPath()+"/admin/users?error=6");
			return;
			
		}else {
			if("admin".equals(userLogin.getUsername())) {
				// được phép xóa
				if(usersDAO.deluseradmin(uid) > 0) {
					response.sendRedirect(request.getContextPath() + "/admin/users?msg=3");
					return;
				}else {
					response.sendRedirect(request.getContextPath() + "/admin/users?error=3");
					return;
				}
				
			} else {
				// không được phép
				response.sendRedirect(request.getContextPath()+"/admin/users?error=6");
				return;
				
			}
		}
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
