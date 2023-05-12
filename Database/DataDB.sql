use milkteashop_kingtea;

insert into user values ('USER1', 'AdminKingtea', '$2a$10$SNnwNJ7k0yQDSfOhit1RIehUMlkErtJcGc26cGsC.dxbxPfDw6Q32', 
	'ADMIN', '2023-09-05 16:48:20', N'Phan Hồng Sơn', N'17 Bình Chánh', '20110560@student.hcmute.edu.vn', 
    '0785773779', '2002-04-23', null, null, 'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Android%2Fprofile_admin.jpg?alt=media&token=58bb49bf-18d0-4272-877e-96ca4d872c40', true);
insert into user values ('USER2', 'HoangVanNam', '$2a$10$r1LHZGKz8gn.sfmaLD4LMuD8XqymJISAJO7c9tVmRI7.Dhidf84jG', 
	'CUSTOMER', '2023-09-05 16:48:20', N'Hoàng Văn Nam', N'22 Phú Thọ', 'namhoang@gmail.com', 
    '08836667991', '2002-03-26', null, null, 'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Android%2Fprofile_default.jpg?alt=media&token=de6a149b-31fe-4543-8d98-bf5de37cd5d5', true);
insert into user values ('USER3', 'PhanHongSon', '$2a$10$EvQVyzDZ0TCdUyWnY6.HeexR163RWlvixFgsWVdBJjaFM9nQDYwjm', 
	'CUSTOMER', '2023-09-05 16:48:20', N'Phan Hồng Sơn', N'22 Bình Chánh', 'phanhongson234@gmail.com', 
    '0785773779', '2002-04-23', null, null, 'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Android%2Fprofile_user_hongson.jpg?alt=media&token=5e28f65b-576f-4c41-9204-bdbe3dc3c13c', true);
    
    
insert into contact values('CONT1', N'Lỗi đăng nhập', 'Tôi không thể đăng nhập vào tài khoản của mình', 
	'phanhongson234@gmail.com', '0785773779', N'Phan Hồng Sơn', null);
insert into contact values('CONT2', N'Trà Đào quá ngọt', 'Tôi đã đặt ly trà đào 50% đường nhưng sao nó vẫn ngọt ?', 
	'phanhongson234@gmail.com', '0785773779', N'Phan Hồng Sơn', 'USER3');
    

insert into category values('CATE1', N'Trà Sủi Bọt', 
	N'Trà sủi bọt bắt nguồn từ Đài Loan, Trung Quốc vào những năm 1980. Kể từ đó, thức uống này bỗng trở thành đồ uống truyền thống của Đài Loan. Do quá trình lắc trong khi pha chế tạo thành bọt nên nó đã được gọi tên là trà sủi bọt. Trà sủi bọt là thức uống rất được ưa thích ở giới trẻ hiện nay !');
insert into category values('CATE2', N'Trà Con Gái', 
	N'Menu Trà Con Gái với 6 loại Trà ngọt ngào - thơm - dịu dàng - cá tính - xinh đẹp - thanh mát như 6 đặc điểm dễ thương của con gái.');
insert into category values('CATE3', N'Trà Sữa', 
	N'Trà sữa Ba Con Báo thơm ngon, mới lạ với đa dạng các loại hương vị sẽ mang đến cho bạn cảm giác khó quên. Với nguồn nguyên liệu chọn lọc kĩ càng từ Hàn Quốc, ly trà sữa được phục vụ sẽ không khiến bạn thất vọng.');
insert into category values('CATE4', N'Trà Tươi', 
	N'Một cây trà nếu được trồng ở những vùng đất có độ cao và khí hậu khác nhau thì sẽ thu được những loại trà cũng khác nhau. Có thể thấy sự phức tạp đến từ phía bên trong, từ những thành phần cũng như cấu tạo hoá học độc nhất vô nhị mà chỉ mình cây trà có được. Thấu hiểu điều đó, để giữ trọn vị tươi nguyên, bảo toàn dưỡng chất tốt nhất, một búp và hai lá non thường được chúng tôi thu hái vào thời điểm sáng sớm. Tiếp đến, quy trình sản xuất để cho ra các sản phẩm trà chất lượng cũng được thực hiện khép kín.');
insert into category values('CATE5', N'Trà Đặc Biệt', 
	N'Những ly trà được pha chế một cách tỉ mỉ, kết hợp những vị đặc biệt tạo cho bạn một trải nghiệm mới mẻ, mang đến cho bạn hương vị không thể chối từ.');
insert into category values('CATE6', N'Kem Tuyết', 
	N'Kem Tuyết là một món không còn gì xa lạ đối với những tín đồ chuyên uống trà sữa. Đặc biệt vào những ngày nóng bức, còn gì tuyệt vời một ly kem tuyết giải nhiệt ở Ba Con Báo đúng không nè.');
    

insert into topping values('TP1', N'Thạch Sữa Tươi', 5000, 500);
insert into topping values('TP2', N'Thạch Sương Sáo', 5000, 500);
insert into topping values('TP3', N'Thạch Sô Cô La', 5000, 500);
insert into topping values('TP4', N'Thủy tinh Kiwi', 8000, 800);
insert into topping values('TP5', N'Thạch Flan', 5000, 500);
insert into topping values('TP6', N'Trân Châu Đen', 8000, 800);
insert into topping values('TP7', N'Thủy Tinh Chanh Dây', 8000, 800);
insert into topping values('TP8', N'Nha Đam', 5000, 500);
insert into topping values('TP9', N'Sủi Bọt', 15000, 1500);
insert into topping values('TP10', N'Hạt Trái Cây', 8000, 800);
insert into topping values('TP11', N'Thủy Tinh Dâu', 8000, 800);
insert into topping values('TP12', N'Sủi Bọt Phô Mai', 15000, 1500);
insert into topping values('TP13', N'Hạt Đẹp', 10000, 1000);
insert into topping values('TP14', N'Hạt Đường Đen', 10000, 1000);
insert into topping values('TP15', N'Thủy Tinh Ya-ua', 8000, 800);
insert into topping values('TP16', N'Thủy Tinh Nguyên Vị', 8000, 800);
insert into topping values('TP17', N'Thủy Tinh Vải', 8000, 800);


insert into Milk_tea values('MTEA1', N'Trà Bí Đao Sủi Bọt', 100, 36000, 3600,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftrabidao_suibot.jpg?alt=media&token=29b9a5f2-ebb0-4779-805f-89955ce74a47' , 'CATE1');
insert into Milk_tea values('MTEA2', N'Trà Xanh Sủi Bọt', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftraxanh_suibot.jpg?alt=media&token=2bee4623-711d-423e-881f-3fb70a78cfd7' , 'CATE1');
insert into Milk_tea values('MTEA3', N'Hồng Trà Sủi Bọt', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Fhongtra_suibot.jpg?alt=media&token=12aafc83-e2a7-49b3-9ece-caf7937b1260', 'CATE1');
insert into Milk_tea values('MTEA4', N'Trà Alisan Sủi Bọt', 100, 41000, 4100,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftraalisan_suibot.jpg?alt=media&token=8ffe32dc-d81a-4255-9818-e87b71f56054', 'CATE1');
insert into Milk_tea values('MTEA5', N'Trà Gạo Nâu Sủi Bọt', 100, 41000, 4100,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftragaonau_suibot.jpg?alt=media&token=99a36510-472d-4ac3-a531-e63fc342a2c2', 'CATE1');
insert into Milk_tea values('MTEA6', N'Trà Quan Âm Sủi Bọt', 100, 41000, 4100,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftraquanam_suibot.jpg?alt=media&token=fec36802-0667-4023-bc46-e5933fea6c33', 'CATE1');
insert into Milk_tea values('MTEA7', N'Trà Ô Long Sủi Bọt', 100, 41000, 4100,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftraolong_suibot.jpg?alt=media&token=0029b4ba-c0e2-4c6b-99e0-b733fd3500c1', 'CATE1');
insert into Milk_tea values('MTEA8', N'Trà Hoa Sủi Bọt', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sui%20Bot%2Ftrahoa_suibot.jpg?alt=media&token=8a1b39b3-7122-47e0-b4a1-b36932a247a5', 'CATE1'); 
insert into Milk_tea values('MTEA9', N'Trà Gạo Nâu', 100, 35000, 3500,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Con%20Gai%2Ftragao_nau.jpg?alt=media&token=0c6afc95-7017-4349-8ad9-82ea4ebc8c12', 'CATE2');  
insert into Milk_tea values('MTEA10', N'Trà Hoa Lục Trà', 100, 35000, 3500,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Con%20Gai%2Ftrahoa_luctra.jpg?alt=media&token=7e48ca9b-832c-4af5-ab9d-aa1768f64837', 'CATE2'); 
insert into Milk_tea values('MTEA11', N'Trà Mật Ong Nha Đam', 100, 35000, 3500,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Con%20Gai%2Ftramatong_nhadam.jpg?alt=media&token=96dbfd31-bc95-4d3a-84ed-3e146a21e87a', 'CATE2'); 
insert into Milk_tea values('MTEA12', N'Trà Blueberry', 100, 41000, 4100,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Con%20Gai%2Ftrablueberry.jpg?alt=media&token=0f9cbe24-cfd7-44d5-b69c-58aa9426ed41', 'CATE2'); 
insert into Milk_tea values('MTEA13', N'Trà Đẹp', 100, 41000, 4100,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Con%20Gai%2Ftradep.jpg?alt=media&token=d383971e-0213-4e87-9520-fbd4ec52ee7d', 'CATE2'); 
insert into Milk_tea values('MTEA14', N'Trà Bưởi', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Con%20Gai%2Ftra_buoi.jpg?alt=media&token=059575a4-5609-4dbc-8fa2-79768908024c', 'CATE2'); 
insert into Milk_tea values('MTEA15', N'Trà Sữa Bí Đao', 100, 29000, 2900,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_bidao.jpg?alt=media&token=626bedbd-576e-4cb1-a50e-c58ec00b9653', 'CATE3'); 
insert into Milk_tea values('MTEA16', N'Trà Sữa Hoa Lục Trà', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_hoaluctra.jpg?alt=media&token=80f07849-94b2-4520-a33e-5362a1bbd30f', 'CATE3'); 
insert into Milk_tea values('MTEA17', N'Trà Sữa BaConBao', 100, 29000, 2900,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_baconbao.jpg?alt=media&token=b087f9a2-c452-47bf-8dbe-97e1c3bd96bd', 'CATE3'); 
insert into Milk_tea values('MTEA18', N'Trà Sữa Gạo Nâu', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_gaonau.jpg?alt=media&token=e4d60532-7ffc-46c9-9e90-c354b9e624cc', 'CATE3'); 
insert into Milk_tea values('MTEA19', N'Trà Sữa Vải', 100, 29000, 2900,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_vai.jpg?alt=media&token=1116b433-d2b5-4a51-9ec5-b697d433e55d', 'CATE3'); 
insert into Milk_tea values('MTEA20', N'Trà Sữa 3Q', 100, 34000, 3400,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_3q.jpg?alt=media&token=7f275a7e-3cf7-4226-89f1-5e2c7fbae166', 'CATE3'); 
insert into Milk_tea values('MTEA21', N'Trà Sữa Trà Xanh', 100, 29000, 2900,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_traxanh.jpg?alt=media&token=a1402517-d9bc-43fc-aa8e-562998dbde71', 'CATE3'); 
insert into Milk_tea values('MTEA22', N'Trà Sữa Kiwi', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_kiwi.jpg?alt=media&token=b2d6d1a2-02e4-4d89-bd71-96d16dd00340', 'CATE3'); 
insert into Milk_tea values('MTEA23', N'Trà Sữa Trân Châu', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_tranchau.jpg?alt=media&token=75294677-fb8d-4562-b9ee-10f6039ad798', 'CATE3'); 
insert into Milk_tea values('MTEA24', N'Trà Sữa Darjeeling', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_dajeeling.jpg?alt=media&token=9446f6fd-a001-4ba6-b7b3-f67d34bd25d5', 'CATE3'); 
insert into Milk_tea values('MTEA25', N'Trà Sữa Dâu Tây', 100, 29000, 2900,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_dautay.jpg?alt=media&token=95b7cb16-fd71-4c58-9bcd-d0a05f418f85', 'CATE3'); 
insert into Milk_tea values('MTEA26', N'Trà Sữa Cà Phê', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_caphe.jpg?alt=media&token=c1a713dd-f27d-4945-b717-cab0782967a4', 'CATE3'); 
insert into Milk_tea values('MTEA27', N'Trà Sữa Ô Long', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_olong.jpg?alt=media&token=444cdf06-c5e2-4acc-9490-bc9b4f3c458d', 'CATE3'); 
insert into Milk_tea values('MTEA28', N'Trà Sữa Bạc Hà', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_bacha.jpg?alt=media&token=b6008d24-2413-4adc-9f84-de747701f7be', 'CATE3'); 
insert into Milk_tea values('MTEA29', N'Trà Sữa Taichi', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_taichi.jpg?alt=media&token=234a266c-45f3-4254-981f-329a5e2b1e30', 'CATE3'); 
insert into Milk_tea values('MTEA30', N'Trà Sữa Đào', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Sua%2Ftrasua_dao.jpg?alt=media&token=3ac7d5d6-0714-43b6-bea7-9ae8f97a6b58', 'CATE3'); 
insert into Milk_tea values('MTEA31', N'Trà Bí Đao', 100, 28000, 2800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Ftra_bidao.jpg?alt=media&token=e4b5603d-e72b-4822-ad10-5bf3f0569429', 'CATE4'); 
insert into Milk_tea values('MTEA32', N'Hồng Trà Ceylon', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Fhongtra_ceylon.jpg?alt=media&token=ac141ac0-9a41-40cf-9bb6-043a6d3bb075', 'CATE4'); 
insert into Milk_tea values('MTEA33', N'Trà Xanh BaConBao', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Ftraxanh_baconbao.jpg?alt=media&token=0fb95e43-919a-4514-a0db-114610cd78ae', 'CATE4'); 
insert into Milk_tea values('MTEA34', N'Trà Cao Sơn Ô Long', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Ftra_caoson_olong.jpg?alt=media&token=f95afebf-9881-4565-a1a9-30ad16f2d420', 'CATE4'); 
insert into Milk_tea values('MTEA35', N'Cao Sơn Trà Tươi', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Fcaoson_tratuoi.jpg?alt=media&token=abd939f3-f763-441f-b99f-083179dab2d0', 'CATE4'); 
insert into Milk_tea values('MTEA36', N'Hồng Trà Dajeeling', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Fhongtra_dajeeling.jpg?alt=media&token=5e1f3024-1941-4b24-a593-3a59139c0dcb', 'CATE4'); 
insert into Milk_tea values('MTEA37', N'Trà Quan Âm', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Ftra_quanam.jpg?alt=media&token=84a6f370-4703-4ea6-9c91-ca9bd71105e4', 'CATE4'); 
insert into Milk_tea values('MTEA38', N'Trà Alishan Lạnh', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Tra%20Tuoi%2Ftra_alishan_lanh.jpg?alt=media&token=588dbb34-de4a-4aa0-8bd3-5febbd4ab264', 'CATE4'); 
insert into Milk_tea values('MTEA39', N'Trà Xanh Xí Muội', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftraxanh_ximuoi.jpg?alt=media&token=00cca5a9-aa19-434c-922d-3e9e8c2ea37e', 'CATE5'); 
insert into Milk_tea values('MTEA40', N'Trà Xanh Mật Ong Chanh', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftraxanh_matongchanh.jpg?alt=media&token=46b0ca35-24ce-4cab-bfff-781a00349d44', 'CATE5'); 
insert into Milk_tea values('MTEA41', N'Trà Xanh Chanh Dây', 100, 32000, 3200,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftraxanh_chanhday.jpg?alt=media&token=4e447656-c61d-4b4f-a1a3-0607586350de', 'CATE5'); 
insert into Milk_tea values('MTEA42', N'Trà Xanh Xoài', 100, 35000, 3500,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftraxanh_xoai.jpg?alt=media&token=e8e6d01a-540f-4f1f-a6ed-5013ca4623ad', 'CATE5'); 
insert into Milk_tea values('MTEA43', N'Trà Đào', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftra_dao.jpg?alt=media&token=4a831282-00b7-4800-9fca-91980090c576', 'CATE5'); 
insert into Milk_tea values('MTEA44', N'Trà Xanh Kiwi', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftraxanh_kiwi.jpg?alt=media&token=3771ae27-d208-4307-9d4a-d48d02d32235', 'CATE5'); 
insert into Milk_tea values('MTEA45', N'Trà Vải', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftra_vai.jpg?alt=media&token=b788659d-9148-461d-9823-55555bf7166b', 'CATE5'); 
insert into Milk_tea values('MTEA46', N'Trà Raspberry', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftra_raspberry.jpg?alt=media&token=bf0b558a-3b2d-4525-aa36-81d5c0e665f7', 'CATE5'); 
insert into Milk_tea values('MTEA47', N'Hồng Trà Dâu', 100, 35000, 3500,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Fhongtra_dau.jpg?alt=media&token=ff31c32e-742a-4615-ba18-c49fa096c0e7', 'CATE5'); 
insert into Milk_tea values('MTEA48', N'Trà Thanh Xuân', 100, 35000, 3500,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Dac%20Biet%2Ftra_thanhxuan.jpg?alt=media&token=e78e0a54-ad3c-4610-858d-0c2b3cb5b645', 'CATE5'); 
insert into Milk_tea values('MTEA49', N'Kem Tuyết Sô Cô La', 100, 38000, 3800,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Kem%20Tuyet%2Fkemtuyet_socola.jpg?alt=media&token=2b0f1d0b-a16f-4a79-92fc-68167e66728a', 'CATE6'); 
insert into Milk_tea values('MTEA50', N'Kem Tuyết Trà Xanh Nhật Bản', 100, 44000, 4400,
'https://firebasestorage.googleapis.com/v0/b/projectooad-651f1.appspot.com/o/Kem%20Tuyet%2Fkemtuyet_traxanh_nhatban.jpg?alt=media&token=a2f42f9e-f36c-4f2d-9670-63f689046cc0', 'CATE6');


insert into custom_milktea values('CUSMT1', 44000, 4400, 'M', '50%', '70%', 'MTEA50');
insert into custom_milktea values('CUSMT2', 54000, 5400, 'L', '50%', '70%', 'MTEA50');
insert into custom_milktea values('CUSMT3', 64000, 6400, 'XL', '50%', '70%', 'MTEA50');


insert into add_on_milktea values('CUSMT1', 'TP1');
insert into add_on_milktea values('CUSMT1', 'TP2');
insert into add_on_milktea values('CUSMT1', 'TP3');
insert into add_on_milktea values('CUSMT2', 'TP1');
insert into add_on_milktea values('CUSMT2', 'TP5');
insert into add_on_milktea values('CUSMT3', 'TP6');
insert into add_on_milktea values('CUSMT3', 'TP2');


insert into order_shop values('ORD1', N'Phan Hồng Sơn', '0785773779', '22 Bình Chánh', 118000, 17700, 
	'2023-05-09 05:02:30', 'COD', 'Waiting Accept', null, 'USER3');
    
    
insert into order_line values('ORD1', 'CUSMT1', 118000, 17700, 2);
insert into order_line values('ORD1', 'CUSMT2', 64000, 6400, 1);
insert into order_line values('ORD1', 'CUSMT3', 231000, 23100, 3);