<%@page import="javax.servlet.annotation.MultipartConfig"%>
<%@page import="model.bean.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp"%>
<%@ include file="/templates/admin/inc/leftbar.jsp"%>
<div id="page-wrapper">
	<div id="page-inner">
		<div class="row">
			<div class="col-md-12">
				<h2>Quản lý người dùng</h2>
			</div>
		</div>
		<!-- /. ROW  -->
		<hr />
		<div class="row">
			<div class="col-md-12">
				<!-- Advanced Tables -->
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="table-responsive">
							<div class="row">
								<div class="col-sm-6">
									<a href="<%=request.getContextPath()%>/admin/user/add"
										class="btn btn-success btn-md">Thêm</a><br />
									<%
									String msg = "";
									String error = "";

									if (request.getParameter("msg") != null) {
										msg = (String) request.getParameter("msg");
									}

									switch (msg) {
									case "1":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">Thêm
										users thành công!</span><br />
									<%
									break;
									case "2":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">Sửa
										users thành công!</span><br />
									<%
									break;

									case "3":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">Xóa
										users thành công!</span><br />
									<%
									break;
									}

									if (request.getParameter("error") != null) {
										error = (String) request.getParameter("error");
									}
									switch (error) {
									case "1":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">Có
										lỗi xảy ra!</span><br />
									<%
									break;

									case "2":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">ID
										không tồn tại!</span><br />
									<%
									break;
									case "3":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">Xóa
										users thất bại!</span><br />
									<%
									break;
									case "4":
									%>
									<br /> <span
										style="color: green; background: yellow; font-weight: bold; padding: 5px">Không
										có quyền thêm!</span><br />
									<%
									break;
									case "5":
										%>
										<br /> <span
											style="color: green; background: yellow; font-weight: bold; padding: 5px">Không
											có quyền sửa!</span><br />
										<%
										break;
									case "6":
										%>
										<br /> <span
											style="color: green; background: yellow; font-weight: bold; padding: 5px">Không
											có quyền xóa!</span><br />
										<%
										break;
									
									
									
									}
									%>
								</div>
								<div class="col-sm-6" style="text-align: right">
									<form action="<%=request.getContextPath()%>/admin/user/seach"
										method="post">
										<input type="submit" name="search" value="Tìm kiếm"
											class="btn btn-warning btn-sm" style="float: right" /> <input
											name="seach" type="search" class="form-control input-sm"
											placeholder="Nhập tên người dùng"
											style="float: right; width: 300px;" />
										<div style="clear: both"></div>
									</form>
									<br />
								</div>
							</div>

							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>ID</th>
										<th>Tên đăng nhập</th>

										<th>Họ tên</th>
										<th width="160px">Chức năng</th>
									</tr>
								</thead>
								<tbody>
									<%
										
									ArrayList<User> listUser = (ArrayList<User>) request.getAttribute("listUser");
									if(listUser != null && listUser.size() >0){
										for(User item: listUser){
									%>
									<tr>
										<td><%=item.getId() %></td>
										<td class="center"><%=item.getUsername() %></td>

										<td class="center"><%=item.getFullname() %></td>
										<%
											if("admin".equals(userLogin.getUsername())){
										%>
										<td class="center"><a
											href="<%=request.getContextPath()%>/admin/user/edit?uid=<%=item.getId()%>"
											title="" class="btn btn-primary"><i class="fa fa-edit "></i>
												Sửa</a> <a
											onclick="return confirm('Bạn có chắc chắn muốn xóa!')"
											href="<%=request.getContextPath()%>/admin/user/del?uid=<%=item.getId()%>"
											title="" class="btn btn-danger"><i class="fa fa-pencil"></i>
												Xóa</a></td>
										<% } else {%>
										<td class="center">
										<% if(userLogin.getId() == item.getId() ) {%>
										<a
											href="<%=request.getContextPath()%>/admin/user/edit?uid=<%=item.getId()%>"
											title="" class="btn btn-primary"><i class="fa fa-edit "></i>
												Sửa</a>
												
												<% } %>
												</td>
										
										<% } %>
									</tr>
									<% }} %>
								</tbody>
							</table>
							 <%
                            	int numberOfPages = (Integer) request.getAttribute("numberOfPages");
                            	int currentPage = (Integer) request.getAttribute("currentPage");
                            	int numberOfItems =(Integer) request.getAttribute("numberOfItems");
                            	int numberPage =(Integer) request.getAttribute("numberPage");
                            	if(listUser != null && listUser.size() > 0 ){
                            		
                            	
                            %>
							
							<div class="row">
								<div class="col-sm-6">
									<div class="dataTables_info" id="dataTables-example_info"
										style="margin-top: 27px">Tổng có <%=numberOfItems %> người dùng</div>
								</div>
								<div class="col-sm-6" style="text-align: right;">
									<div class="dataTables_paginate paging_simple_numbers"
										id="dataTables-example_paginate">
										<ul class="pagination">
										<% 
											if(currentPage == numberOfPages/numberOfPages){
												currentPage = 1;
											}
											%>
											<li class="paginate_button previous "
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/users?page=<%=currentPage-1%>">Trang trước</a></li>
										  <%
	                                          	String active = "";
	                                          	for(int i = 1; i <= numberOfPages; i++) {
	                                          		if(currentPage == i) {
	                                          			active = " active";
	                                          		}else {
	                                          			active = "";
	                                          		}
                                            %>
											<li class="paginate_button <%=active %>"
												aria-controls="dataTables-example" tabindex="0"><a
												href="<%=request.getContextPath()%>/admin/users?page=<%=i%>"><%=i %></a></li>
											<% } %>
											<li class="paginate_button next"
											<% 
											if(currentPage == numberOfPages){
												currentPage = 0;
											}
											%>
												aria-controls="dataTables-example" tabindex="0"
												id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/users?page=<%=currentPage+1%>">Trang tiếp</a></li>
										
										</ul>
									</div>
								</div>
							</div>
							<% } %>
							
						</div>

					</div>
				</div>
				<!--End Advanced Tables -->
			</div>
		</div>
	</div>
</div>
<script>
    document.getElementById("user").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp"%>