<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Thêm người dùng</h2>
            </div>
        </div>
        <!-- /. ROW  -->
        
         <%
        	String name = request.getParameter("name");
	        String fullname = request.getParameter("fullname");
         
        	String error = request.getParameter("error");
        	if("1".equals(error)){
        		out.print("<span style =\"background: yellow; color: red; font-weight: bold; padding: 5px;\">Có lỗi khi thêm!</span>");
        	}
        	if("2".equals(error)){
        		out.print("<span style =\"background: yellow; color: red; font-weight: bold; padding: 5px;\">Username đã tồn tại!</span>");
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
                                <form action="<%=request.getContextPath() %>/admin/user/add"  role="form" method="post" id="form">
                                    <div class="form-group">
                                        <label for="username">Tên người dùng</label>
                                        <input type="text" id="username" value="<%if(name != null) out.print(name); %>" name="username" class="form-control" />
                                    </div>
                                   <div class="form-group">
                                        <label for="password">Mật khẩu</label>
                                        <input type="password" id="name" value="" name="password" class="form-control" />
                                    </div>
                                     <div class="form-group">
                                        <label for="fullname">Fullname</label>
                                        <input type="text" id="name" value="<%if(fullname != null) out.print(fullname);%>" name="fullname" class="form-control" />
                                    </div>
                                    
                                   
                                    <button type="submit" name="submit" class="btn btn-success btn-md">Thêm</button>
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
			"username":	{   
				required: true,
				maxlength: 50,
				minlength: 2
			},
			
			"fullname":	{   
				required: true,
				
			},
			
		},
		messages:{
			"name":	{   
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