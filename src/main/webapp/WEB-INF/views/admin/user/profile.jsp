<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="formUrl" value="/api/user"/>
<html>
<head>
    <title>Chỉnh sửa người dùng</title>
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
                    <a href="#">Trang chủ</a>
                </li>
                <li class="active">Chỉnh sửa người dùng</li>
            </ul><!-- /.breadcrumb -->
        </div>
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <div id="profile">
                        <form:form id="formEdit" class="form-horizontal" modelAttribute="model">
                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">
                                        Tên đăng nhập
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="username" id="userName" cssClass="form-control" disabled="true"/>
                                </div>
                            </div>
                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">
                                        Tên đầy đủ
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="fullName" id="fullName" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">
                                    Số điện thoại
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="phone" id="phone" cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="space-4"></div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right">
                                    Email
                                </label>
                                <div class="col-sm-9">
                                    <form:input path="email" id="email" cssClass="form-control"/>
                                </div>
                            </div>
                            <!--Btn-->
                            <div class="col-sm-12">
                                    <label class="col-sm-3 control-label no-padding-right message-info"></label>
                                    <input type="button" class="btn btn-white btn-warning btn-bold"
                                           value="Cập nhật người dùng" id="btnUpdateUser"/>
                                  <img src="/img/loading.gif" style="display: none; height: 50px" id="loading_image" alt="">
                            </div>
                        <form:hidden path="id" id="userId"/>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        $("#btnUpdateUser").click(function (event) {
            event.preventDefault();
            $('#loading_image').show();
            let formData = $("#formEdit").serializeArray();
            let dataArray = {};
            $.each(formData, function (i, v) {
                dataArray["" + v.name + ""] = v.value;
            });
            dataArray['username'] = $('#userName').val()
            if ($('#userId').val() !== "") {
                updateInfo(dataArray);
            } else {
                $('#loading_image').hide();
                showAlertError("Lỗi khi cập nhật thông tin")
            }
        });

        function updateInfo(data) {
            $.ajax({
                url: '${formUrl}/profile',
                type: 'PUT',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function () {
                    $('#loading_image').hide();
                    showAlert("Cập nhật thông tin thành công!")
                },
                error: function (res) {
                    $('#loading_image').hide();
                    try {
                        let errors = JSON.parse(res.responseText)
                        showAlertError(errors['errors'][0])
                    }
                    catch(err) {
                        showAlertError("Có lỗi xảy ra")
                    }
                }
            });
        }
    </script>
</div>
</body>
</html>
