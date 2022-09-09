<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="formUrl" value="/api/user"/>
<html>
<head>
    <title>${title}</title>
</head>
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
                    <a href="<c:url value='/admin/home'/>">Trang chủ</a>
                </li>
                <li class="active">
                    <a href="<c:url value='/admin/user-list'/>">Danh sách tài khoản</a>
                </li>
                <li class="active">${title}</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">

                    <form:form id="formEdit" class="form-horizontal" modelAttribute="model">
                        <div id="profile">

                            <div class="form-group">
                                <label class="col-sm-12 "><span><i style="font-size: small">Các trường (*) không được để trống</i></span></label>
                            </div>
                            <div class="space-2"></div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label " style="text-align: left">Vai trò (*)</label>
                                <div class="col-sm-9">
                                    <form:select path="roleCode" id="roleCode">
                                        <form:option value="" label="--- Chọn vai trò ---"/>
                                        <form:options items="${rolesMap}"/>
                                    </form:select>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label " style="text-align: left">
                                   Tên đăng nhập (*)
                                </label>
                                <div class="col-sm-9">
                                    <c:choose>
                                        <c:when test="${not empty model.id}">
                                            <form:input path="username" id="userName" ssClass="form-control" disabled="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <form:input path="username" id="userName" cssClass="form-control"/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label " style="text-align: left">Trạng thái (*)</label>
                                <div class="col-sm-9">
                                    <form:select path="status" id="status">
                                        <form:options items="${statusMap}"/>
                                    </form:select>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label " style="text-align: left">
                                    Tên đầy đủ (*)
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="fullName" id="fullName" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label " style="text-align: left">
                                    Số điện thoại
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="phone" id="phone" cssClass="form-control"/>
                                </div>
                            </div>

                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label " style="text-align: left">
                                    Email
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="email" id="email" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="space-4"></div>

                            <!--Btn-->
                            <div class="col-sm-12">
                                <label class="col-sm-2 control-label message-info"></label>
                                <c:if test="${not empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Cập nhật thông tin" id="btnAddOrUpdateUsers"/>
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Reset mật khẩu" id="btnResetPassword"/>
                                    <img src="/img/loading.gif" style="display: none; height: 50px" id="loading_image">
                                </c:if>
                                <c:if test="${empty model.id}">
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Thêm mới tài khoản" id="btnAddOrUpdateUsers"/>
                                    <img src="/img/loading.gif" style="display: none; height: 50px" id="loading_image">
                                </c:if>
                            </div>

                            <form:hidden path="id" id="userId"/>
                        </form:form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#btnAddOrUpdateUsers").click(function (event) {
        event.preventDefault();

        // prepare form data
        let formData = $("#formEdit").serializeArray();
        let dataArray = {};
        $.each(formData, function (i, v) {
            dataArray["" + v.name + ""] = v.value;
        });

        let userId = $('#userId').val()

        if (userId !== "") { // case update
            dataArray['username'] = $('#userName').val()
            let roleCode = dataArray['roleCode'];
            if (roleCode !== '') {
                $('#loading_image').show();
                updateUser(dataArray);
            } else {
                showAlertError("Vai trò không được để trống")
            }
        }
        else { // create
            let userName = dataArray['username'];
            let roleCode = dataArray['roleCode'];
            if (userName !== '' && roleCode !== '') {
                $('#loading_image').show();
                addUser(dataArray);
            } else {
                showAlertError("Tên đăng nhập và vai trò không được để trống")
            }
        }
    });

    $('#btnResetPassword').click(function (event) {
        event.preventDefault();
        $('#loading_image').show();
        resetPassword($('#userId').val());
    });

    function handleError(res) {
        $('#loading_image').hide();
        try {
            let errors = JSON.parse(res.responseText)
            showAlertError(errors['errors'][0])
        }
        catch(err) {
            showAlertError("Có lỗi xảy ra")
        }
    }

    function addUser(data) {
        $.ajax({
            url: '${formUrl}',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                $('#loading_image').hide();
                document.getElementById("formEdit").reset()
                showAlert("Bạn đã thêm mới người dùng!")
            },
            error: function (res) {
                handleError(res)
            }
        });
    }

    function updateUser(data) {
        $.ajax({
            url: '${formUrl}',
            type: 'PUT',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                $('#loading_image').hide();
                showAlert("Bạn đã cập nhật thông tin người dùng!")
            },
            error: function (res) {
                handleError(res)
            }
        });
    }

    function resetPassword(id) {
        $.ajax({
            url: '${formUrl}/password/'+id+'/reset',
            type: 'PUT',
            dataType: 'json',
            success: function () {
                $('#loading_image').hide();
                showAlert("Bạn đã reset mật khẩu người dùng!")
            },
            error: function (res) {
                handleError(res)
            }
        });
    }

</script>
</html>
