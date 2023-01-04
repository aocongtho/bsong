<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
   <%
		if(request.getParameter("error")!= null){
			int error = Integer.parseInt(request.getParameter("error"));
			if(error == 1){
				out.print("<br/> <p style='color:red'; font-size: 25px; >Không tìm thấy!</p>");
			}
		}	
   %>
  </div>
 
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
