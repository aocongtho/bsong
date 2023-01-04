function deletecat(){
	var text;
	var choice = confirm("Bạn đang thực hiện xóa thông tin!");
	
	if (choice == true){
		text = "xoa thanh cong";
		return;
	}else {
		text = "xoa that bai";
		return;
	}
}	
