<%@page import="model.bean.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Sửa người dùng</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        <%
	        String username = request.getParameter("username");
        	String fullname = request.getParameter("fullname");
        
		   	 User user = (User) request.getAttribute("user");
		   	 if(user != null){
		   		username = user.getUsername();
		   		fullname = user.getFullname();
		   		 
	    	}
	        	
	        String error = request.getParameter("error");
	    	if("1".equals(error)){
	    		out.print("<span style =\"background: yellow; color: red; font-weight: bold; padding: 5px;\">Có lỗi xảy ra</span>");
	    	}
        
        %>
        <hr />
        <div class="row">
            <div class="col-md-12">
                <!-- Form Elements -->
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-md-12">
                                <form action=""  role="form" method="post" id="form">
                                
                                    <div class="form-group">
                                        <label for="username">Tên người dùng</label>
                                        <input type="text" id="username" value="<%if(username != null) out.print(username); %>" name="username" class="form-control" disabled="disabled"/>
                                    </div>
                                  	 <div class="form-group">
                                        <label for="username">Mật khẩu</label>
                                        <input type="password" id="password" value="" name="password" class="form-control" />
                                    </div>
                                  <!-- 
                                    <div class="form-group">Mật khẩu</label>
                                         <input type="password" id="password" value="" name="password" class="form-control" />
                                    </div> -->
                                    <div class="form-group">
                                        <label for="fullname">Fullname</label>
                                        <input type="text" id="fullname" value="<%if(fullname != null) out.print(fullname); %>" name="fullname" class="form-control" /><br />
                                   </div>
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Update</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- End Form Elements -->
            </div>
        </div>
        <!-- /. ROW  -->
    </div>
    <!-- /. PAGE INNER  -->
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('#form').validate({
		rules:{
			"password":	{   
				required: true,
				minlength: 8
				maxlength:15
				
			},
			"fullname":	{   
				required: true,
				
			},
			
		},
		messages:{
			"password":	{   
				required: "Bạn chưa nhập thông tin !",
			},
			"fullname":	{   
				required: "Bạn chưa nhập thông tin !",
			},
								
		},
	});

}); 
    document.getElementById("users").classList.add('active-menu');
</script>
<style>
	.error {
		color: red;
		padding-left: 5px;
		font-size: 12px;
		font-style: italic;
	}
	
</style>
<!-- /. PAGE WRAPPER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>