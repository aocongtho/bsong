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
                                    <%
										String msg = "";
										if (request.getParameter("msg") != null) {
											msg = (String) request.getParameter("msg");
										}
										switch (msg){
										case "1": 
											%>
											<p style="color: green">Tìm thấy bài hát thành công!</p>
											<%
											break;
									
										}
									
									
									%>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <form method="post" action="">
                                        <input type="submit" name="seach" value="Tìm kiếm" class="btn btn-warning btn-sm" style="float:right" />
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
                                        <th>Miêu tả</th>
                                        <th>Chi tiết</th>
                                        <th>Ngày đăng</th>
                                        <th>Lượt đọc</th>
                                        <th>Hình ảnh</th>
                                        <th>Cat_id</th>
                                        <th width="160px">Chức năng</th>
                                    </tr>
                                </thead>
                                <tbody>
	                                  <%
									    if(request.getAttribute("items")!= null){
											ArrayList<Song> items =(ArrayList<Song>) request.getAttribute("items");
											if(items != null){
												for(Song objSong: items){
									  %>
                                    <tr>
                                        <td><%=objSong.getId() %></td>
                                        <td class="center"><%=objSong.getName()%></td>
                                        <td class="center"><%=objSong.getCategory().getId() %></td>
                                        <td class="center"><%=objSong.getPreviewText() %></td>
                                        <td class="center"><%=objSong.getDetailText() %></td>
                                        <td class="center"><%=objSong.getDateCreate() %></td>
                                        <td class="center"><%=objSong.getCounter() %></td>
                                       
                                        <td class="center">
											<img width="50px" height="50px" src="<%=request.getContextPath() %>/files/<%=objSong.getPicture() %>" alt="<%=objSong.getName()%>"/>
                                        </td>
                                       <td class="center"><%=objSong.getCategory().getId() %></td>
                                        <td class="center">
                                            <a href="<%=request.getContextPath() %>/admin/song/edit?sid=<%=objSong.getId() %>" title="" class="btn btn-primary"><i class="fa fa-edit "></i> Sửa</a>
                                            <a onclick="return deletecat()" href="<%=request.getContextPath() %>/admin/song/del?sid=<%=objSong.getId() %>" title="" class="btn btn-danger"><i class="fa fa-pencil"></i> Xóa</a>
                                        </td>
                                    </tr>
									<% }}} %>
									
                                </tbody>
                            </table>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="dataTables_info" id="dataTables-example_info" style="margin-top:27px">Hiển thị từ 1 đến 5 của 24 truyện</div>
                                </div>
                                <div class="col-sm-6" style="text-align: right;">
                                    <div class="dataTables_paginate paging_simple_numbers" id="dataTables-example_paginate">
                                        <ul class="pagination">
                                            <li class="paginate_button previous disabled" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_previous"><a href="#">Trang trước</a></li>
                                            <li class="paginate_button active" aria-controls="dataTables-example" tabindex="0"><a href="">1</a></li>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="">2</a></li>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="">3</a></li>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="">4</a></li>
											<li class="paginate_button" aria-controls="dataTables-example" tabindex="0"><a href="">5</a></li>
                                            <li class="paginate_button next" aria-controls="dataTables-example" tabindex="0" id="dataTables-example_next"><a href="#">Trang tiếp</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
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