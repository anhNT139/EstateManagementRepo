<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglib.jsp"%>
<c:url var="customerAPI" value="/api/customer"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="<c:url value='/admin/home'/>">Trang chủ</a>
                </li>
                <li class="active">
                    <a href="<c:url value='/admin/customer-list'/>">Danh sách khách hàng</a>
                </li>
                <li class="active">${title}</li>
            </ul><!-- /.breadcrumb -->

        </div>

        <div class="page-content">
            <div class="ace-settings-container" id="ace-settings-container">

                <div class="ace-settings-box clearfix" id="ace-settings-box">
                    <div class="pull-left width-50">
                        <div class="ace-settings-item">
                            <div class="pull-left">
                                <select id="skin-colorpicker" class="hide">
                                    <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                    <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                    <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                    <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                </select>
                            </div>
                            <span>&nbsp; Choose Skin</span>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
                            <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
                            <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
                            <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
                            <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
                            <label class="lbl" for="ace-settings-add-container">
                                Inside
                                <b>.container</b>
                            </label>
                        </div>
                    </div><!-- /.pull-left -->

                    <div class="pull-left width-50">
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
                            <label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
                            <label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
                            <label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
                        </div>
                    </div><!-- /.pull-left -->
                </div><!-- /.ace-settings-box -->
            </div><!-- /.ace-settings-container -->

            <div class="row">
                <div class="col-xs-12">
                    <div class="col-xs-12 col-sm-12">

                    <!-- PAGE CONTENT BEGINS -->
                        <span><i style="font-size: small">Các trường (*) không được để trống</i></span>

                        <form:form modelAttribute="customer" class="form-horizontal" role="form" style="margin-top:10px" id="form-customer">

                            <input type="hidden" name="id" id="customer-id" value="${customer.id}">

                            <div class="form-group">
                                <label class="col-sm-2" style="padding-top:7px" for="fullName">Tên (*)</label>
                                <div class="col-sm-10">
                                    <input type="text" id="fullName" name="fullName" class="form-control" value="${customer.fullName}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2" style="padding-top:7px" for="phone">Số điện thoại (*)</label>
                                <div class="col-sm-10">
                                    <input type="number" id="phone" name="phone" class="form-control" value="${customer.phone}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2" style="padding-top:7px" for="email">Email</label>
                                <div class="col-sm-10">
                                    <input type="email" id="email" name="email" class="form-control" value="${customer.email}"/>
                                </div>
                            </div>

                            <c:if test="${submitMethod.equals('PUT')}">
                                <div class="form-group">
                                    <label class="col-sm-2" style="padding-top:7px" for="status">Trạng thái</label>
                                    <div class="col-sm-10">
                                        <form:select path="status">
                                            <form:options items="${statusMap}"/>
                                        </form:select>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label class="col-sm-2" style="padding-top:7px" for="note">Ghi chú</label>
                                <div class="col-sm-10">
                                    <input type="text" id="note" name="note" class="form-control" value="${customer.note}"/>
                                </div>
                            </div>

                            <div style="text-align: center;">
                                <button id="submit" class="btn btn-info">
                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                    ${submitText}
                                </button>

                                &nbsp; &nbsp; &nbsp;
                                <button class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                    Làm mới
                                </button>

                                <img src="/img/loading.gif" style="display: none; height: 50px" id="loading_image1" alt="">
                            </div>
                        </form:form>

                        <c:if test="${submitMethod.equals('PUT')}">
                            <hr width="90%"/>
                            <label class="col-sm-2" style="padding-top:7px; padding-left: 0; margin-bottom: 7px" for="transactions-table">Quá trình hỗ trợ</label>

                            <table id="transactions-table" class="table table-striped table-bordered table-hover" >
                                <thead>
                                    <tr>
                                        <th>Thời gian tạo</th>
                                        <th>Ghi chú</th>
                                        <th>Loại giao dịch</th>
                                        <th>Người tạo</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="item" items="${transactions}">
                                        <tr>
                                            <td>${item.createTime}</td>
                                            <td>${item.note}</td>
                                            <td>${item.codeName}</td>
                                            <td><a style="color: black; text-decoration: underline" href="#" onclick="openModalUserInfo('${item.staffUserName}')">${item.staffUserName}</a> </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <div style="text-align: left;">
                                <button id="show-modal-add-transaction" class="btn btn-success">
                                    <i class="ace-icon fa fa-plus-circle bigger-110"></i>
                                        Thêm
                                </button>
                            </div>
                        </c:if>
                        <!-- PAGE CONTENT ENDS -->
                    </div>
                </div><!-- /.col -->
            </div><!-- /.row -->

        </div><!-- /.page-content -->
    </div>
</div><!-- /.main-content -->

<script>
    $("#submit").click(function (e) {
        $('#loading_image1').show();
        e.preventDefault();
        let data = {};
        let formData = $("#form-customer").serializeArray();
        $.each(formData, function (index, v) {
            data[v.name] = v.value;
        })

        $.ajax({
            type: '${submitMethod}',
            url: '${customerAPI}',
            data: JSON.stringify(data),
            dataType: 'text',
            contentType: 'application/json',
            success: function () {
                $('#loading_image1').hide();
                if ('${submitMethod}' === 'PUT') {
                    showAlert("Bạn đã cập nhật thông tin khách hàng thành công!")
                }
                else {
                    showAlert("Bạn đã thêm mới khách hàng thành công!")
                    document.getElementById("form-customer").reset()
                }
            },
            error: function (response) {
                $('#loading_image1').hide();
                try {
                    let errors = JSON.parse(response.responseText)
                    if ('${submitMethod}' === 'PUT')
                        showAlertError(errors['errors'][0])
                    else
                        showAlertError(errors['errors'][0])
                }
                catch(err) {
                    showAlertError("Có lỗi xảy ra")
                }
            }
        });

    })

</script>

<div id="transactionModal" class="modal fade" role="dialog">
    <div class="modal-dialog" style="margin-top: 10%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 id="modal-title" class="modal-title">Thêm giao dịch</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" style="margin-top:10px" id="form-create-transaction">

                    <div class="form-group">
                        <label class="col-sm-2" style="padding-top:7px" for="transaction-note">Ghi chú</label>
                        <div class="col-sm-10">
                            <input type="text" id="transaction-note" name="note" class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2" style="padding-top:7px" for="transaction-code">Loại giao dịch</label>
                        <br>
                        <div class="col-sm-10">
                            <select id="transaction-code" name="code">
                                <c:forEach var="item" items="${transactionTypes}">
                                    <option value="${item.key}">${item.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <img src="/img/loading.gif" style="display: none; height: 50px" id="loading_image2" alt="">
                <button type="button" class="btn btn-default" id="add-transaction" >Xác nhận</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
            </div>
        </div>

    </div>
</div>

<script>
    $("#show-modal-add-transaction").click(function () {
        $("#transactionModal").modal()
    })

    $("#add-transaction").click(function () {
        $('#loading_image2').show();
        let formData = $("#form-create-transaction").serializeArray()
        let data = {}
        data['customerId'] = $("#customer-id").val()
        $.each(formData, function (index, v) {
            data[v.name] = v.value;
        })
        let tranModal = $("#transactionModal")
        $.ajax({
            type: 'POST',
            url: '${customerAPI}/transaction',
            data: JSON.stringify(data),
            dataType: 'text',
            contentType: 'application/json',
            success: function (response) {
                let objResponse = JSON.parse(response)
                let row = '<tr>'
                row += '<td>' + objResponse.createTime + '</td>'
                row += '<td>' + objResponse.note + '</td>'
                row += '<td>' + objResponse.codeName + '</td>'
                row += '<td><a style="color: black; text-decoration: underline" href="#" onclick="openModalUserInfo(' + "'" + objResponse.staffUserName + "'" + ')">' + objResponse.staffUserName +'</a> </td>'
                row += '</tr>'

                $("#transactions-table tbody").append(row)

                tranModal.modal('hide')
                $('#loading_image2').hide();
                showAlert("Thêm mới giao dịch thành công!")
                tranModal.on('hidden.bs.modal', function(){
                    $(this).find('form')[0].reset();
                });
            },
            error: function (response) {
                $('#loading_image2').hide();
                try {
                    let errors = JSON.parse(response.responseText)
                    tranModal.modal('hide')
                    showAlertError("Thêm giao dịch thất bại! " +  errors['errors'][0])
                }
                catch(err) {
                    showAlertError("Có lỗi xảy ra")
                }
            }
        });
    })
</script>

<div id="userInfoModal" class="modal fade" role="dialog">
    <div class="modal-dialog" style="margin-top: 10%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 id="modal-title-user" class="modal-title" >Thông tin tài khoản</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" style="margin-top:10px">

                    <div class="form-group">
                        <p class="col-sm-2" >Tên đầy đủ:</p>
                        <div class="col-sm-10" >
                            <p id="user-full-name"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <p class="col-sm-2" >Số điện thoại:</p>
                        <div class="col-sm-10" >
                            <p id="user-phone"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <p class="col-sm-2" >Vai trò:</p>
                        <div class="col-sm-10" >
                            <p id="user-role"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <p class="col-sm-2" >Email:</p>
                        <div class="col-sm-10" >
                            <p id="user-email"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <p class="col-sm-2" >Trạng thái:</p>
                        <div class="col-sm-10" >
                            <p id="user-status"></p>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
            </div>
        </div>

    </div>
</div>

<img src="/img/loading.gif" style="display: none; height: 100px; position: fixed; top: 40%; left: 50%" id="loading_image3">

<script>
    function openModalUserInfo(username) {
        $('#loading_image3').show();
        $("#userInfoModal #modal-title-user").html("Thông tin tài khoản " + username)
        $.ajax({
            type: 'GET',
            url: '/api/user?username=' + username,
            dataType: 'json',
            contentType: 'application/json',
            success: function (res) {
                $("#user-full-name").html(res.fullName)
                $("#user-phone").html(res.phone)
                if (res.roleCode === "manager") {
                    $("#user-role").html("Quản lý")
                } else {
                    $("#user-role").html("Nhân viên")
                }


                $("#user-email").html(res.email)
                if (res.status === 0) {
                    $("#user-status").html('Bị khóa')
                } else {
                    $("#user-status").html('Hoạt động')
                }
                $('#loading_image3').hide();
                $("#userInfoModal").modal()
            },
            error: function () {
                $('#loading_image3').hide();
                showAlertError("Load người dùng thất bại")
            }
        });

    }
</script>

</body>
</html>
