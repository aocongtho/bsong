<%@page import="model.bean.Contact"%>
<%@page import="model.bean.User"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý liên hệ</h2>
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
                                    
									<%
										String msg = "";
										String error = "";
									
										if (request.getParameter("msg") != null) {
											msg = (String) request.getParameter("msg");
										}
										switch (msg){
											
										case "1": 
											%>
											<br/><span style="color: green;background: yellow; font-weight: bold; padding: 5px" >Xóa liên hệ thành công!</span><br/>
											<%
											break;	
										}
									
										if (request.getParameter("error") != null) {
											error = (String) request.getParameter("error");
										}

										switch (error){
										case "1": 
											%>
											<br/><span style="color: green;background: yellow; font-weight: bold; padding: 5px" >ID không tồn tại!</span><br/>
											<%
											break;	
											
										case "2": 
											%>
											<br/><span style="color: green;background: yellow; font-weight: bold; padding: 5px" >Có lỗi xảy ra!</span><br/>
											<%
											break;		
										}
									%>
								</div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form  action="<%=request.getContextPath() %>/admin/contact/seach" method="post">
                                        <input type="submit" name="search" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input name="seach" type="search" class="form-control input-sm" placeholder="Nhập tên người liên hệ " style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên người liên hệ</th>
                                        <th>Email</th>
                                        <th>Webside</th>
                                        <th>Message</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                               <%
                               	ArrayList<Contact> listC =(ArrayList<Contact>) request.getAttribute("listC");
                               	if(listC != null && listC.size() > 0 ){
                               		for(Contact objC: listC){
                               	
                               %>
                                    <tr>
                                        <td><%=objC.getId() %></td>
                                        <td class="center"><%=objC.getName() %></td>
                                        <td class="center"><%=objC.getEmail()%></td>
                                        <td class="center"><%=objC.getWebside() %></td>
                                       	<td class="center"><%=objC.getMessage() %></td>
                                        <td class="center">
                                            <a  href="<%=request.getContextPath() %>/admin/contact/del?did=<%=objC.getId() %>" di title="" class="btn btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa!')"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
									<% }} %>
                                </tbody>
                            </table>
                             <%
                            	int numberOfPages =(Integer) request.getAttribute("numberOfPages");
                            	int currentPage =(Integer) request.getAttribute("currentPage");
                            	int numberOfItems =(Integer) request.getAttribute("numberOfItems");
                            	int numberPage =(Integer) request.getAttribute("numberPage");
                            	if(listC != null && listC.size() > 0){
                            
                            %>
                            
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Tổng có <%=numberOfItems %> liên hệ</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<% 
											if(currentPage == numberOfPages/numberOfPages){
												currentPage = 1;
											}
											%>
                                            <li class="paginate_button previous " aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/contacts?page=<%=currentPage-1%>">Trang trước</a></li>
                                             <%
                                             	
                                            	String active = "";
                                            	for(int i = 1; i <= numberOfPages; i++ ){
                                            		if(currentPage == i){
                                            			active = " active";
                                            		}else{
                                            			active = "";
                                            		}
                                            	
                                            %>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/contacts?page=<%=i%>"><%=i %></a></li>
											<% } %>
											<% 
											if(currentPage == numberOfPages){
												currentPage = 0;
											}
											%>
				   
											
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/contacts?page=<%=currentPage+1%>">Trang tiếp</a></li>
                                       		
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
    document.getElementById("contact").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>