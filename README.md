# Library-Management
Một ứng dụng quản lý thư viện giúp quản lý sách, độc giả, mượn/trả sách và thông báo tự động.
## Tính năng chính
- Quản lý sách (thêm, sửa, xóa, tìm kiếm)
- Quản lý độc giả
- Quản lý mượn/trả sách
- Thông báo sách sắp đến hạn trả
- Dashboard trực quan cho quản lý
## Cài đặt
1. Clone repository: https://github.com/username/library-management.git
2. Backend (Spring Boot):
- Cài Java 21 và Maven
- Chạy: `mvn spring-boot:run`
3. Frontend (ReactJS):
- Cài Node.js
- Vào thư mục `frontend` và chạy:
  ```
  npm install
  npm start
  ```
## Hướng dẫn sử dụng
- Truy cập `http://localhost:3000` để mở giao diện web
- Đăng nhập bằng tài khoản admin (username: admin, password: admin) mặc định
- Thêm sách, quản lý mượn/trả, xem báo cáo
## Công nghệ sử dụng
- Backend: Java, Spring Boot
- Frontend: ReactJS
- Database: MySQL
- Giao tiếp: REST API
