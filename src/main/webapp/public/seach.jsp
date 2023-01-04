<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>
<div class="content_resize">
  <div class="mainbar">
  <%
	String msg = "";
	if (request.getParameter("msg") != null) {
		msg = (String) request.getParameter("msg");
	}
	switch (msg){
	case "1": 
		%>
		<p style="color: green">Tìm bài hát thành công!</p>
		<%
		break;
	}

   %>
   <%
	    if(request.getAttribute("items")!= null){
		ArrayList<Song> items =(ArrayList<Song>) request.getAttribute("items");
		if(items != null){
			int i = 0;
			for(Song item: items){
				i++;
	%>
  
    <div class="article">
      <h2><a href="<%=request.getContextPath() %>/detail?id=<%=item.getId() %>" title="<%=item.getName()%>"><%=item.getName() %></a></h2>
      <p class="infopost">Ngày đăng: <%=item.getDateCreate() %>. Lượt xem: <%=item.getCounter() %> <a href="#" class="com"><span><%=i %></span></a></p>
      <div class="clr"></div>
      <div class="img"><img src="<%=request.getContextPath() %>/files/<%=item.getPicture()%>" width="177" height="213" alt="<%=item.getName()%>" class="fl" /></div>
      <div class="post_content">
        <p><%=item.getPreviewText()%></p>
        <p class="spec"><a href="<%=request.getContextPath() %>/detail?id=<%=item.getId() %>" class="rm">Chi tiết &raquo;</a></p>
      </div>
      <div class="clr"></div>
    </div>
   <%}}}  %>
  
  </div>
 
  <div class="sidebar">
      <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
