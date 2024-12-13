<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                    <meta name="author" content="Hỏi Dân IT" />
                    <title>Chi tiết đơn hàng</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Quản lí sản phẩm</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item active"><a href="/admin/order">Quản lí đơn hàng</a>
                                        </li>
                                        <li class="breadcrumb-item active">Thông tin đơn nhận</li>
                                    </ol>
                                    <div class="container mt-3">
                                        <div class="card" style="width: 100%">
                                            <div class="card-header">
                                                Thông tin đơn nhận
                                            </div>
                                            <ul class="list-group list-group-flush">
                                                <li class="list-group-item">ID: ${order.id}</li>
                                                <li class="list-group-item">Người đặt: ${order.user.fullName}
                                                </li>
                                                <li class="list-group-item">Họ và tên người nhận: ${order.receiverName}
                                                </li>
                                                <li class="list-group-item">Số điện thoại: ${order.receiverPhone}</li>
                                                <li class="list-group-item">Địa chỉ: ${order.receiverAddress}</li>
                                                <li class="list-group-item">Thành tiền: ${order.totalPrice}</li>
                                                <li class="list-group-item">Trạng thái: ${order.status}</li>
                                            </ul>
                                        </div>
                                        <a href="/admin/order/detail/${order.id}" class="btn btn-info mt-3">Xem chi
                                            tiết</a>
                                    </div>
                                </div>
                            </main>
                            <jsp:include page="../layout/footer.jsp" />
                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/js/scripts.js"></script>
                </body>

                </html>