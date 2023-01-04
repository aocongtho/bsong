<%@page import="model.bean.Song"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/templates/admin/inc/header.jsp" %>
<%@ include file="/templates/admin/inc/leftbar.jsp" %>
<div id="page-wrapper">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h2>Quản lý bài hát</h2>
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
                                    <a href="<%=request.getContextPath() %>/admin/song/add" class="btn btn-success btn-md">Thêm</a> <br/>
                                    <%
										String msg = "";
										String error = "";
									
										if (request.getParameter("msg") != null) {
											msg = (String) request.getParameter("msg");
										}
										
										
										switch (msg){
										case "1": 
											%>
											<br/><span style="color: green;background: yellow; font-weight: bold; padding: 5px" >Thêm bài hát thành công!</span><br/>
											<%
											break;
										case "2": 
											%>
											<br/><span style="color: green;background: yellow; font-weight: bold; padding: 5px" >Sửa bài hát thành công!</span><br/>
											<%
											break;
											
										case "3": 
											%>
											<br/><span style="color: green;background: yellow; font-weight: bold; padding: 5px" >Xóa bài hát thành công!</span><br/>
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
										
										}
										
									%>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="post" action="<%=request.getContextPath()%>/admin/song/seach">
                                        <input type="submit"  value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
                                        <input name="seach" type="search" class="form-control input-sm" placeholder="Nhập tên bài hát" style="float:right; width: 300px;" />
                                        <div style="clear:both"></div>
                                    </form><br />
                                </div>
                            </div>

                            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên bài hát</th>
                                        <th>Danh mục</th>
                                        <th>Lượt đọc</th>
                                        <th>Hình ảnh</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%
                                	@SuppressWarnings("unchecked")
                                	ArrayList<Song> listSong =(ArrayList<Song>) request.getAttribute("listSong");
                                	if(listSong != null && listSong.size() > 0 ){
                                		for(Song item: listSong){
                                %>
                                    <tr>
                                        <td><%=item.getId() %></td>
                                        <td class="center"><%=item.getName()%></td>
                                        <td class="center"><%=item.getCategory().getName() %></td>
                                        <td class="center"><%=item.getCounter()%></td>
                                       
                                        <td class="center">
                                        <%if(item.getPicture().isEmpty()){ %>
                                        	chưa có hình ảnh 
                                        <%}else { %>
                                        <img width="200px" height="200px" src="<%=request.getContextPath() %>/files/<%=item.getPicture() %>" alt="<%=item.getName()%>"/>
                                         <%} %>
                                        </td>
                                       
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin/song/edit?sid=<%=item.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a onclick="return confirm('Bạn có chắc chắn muốn xóa!') " href="<%=request.getContextPath() %>/admin/song/del?sid=<%=item.getId() %>" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
									<% }} else {%>
									<tr><td colspan="6" align="enter"> Chưa có bài hát nào</td></tr>
									<% } %>
									
                                </tbody>
                            </table>
                            <%
                            	int numberOfPages = (Integer) request.getAttribute("numberOfPages");
                            	int currentPage = (Integer) request.getAttribute("currentPage");
                            	int numberOfItems =(Integer) request.getAttribute("numberOfItems");
                            	int numberPage =(Integer) request.getAttribute("numberPage");
                            	if(listSong != null && listSong.size() > 0 ){
                            		
                            	
                            %>
                            
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Tổng có <%=numberOfItems %> bài hát</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                        	<% 
											if(currentPage == numberOfPages/numberOfPages){
												currentPage = 1;
											}
											%>
											
                                            <li class="paginate_button previous " aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="<%=request.getContextPath()%>/admin/songs?page=<%=currentPage-1%>">Trang trước</a></li>
                                           
                                            <%
                                            	String active = "";
                                            	for(int i = 1; i <= numberOfPages; i++) {
                                            		if(currentPage == i) {
                                            			active = " active";
                                            		}else {
                                            			active = "";
                                            		}
                                            %>
                                            <li class="paginate_button <%=active %>" aria-controls="dataTables-example" tabindex="0"><a href="<%=request.getContextPath()%>/admin/songs?page=<%=i%>"><%=i %></a></li>
                                            <% } %>
                                            
                                            <% 
											if(currentPage == numberOfPages){
												currentPage = 0;
											}
											%>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="<%=request.getContextPath()%>/admin/songs?page=<%=currentPage+1%>">Trang tiếp</a></li>
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
    document.getElementById("song").classList.add('active-menu');
</script>
<!-- /. PAGE INNER  -->
<%@ include file="/templates/admin/inc/footer.jsp" %>