<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="formUrl" value="/admin/user-list"/>
<c:url var="formAjax" value="/api/user"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
            Danh sách tài khoản
    </title>
</head>

<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="<c:url value="/admin/home"/>">
                        Trang chủ
                    </a>
                </li>
                <li class="active">
                        Danh sách tài khoản
                </li>
            </ul>
            <!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <!-- PAGE CONTENT BEGINS -->
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="widget-box table-filter">
                                <div class="widget-header">
                                    <h4 class="widget-title">
                                        Tìm kiếm
                                    </h4>
                                    <div class="widget-toolbar">
                                        <a href="#" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-up"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <div class="form-horizontal">
                                            <form:form modelAttribute="searchRequest" action="${formUrl}" id="listForm" method="GET">
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">
                                                            Username hoặc tên
                                                    </label>
                                                    <div class="col-sm-8">
                                                        <div class="fg-line">
                                                            <form:input path="searchValue" cssClass="form-control input-sm"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">
                                                        Trạng thái tài khoản
                                                    </label>
                                                    <div class="col-sm-8">
                                                        <div class="fg-line">
                                                            <form:select path="status" id="status">
                                                                <form:option label="-- Chọn --" value=""/>
                                                                <form:options items="${statusMap}"/>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label"></label>
                                                    <div class="col-sm-8">
                                                        <button id="btnSearch" type="button"
                                                                class="btn btn-sm btn-success">
                                                            Tìm kiếm
                                                            <i class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i>
                                                        </button>
                                                    </div>

                                                </div>
                                                <input id="current-page" type="hidden" name="page" value="1">
                                                <input id="limit-item" type="hidden" name="limit" value="5">
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="table-btn-controls">
                                <div class="pull-right tableTools-container">
                                    <div class="dt-buttons btn-overlap btn-group">
                                        <a flag="info"
                                           class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
                                           data-toggle="tooltip"
                                           title="Thêm người dùng"
                                           href='<c:url value="/admin/user-edit"/>'>
                                            <span>
                                                <i class="fa fa-plus-circle bigger-110"></i>
                                            </span>
                                        </a>
                                        <button id="btnDelete" type="button" disabled
                                                class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
                                                data-toggle="tooltip"
                                                title="Xóa tài khoản" onclick="warningBeforeDelete()">
                                            <span>
                                                <i class="fa fa-trash-o bigger-110"></i>
                                            </span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="table-responsive">
                                <table id="building-list" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Thời gian tạo</th>
                                            <th>Username</th>
                                            <th>Tên</th>
                                            <th>Số điện thoại</th>
                                            <th>Trạng thái</th>
                                            <th>Vai trò</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach var="item" items="${pageableUsers.items}">
                                            <tr id="row-user${item.id}">
                                                <td class="center">
                                                    <label class="pos-rel">
                                                        <input type="checkbox" class="ace" value="${item.id}"/>
                                                        <span class="lbl"></span>
                                                    </label>
                                                </td>
                                                <td>${item.createDate}</td>
                                                <td>${item.username}</td>
                                                <td>${item.fullName}</td>
                                                <td>${item.phone}</td>
                                                <td>${item.status}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.role.equals('manager')}">
                                                            Quản lý
                                                        </c:when>
                                                        <c:otherwise>
                                                            Nhân viên
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.role.equals('manager')}">
                                                            <p>Không đươc thao tác</p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="btn btn-sm btn-primary btn-edit" data-toggle="tooltip"
                                                               title="Cập nhật thông tin"
                                                               href='<c:url value="/admin/user-edit-${item.id}"/>'>
                                                                <i class="fa fa-pencil-square-o"></i>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <ul class="pagination" id="pagination"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<img src="/img/loading.gif" style="display: none; height: 100px; position: fixed; top: 40%; left: 50%" id="loading_image" alt="">

<script type="text/javascript">
    $(document).ready(function () {
        $('#btnSearch').click(function () {
            $('#listForm').submit();
        });
    });

    function warningBeforeDelete() {
        showAlertBeforeDelete(function () {
            $("#loading_image").show()
            event.preventDefault();
            let dataArray = $('tbody input[type=checkbox]:checked').map(function () {
                return $(this).val();
            }).get();
            deleteUser(dataArray);
        });
    }

    function deleteUser(data) {
        $.ajax({
            url: '${formAjax}/',
            type: 'DELETE',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                data.forEach(function (buildingId) {
                    $('#row-user' + buildingId).remove()
                })
                $("#loading_image").hide()
                showAlert("Bạn đã xóa người dùng!")
            },
            error: function (res) {
                $("#loading_image").hide()
                try {
                    let errors = JSON.parse(res.responseText)
                    showAlertError(errors['errors'][0])
                }
                catch(err) {
                    showAlertError("Có lỗi xảy ra! Xóa người dùng không thành công")
                }
            }
        });
    }

    $(function () {
        let currentPage = ${pageableUsers.currentPage};
        let totalPages = ${pageableUsers.totalPages};
        if (currentPage > totalPages) {
            totalPages = currentPage
        }
        window.pagObj = $('#pagination').twbsPagination({
            totalPages: totalPages,
            visiblePages: 10,
            startPage: currentPage,
            onPageClick: function (event, page) {

                if (currentPage !== page) {
                    $("#current-page").val(page)
                    $("#limit-item").val(5)
                    $('#listForm')[0].reset();
                    $("#listForm").submit()
                }
            }
        })
    })

</script>


</body>

</html>