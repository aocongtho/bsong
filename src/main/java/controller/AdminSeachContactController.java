package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Contact;
import model.dao.ContactDAO;

public class AdminSeachContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ContactDAO contactDAO;   
    public AdminSeachContactController() {
        super();
        contactDAO = new ContactDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String seach = request.getParameter("seach");
		
		ArrayList<Contact> listC = contactDAO.getseach(seach);
		
		if(listC != null && listC.size() > 0) {
			request.setAttribute("listC", listC);
			RequestDispatcher rd = request.getRequestDispatcher("/admin/contact/seach.jsp?msg=1");
			rd.forward(request, response);
			return;
		}else {
			response.sendRedirect(request.getContextPath()+"/admin/contact/noseach.jsp?error=1");
			return;
		}
	}

}
