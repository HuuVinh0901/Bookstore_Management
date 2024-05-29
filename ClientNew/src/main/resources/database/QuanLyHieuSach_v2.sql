CREATE DATABASE QLHieuSach

go
-- Sử dụng cơ sở dữ liệu QLHieuSach
USE QLHieuSach
go
CREATE TABLE TaiKhoan (
    idTaiKhoan NVARCHAR(14) NOT NULL PRIMARY KEY,
    matKhau NVARCHAR(255) NOT NULL, 
    ngayLap DATE DEFAULT GETDATE(), 
)
CREATE TABLE NhanVien (
    idNhanVien NVARCHAR(14) NOT NULL PRIMARY KEY,
    tenNhanVien NVARCHAR(50) NOT NULL,
    soDienThoai NVARCHAR(10),
    diaChi NVARCHAR(50),
    email NVARCHAR(50),
    ngaySinh DATE CHECK (ngaySinh < GETDATE()), 
    gioiTinh BIT,
    chucVu NVARCHAR(50),
    trangThai BIT DEFAULT 1, -- '1' cho 'Đang làm việc', '0' cho 'Đã nghỉ việc'
   
)
CREATE TABLE QuanLy (
    idQuanLy NVARCHAR(14) NOT NULL PRIMARY KEY,
    tenNhanVien NVARCHAR(50) NOT NULL,
    soDienThoai NVARCHAR(10),
    diaChi NVARCHAR(50),
    email NVARCHAR(50),
    ngaySinh DATE CHECK (ngaySinh < GETDATE()), 
    gioiTinh BIT,
    chucVu NVARCHAR(50),
    trangThai BIT DEFAULT 1, -- '1' cho 'Đang làm việc', '0' cho 'Đã nghỉ việc'
   
)


go
CREATE TABLE KhachHang(
	idKhachHang NVARCHAR(14) not null PRIMARY KEY,
	tenKhachHang NVARCHAR(50) not null,
	soDienThoai NVARCHAR(10),
	email NVARCHAR(50),
	diaChi NVARCHAR(50),
	ngaySinh DATE CHECK (ngaySinh < GETDATE()), 
	gioiTinh BIT
)
go
CREATE TABLE HoaDon (
    idDonHang NVARCHAR(14) NOT NULL PRIMARY KEY, 
    ngayLap DATE NOT NULL, 
	nhanVien NVARCHAR(14) NOT NULL, 
    khachHang NVARCHAR(14) NOT NULL, 
    tienKhachDua FLOAT CHECK (tienKhachDua >= 0), 
	tongTien FLOAT,
	tongLoiNhuan FLOAT,
    FOREIGN KEY (khachHang) REFERENCES KhachHang(idKhachHang), 
    FOREIGN KEY (nhanVien) REFERENCES NhanVien(idNhanVien)
)
go

CREATE TABLE NhaCungCap(
	idNhaCungCap NVARCHAR(15) NOT NULL PRIMARY KEY,
	tenNhaCungCap NVARCHAR(50) NOT NULL,
	diaChi NVARCHAR(100),
	soDienThoai NVARCHAR(10)
)
go
CREATE TABLE LoaiSanPham (
    idLoaiSanPham NVARCHAR(15) PRIMARY KEY,
    tenLoaiSanPham NVARCHAR(50) NOT NULL
)


go
CREATE TABLE TacGia(
	idTacGia NVARCHAR(14) NOT NULL PRIMARY KEY,
	tenTacGia NVARCHAR(30) NOT NULL,
	ngaySinh DATE CHECK (ngaySinh < GETDATE()), 
	soLuongTacPham INT CHECK (soLuongTacPham >= 0)
)
go
CREATE TABLE TheLoai(
	idTheLoai NVARCHAR(14) NOT NULL PRIMARY KEY,
	tenTheLoai NVARCHAR(100) NOT NULL,
	soLuongSach INT CHECK (soLuongSach >= 0),
	moTa NVARCHAR(100) NOT NULL
)
go
CREATE TABLE SanPham (
    idSanPham NVARCHAR(14) NOT NULL PRIMARY KEY, 
    tenSanPham NVARCHAR(30) NOT NULL, 
	loaiSanPham NVARCHAR(15) NOT NULL,
    nhaCungCap NVARCHAR(15) NOT NULL,
    kichThuoc FLOAT NOT NULL, 
    mauSac NVARCHAR(255) NOT NULL, 
    trangThai BIT NOT NULL ,
	thue FLOAT CHECK (thue >= 0),
	giaNhap FLOAT CHECK (giaNhap >= 0),
	soLuong INT ,
	giaBan FLOAT,
	giaKhuyenMai FLOAT,
	FOREIGN KEY (loaiSanPham) REFERENCES LoaiSanPham(idLoaiSanPham),
    FOREIGN KEY (nhaCungCap) REFERENCES NhaCungCap(idNhaCungCap)
)

go
CREATE TABLE ChiTietHoaDonSanPham (
    soLuong INT CHECK (soLuong > 0), 
    idDonHang NVARCHAR(14) NOT NULL , 
    idSanPham NVARCHAR(14) NOT NULL, 
	thanhTien FLOAT,
	loiNhuan FLOAT,
	PRIMARY KEY (idDonHang, idSanPham),
    FOREIGN KEY (idDonHang) REFERENCES HoaDon(idDonHang), 
    FOREIGN KEY (idSanPham) REFERENCES SanPham(idSanPham)
)
go
CREATE TABLE Sach (
    idSanPham NVARCHAR(13) NOT NULL PRIMARY KEY, 
	tenSanPham NVARCHAR(100) NOT NULL, 
    tacGia NVARCHAR(14) NOT NULL, 
    theLoai NVARCHAR(14) NOT NULL,
    namXuatBan DATE NOT NULL CHECK (YEAR(namXuatBan) <= YEAR(GETDATE())),
    ISBN NVARCHAR(255) NOT NULL, 
    soTrang INT , 
	loaiSanPham NVARCHAR(15) NOT NULL,
    nhaCungCap NVARCHAR(15) NOT NULL,
    kichThuoc FLOAT NOT NULL, 
    mauSac NVARCHAR(255) NOT NULL, 
    trangThai BIT NOT NULL ,
	thue FLOAT CHECK (thue >= 0),
	soLuong INT,
	giaNhap FLOAT CHECK (giaNhap >= 0),
	giaBan FLOAT,
	giaKhuyenMai FLOAT
	FOREIGN KEY (loaiSanPham) REFERENCES LoaiSanPham(idLoaiSanPham),
    FOREIGN KEY (nhaCungCap) REFERENCES NhaCungCap(idNhaCungCap),
    FOREIGN KEY (tacGia) REFERENCES TacGia(idTacGia), 
    FOREIGN KEY (theLoai) REFERENCES TheLoai(idTheLoai)
)

go
CREATE TABLE ChiTietHoaDonSach (
    soLuong INT CHECK (soLuong > 0), 
    idDonHang NVARCHAR(14) NOT NULL , 
    idSanPham NVARCHAR(13) NOT NULL, 
	thanhTien FLOAT,
	loiNhuan FLOAT,
	PRIMARY KEY (idDonHang, idSanPham),
    FOREIGN KEY (idDonHang) REFERENCES HoaDon(idDonHang), 
    FOREIGN KEY (idSanPham) REFERENCES Sach(idSanPham)
)
go
CREATE TABLE HoaDonCho (
	idDonHangCho NVARCHAR(15) NOT NULL PRIMARY KEY, 
    idDonHang NVARCHAR(14) NOT NULL, 
	idKhachHang NVARCHAR(14) NOT NULL,
	tenKhachHang NVARCHAR(30) NOT NULL,
	soDienThoai NVARCHAR(14) NOT NULL,
    ngayLap DATE NOT NULL
    FOREIGN KEY (idKhachHang) REFERENCES KhachHang(idKhachHang), 
)
go

CREATE TABLE ChiTietHoaDonChoSach (
    idDonHangCho NVARCHAR(15) NOT NULL , 
	idDonHang NVARCHAR(14) NOT NULL , 
	tenSanPham NVARCHAR(30) NOT NULL , 
    idSanPham NVARCHAR(13) NOT NULL, 
	giaBan FLOAT,
	khuyenMai FLOAT,
	soLuong INT CHECK (soLuong > 0), 
	giaCuoi FLOAT,
	thanhTien FLOAT,
	PRIMARY KEY (idDonHangCho, idSanPham),
    FOREIGN KEY (idDonHangCho) REFERENCES HoaDonCho(idDonHangCho), 
    FOREIGN KEY (idSanPham) REFERENCES Sach(idSanPham)
)
go
CREATE TABLE ChiTietHoaDonChoSanPham (
    idDonHangCho NVARCHAR(15) NOT NULL , 
	idDonHang NVARCHAR(14) NOT NULL , 
	tenSanPham NVARCHAR(30) NOT NULL , 
    idSanPham NVARCHAR(14) NOT NULL, 
	giaBan FLOAT,
	khuyenMai FLOAT,
	soLuong INT CHECK (soLuong > 0), 
	giaCuoi FLOAT,
	thanhTien FLOAT,
	PRIMARY KEY (idDonHangCho, idSanPham),
    FOREIGN KEY (idDonHangCho) REFERENCES HoaDonCho(idDonHangCho), 
    FOREIGN KEY (idSanPham) REFERENCES SanPham(idSanPham)
)

go
CREATE TABLE KhuyenMai (
    idKM NVARCHAR(14) NOT NULL PRIMARY KEY,
    tenKM NVARCHAR(20) NOT NULL, 
    ngayBatDau DATE DEFAULT GETDATE(), 
    trangThai bit,
	loaiKM NVARCHAR(8)
)
CREATE TABLE ApDungKhuyenMai (
    idSP nvarchar(14) PRIMARY KEY,
    idKM nvarchar(14),
	tenSP nvarchar(30),
	giaBan float,
	giaKM float,
	FOREIGN KEY (idKM) REFERENCES KhuyenMai(idKM),
	FOREIGN KEY (idSP) REFERENCES SanPham(idSanPham),
	
);
CREATE TABLE ApDungKhuyenMaiSach (
    idS nvarchar(13) PRIMARY KEY,
    idKM nvarchar(14),
	tenSP nvarchar(30),
	giaBan float,
	giaKM float,
	FOREIGN KEY (idKM) REFERENCES KhuyenMai(idKM),
	FOREIGN KEY (idS) REFERENCES Sach(idSanPham),
	
);

go
--Quản lý
CREATE TRIGGER trg_GenerateQuanLyID
ON QuanLy
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(15);
    DECLARE @MaxID NVARCHAR(15);

    SELECT @MaxID = MAX(idQuanLy) FROM QuanLy;

    IF @MaxID IS NULL
        SET @NewID = 'QL' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'QL' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO QuanLy(idQuanLy,tenNhanVien,soDienThoai,diaChi,email,ngaySinh,gioiTinh,chucVu,trangThai)
    SELECT @NewID,tenNhanVien,soDienThoai,diaChi,email,ngaySinh,gioiTinh,chucVu,trangThai
    FROM INSERTED
END;
go
--Nhân viên
CREATE TRIGGER trg_GenerateNhanVienID
ON NhanVien
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(15);
    DECLARE @MaxID NVARCHAR(15);

    SELECT @MaxID = MAX(idNhanVien) FROM NhanVien;

    IF @MaxID IS NULL
        SET @NewID = 'NV' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'NV' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO NhanVien(idNhanVien,tenNhanVien,soDienThoai,diaChi,email,ngaySinh,gioiTinh,chucVu,trangThai)
    SELECT @NewID,tenNhanVien,soDienThoai,diaChi,email,ngaySinh,gioiTinh,chucVu,trangThai
    FROM INSERTED
END;
go
--KhachHang
CREATE TRIGGER trg_GenerateKhachHangID
ON KhachHang
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(15);
    DECLARE @MaxID NVARCHAR(15);

    SELECT @MaxID = MAX(idKhachHang) FROM KhachHang;

    IF @MaxID IS NULL
        SET @NewID = 'KH' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'KH' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO KhachHang(idKhachHang,tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh)
    SELECT @NewID,tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh
    FROM INSERTED
END;
go
--loai san pham 
CREATE TRIGGER trg_GenerateLoaiSanPhamID
ON LoaiSanPham
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(15);
    DECLARE @MaxID NVARCHAR(15);

    SELECT @MaxID = MAX(idLoaiSanPham) FROM LoaiSanPham;

    IF @MaxID IS NULL
        SET @NewID = 'LSP' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'LSP' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO LoaiSanPham(idLoaiSanPham,tenLoaiSanPham)
    SELECT @NewID,tenLoaiSanPham
    FROM INSERTED
END
go
--Nha Cung Cap 
CREATE TRIGGER trg_GenerateNhaCungCapID
ON NhaCungCap
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(15);
    DECLARE @MaxID NVARCHAR(15);

    SELECT @MaxID = MAX(idNhaCungCap) FROM NhaCungCap;

    IF @MaxID IS NULL
        SET @NewID = 'NCC' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'NCC' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO NhaCungCap(idNhaCungCap,tenNhaCungCap,diaChi, soDienThoai)
    SELECT @NewID,tenNhaCungCap, diaChi, soDienThoai
    FROM INSERTED
END
go
--Tac gia
CREATE TRIGGER trg_GenerateTacGiaID
ON TacGia
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(14);
    DECLARE @MaxID NVARCHAR(14);

    SELECT @MaxID = MAX(idTacGia) FROM TacGia;

    IF @MaxID IS NULL
        SET @NewID = 'TG' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'TG' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO TacGia(idTacGia,tenTacGia,ngaySinh, soLuongTacPham)
    SELECT @NewID,tenTacGia, ngaySinh, soLuongTacPham
    FROM INSERTED
END
go

--The Loai
CREATE TRIGGER trg_GenerateTheLoaiID
ON TheLoai
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(14);
    DECLARE @MaxID NVARCHAR(14);

    SELECT @MaxID = MAX(idTheLoai) FROM TheLoai;

    IF @MaxID IS NULL
        SET @NewID = 'TL' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'TL' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO TheLoai(idTheLoai,tenTheLoai,soLuongSach,moTa)
    SELECT @NewID,tenTheLoai,soLuongSach,moTa
    FROM INSERTED
END
go
--Sach
CREATE TRIGGER trg_GenerateSachID
ON Sach
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @NewID NVARCHAR(13);
    DECLARE @MaxID NVARCHAR(13);

    SELECT @MaxID = MAX(idSanPham) FROM Sach;

    IF @MaxID IS NULL
        SET @NewID = 'S' + FORMAT(GETDATE(), 'yyyyMMdd') + '0001';
    ELSE
    BEGIN
        DECLARE @LastCounter INT;
        SELECT @LastCounter = CAST(RIGHT(@MaxID, 4) AS INT);
        SET @LastCounter = @LastCounter + 1;

        SET @NewID = 'S' + FORMAT(GETDATE(), 'yyyyMMdd') + RIGHT('0000' + CAST(@LastCounter AS NVARCHAR(4)), 4);
    END

    
    INSERT INTO Sach(idSanPham,tenSanPham,tacGia, theLoai, namXuatBan, ISBN, soTrang, loaiSanPham, nhaCungCap, kichThuoc, mauSac, trangThai, thue, soLuong, giaNhap, giaBan, giaKhuyenMai)
    SELECT @NewID,tenSanPham,tacGia, theLoai, namXuatBan, ISBN, soTrang, loaiSanPham, nhaCungCap, kichThuoc, mauSac, trangThai, thue, soLuong, giaNhap, giaBan, giaKhuyenMai
    FROM INSERTED
END

go
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Hữu Vinh',N'0918255167',N'vinhpham123@gmail.com',N'320D phường 7, TP.Bến Tre','2003-01-09',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Vũ Duy','0918288167','duy123@gmail.com','Phan Rang','2003-06-27',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Tấn Đạt','0929255167','datpham@gmail.com',N'Tiền Giang','2003-04-13',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Văn Hoàng','0918255167','hoangle@gmail.com',N'Long Thành,Đồng Nai','2003-01-09',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Bảo Trinh','0379121672','trinhxinhdep@gmail.com',N'Bình Định','2003-11-17',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Hồng Luyên','0976321697','luyenxinh@gmail.com',N'Bình Định','2003-09-29',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Hồng Lưu','0783126567','luuxinh@gmail.com',N'Bình Định','2003-09-29',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Phước Hậu','0783454367','haungo@gmail.com',N'Bến Tre','2003-11-21',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Đức Minh','0776754367','minhcute@gmail.com',N'Bến Tre','2003-01-21',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Thái Công','0989891437','congthai@gmail.com',N'Bến Tre','2003-07-23',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Lan Anh','0778884367','lananh25@gmail.com',N'Bến Tre','2003-11-25',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Mỹ Dung','0765754367','dungxd@gmail.com',N'Bến Tre','2003-02-06',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Thanh Thảo','0744454367','thaodep@gmail.com',N'Kon Tum','2002-02-05',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Duy Thống',N'0917755167',N'thongtruong123@gmail.com',N'329 phường 8, TP.Bến Tre','2003-08-17',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Thành Đạt','0918987867','datcool@gmail.com',N'Bến Tre','2003-06-27',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Tường Vy','0989255167','vynguyen@gmail.com',N'Bến Tre','2003-11-13',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Mỹ Hạnh','0918257767','myhanh@gmail.com',N'222 phương 7, Bến Tre','2003-01-18',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Cát Tường','0879121672','cattuong@gmail.com',N'Bến Tre','2003-05-28',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Tuyết Nhung','0976321688','tuyenxinh@gmail.com',N'Hà Nội','2003-09-29',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Yến Vân','0783776567','yenvan@gmail.com',N'250 phương 7, TP.Bến Tre','2003-09-19',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Tuấn Duy','0783455367','duy23@gmail.com',N'Bến Tre','2003-11-20',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Minh Khang','0746754367','minhkhang@gmail.com',N'Bến Tre','2003-01-16',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Duy Thuận','0989891777','dt@gmail.com',N'Bến Tre','2003-01-16',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Mỹ Vy','0778810367','vydep@gmail.com',N'Bến Tre','2003-11-25',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Tường Vân','0765754797','tv@gmail.com',N'Bến Tre','2003-02-06',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Quốc Tuấn','0744454355','tuan@gmail.com',N'Kon Tum','2002-02-05',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Minh Nhựt','0744554367','minhnhutg@gmail.com',N'Bến Tre','2003-01-26',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Thanh Nhiên','0989891789','tn@gmail.com',N'Bến Tre','2003-01-16',1)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Phương Quyên','0773303675','quyen@gmail.com',N'Bến Tre','2003-01-01',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Ngọc Châu','0765754740','chau@gmail.com',N'Cần Thơ','2003-05-06',0)
insert KhachHang (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Chí Khang','0744454355','khang@gmail.com',N'Kon Tum','2002-08-20',1)
insert KhachHang  (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Quang Vinh','0355430475','vinh@mail',N'125 Bùi Đình Túy, TP.HCM','2023/01/09',1)
insert KhachHang  (tenKhachHang,soDienThoai,email,diaChi,ngaySinh,gioiTinh) values (N'Hoàng Anh','0355430754','hoang123@mail',N'Bình Quới, Bến Tre','2023/01/04',1)



--INSERT INTO Sach VALUES (N'S202311130001', N'Chút gió thoáng qua', N'TG202311230001', N'TL202311230001', '2022-01-01', N'978-123-123-123-2', 200, N'LSP202311230001', N'NCC202311230001', 15.5, N'Nâu', 1, 2500, 22, 500000, 800000,800000);
--INSERT INTO Sach VALUES (N'S202311130002', N'Cây cam ngọt ngào', N'TG202311230001', N'TL202311230001', '2022-01-01', N'978-123-123-123-2', 200, N'LSP202311230003', N'NCC202311230003', 15.5, N'Nâu', 1, 2500, 100, 700000, 1120000,1120000);
--go

--INSERT INTO SanPham VALUES (N'SP202311130001', N'Sản phẩm A', N'LSP202311230001', N'NCC202311230001', 10.0, N'Đỏ', 1, 0, 100000, 50, 160000, 160000);
--INSERT INTO SanPham VALUES (N'SP202311130002', N'Sản phẩm B', N'LSP202311230002', N'NCC202311230002', 12.5, N'Xanh', 1, 0, 120000, 30, 192000, 192000);
--INSERT INTO SanPham VALUES (N'SP202311130003', N'Sản phẩm C', N'LSP202311230003', N'NCC202311230003', 8.0, N'Vàng', 1, 0, 80000, 70, 128000, 128000);
--INSERT INTO SanPham VALUES (N'SP202311130004', N'Sản phẩm D', N'LSP202311230001', N'NCC202311230002', 15.0, N'Đen', 1, 0, 150000, 40, 240000, 240000);
--INSERT INTO SanPham VALUES (N'SP202311130005', N'Sản phẩm E', N'LSP202311230002', N'NCC202311230003', 9.5, N'Hồng', 1, 0, 95000, 60, 152000, 152000);
go
insert TaiKhoan values(N'QL202311300002',N'1111',N'2023-11-30')




--insert TaiKhoan values('ADMIN','1111','2023-11-13')
insert TaiKhoan values('ADMIN',HASHBYTES('SHA2_512', '1111'),'2023-10-13')
select *from NhanVien
select *from TaiKhoan
go
--QL202312120001
--NV202312090001
select *from TheLoai
select *from NhaCungCap
select *from LoaiSanPham
select *from Sach
select *from TacGia
select *from TaiKhoan
select *from NhanVien
select *from QuanLy
select *from HoaDon
select *from SanPham
select *from ChiTietHoaDonSanPham

use master
drop database QLHieuSach

delete SanPham
delete TacGia
delete TheLoai

delete NhaCungCap
delete LoaiSanPham
delete Sach
delete ChiTietHoaDonSanPham
delete ChiTietHoaDonSach


INSERT INTO sanPham (idSanPham, tenSanPham, idLoaiSanPham, idNhaCungCap, kichThuoc, mauSac, trangThai, thue, giaNhap, soLuong, giaBan, giaKhuyenMai)
SELECT temp.idSanPham, temp.tenSanPham, loaiSanPham.id, nhaCungCap.id, temp.kichThuoc, temp.mauSac, temp.trangThai, temp.thue, temp.giaNhap, temp.soLuong, temp.giaBan, temp.giaKhuyenMai
FROM temp
LEFT JOIN loaiSanPham ON loaiSanPham.tenLoaiSanPham = temp.loaiSanPham
LEFT JOIN nhaCungCap ON nhaCungCap.tenNhaCungCap = temp.nhaCungCap;
