<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/public/inc/header.jsp" %>

<div class="content_resize">
  <div class="mainbar">
    <div class="article">
      <h2><span>Liên hệ</span></h2>
      <div class="clr"></div>
      <%
      	String msg = request.getParameter("msg");
      	String error = request.getParameter("error");
      	
      	String name = request.getParameter("name");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String message = request.getParameter("message");
      	
      	if("1".equals(msg)){
      		out.print("<span style=\" background: yellow; color: green; font-weight: bold; padding: 5px\">Gửi liên hệ thành công!</span>");
      		
      	}
      	
      	if("1".equals(error)){
      		out.print("<span style=\" background: yellow; color: red; font-weight: bold; padding: 5px\">Gửi liên hệ thất bại!</span>");
      		
      	}
      		
      %>
      
      
      <p>Khi bạn có thắc mắc, vui lòng gửi liên hệ, chúng tôi sẽ phản hồi trong thời gian sớm nhất.</p>
    </div>
    <div class="article">
      <h2>Gửi liên hệ đến chúng tôi</h2>
      <div class="clr"></div>
      <form action="" method="post" id="sendemail">
        <ol>
          <li>
            <label for="name">Họ tên </label>
            <input id="name" value="<%if(name != null) out.print(name); %>" name="name" class="text" />
          </li>
          <li>
            <label for="email">Email </label>
            <input id="email" value="<%if(email != null) out.print(email); %>" name="email" class="text" />
          </li>
          <li>
            <label for="website">Website</label>
            <input id="website" value="<%if(website != null) out.print(website); %>" name="website" class="text" />
          </li>
          <li>
            <label for="message">Nội dung</label>
            <textarea id="message" name="message" rows="8" cols="50"><%if(message != null) out.print(message); %></textarea>
          </li>
          <li>
            <input type="image" name="imageField" id="imageField" src="<%=request.getContextPath() %>/templates/public/images/submit.gif" class="send" />
            <div class="clr"></div>
          </li>
        </ol>
      </form>
    </div>
  </div>
  <div class="sidebar">
  <%@ include file="/templates/public/inc/leftbar.jsp" %>
  </div>
  <div class="clr"></div>
</div>
<%@ include file="/templates/public/inc/footer.jsp" %>
<script type="text/javascript">
	$(document).ready(function(){
		$('#sendemail').validate({
			ignore:[],
			rules:{
				"name":	{   
					required: true,
					
				},
				"email":	{   
					required: true,
					
				},
			
				
				"message":	{   
					required: true,
					
				},
				
				
			},
			messages:{
				"name":	{   
					required: "Bạn chưa nhập thông tin !",
				},
				
				"email":	{   
					required: "Bạn chưa nhập thông tin !",
				},
				
				
				"message":	{   
					required: "Bạn chưa nhập thông tin !",
				},
			},
		});
	
	}); 
   
</script>
<script type="text/javascript">
	var editor = CKEDITOR.replace('message');
</script>
<style>
	.error {
		color: red;
		padding-left: 5px;
		font-size: 12px;
		font-style: italic;
	}
	
</style>
