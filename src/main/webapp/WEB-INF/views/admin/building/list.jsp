<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="searchBuildingURL" value="/admin/building-list"/>
<c:url var="getStaffsAPI" value="/api/user/staffs"/>
<c:url var="deleteBuildingAPI" value="/api/building"/>
<c:url var="assignBuildingAPI" value="/api/building/assignment"/>
<html>
<head>
    <title>Danh sách tòa nhà</title>
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
                <li class="active">Tòa nhà</li>
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
                    <!-- PAGE CONTENT BEGINS -->
                    <div class="row">
                        <div class="col-xs-12 col-sm-12">

                            <div class="widget-box" >
                                <div class="widget-header">
                                    <h4 class="widget-title">Tìm kiếm</h4>

                                    <div class="widget-toolbar">
                                        <a href="#" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-down"></i>
                                        </a>

                                    </div>
                                </div>

                                <div class="widget-body">
                                    <div class="widget-main">
                                        <form:form modelAttribute="modelSearch" action="${searchBuildingURL}" class="form-horizontal" id="form-find-building" method="GET">
                                            <div class="form-group">
                                                <div class="col-xs-12 col-sm-6">
                                                    <label for="building-name">Tên toà nhà</label>
                                                    <input type="text" id="building-name" class="form-control" name="name" value="${modelSearch.name}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-6">
                                                    <label for="dien-tich-san">Diện tích sàn</label>
                                                    <input type="number" id="dien-tich-san" class="form-control" name="floorArea" value="${modelSearch.floorArea}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="district">Quận hiện có</label>
                                                    <br>
                                                    <form:select path="district">
                                                        <form:option value="" label="--- Tất cả ---"/>
                                                        <form:options items="${districtsMap}" />
                                                    </form:select>
                                                </div>
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="ward">Phường</label>
                                                    <input type="text" id="ward" class="form-control" name="ward" value="${modelSearch.ward}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="street">Đường</label>
                                                    <input type="text" id="street" class="form-control" name="street" value="${modelSearch.street}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="numberofbasement">Số tầng hầm</label>
                                                    <input type="number" id="numberofbasement" class="form-control" name="numberOfBasement" value="${modelSearch.numberOfBasement}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="direction">Hướng</label>
                                                    <input type="text" id="direction" class="form-control" name="direction" value="${modelSearch.direction}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="level">Hạng</label>
                                                    <input type="text" id="level" class="form-control" name="level" value="${modelSearch.level}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-xs-12 col-sm-3">
                                                    <label for="rent-area-from">Diện tích thuê từ</label>
                                                    <input type="number" id="rent-area-from" class="form-control" name="rentAreaFrom" value="${modelSearch.rentAreaFrom}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-3">
                                                    <label for="rent-area-to">Diện tích thuê đến</label>
                                                    <input type="number" id="rent-area-to" class="form-control" name="rentAreaTo" value="${modelSearch.rentAreaTo}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-3">
                                                    <label for="gia-thue-tu">Giá thuê từ</label>
                                                    <input type="number" id="gia-thue-tu" class="form-control" name="rentPriceFrom" value="${modelSearch.rentPriceFrom}"/>
                                                </div>
                                                <div class="col-xs-12 col-sm-3">
                                                    <label for="gia-thue-den">Giá thuê đến</label>
                                                    <input type="number" id="gia-thue-den" name="rentPriceTo" class="form-control" value="${modelSearch.rentPriceTo}"/>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="manager-name">Tên quản lý</label>
                                                    <input
                                                            type="text" id="manager-name" name="managerName" class="form-control" value="${modelSearch.managerName}"
                                                            <security:authorize access="hasRole('staff')">
                                                                disabled
                                                            </security:authorize>
                                                    />
                                                </div>
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="manager-phone-number">Điện thoại quản lý</label>
                                                    <input
                                                            type="text" id="manager-phone-number" name="managerPhoneNumber" class="form-control" value="${modelSearch.managerPhoneNumber}"
                                                            <security:authorize access="hasRole('staff')">
                                                                disabled
                                                            </security:authorize>
                                                    />
                                                </div>
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="staffName">Chọn nhân viên phụ trách</label>
                                                    <br>
                                                    <security:authorize access="hasRole('staff')">
                                                        <input id="staffName" class="form-control" value="<security:authentication property="principal.fullName"/>" disabled/>
                                                    </security:authorize>

                                                    <security:authorize access="hasRole('manager')">
                                                        <form:select path="staffId">
                                                            <form:option value="" label="--- Tất cả ---"/>
                                                            <form:options items="${staffsMap}" />
                                                        </form:select>
                                                    </security:authorize>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-xs-12 col-sm-4">
                                                    <label for="rentStatus">Trạng thái</label>
                                                    <br>
                                                    <form:select path="rentStatus">
                                                        <form:option value="" label="--- Tất cả ---"/>
                                                        <form:options items="${buildingRentStatus}" />
                                                    </form:select>
                                                </div>
                                                <div class="col-xs-12 col-sm-2">
                                                    <label for="create-date-from">Thời gian tạo từ</label>
                                                    <input style="display: block" type="date" id="create-date-from" name="createDateFrom" value="${modelSearch.createDateFrom}">
                                                </div>
                                                <div class="col-xs-12 col-sm-2">
                                                    <label for="create-date-to">Thời gian tạo đến</label>
                                                    <input style="display: block" type="date" id="create-date-to" name="createDateTo" value="${modelSearch.createDateTo}">
                                                </div>
                                            </div>

                                            <div class="form-group" style="display:flex; margin-left:4px">
                                                <c:forEach var="item" items="${buildingTypes}">
                                                    <input
                                                            type="checkbox" id="${item.key}" name="buildingTypes" value="${item.key}"
                                                            <c:if test="${modelSearch.buildingTypes.contains(item.key)}">checked</c:if>
                                                    >
                                                    <label for="${item.key}" style="margin: 5px 10px 0 2px;"> ${item.value}</label><br>
                                                </c:forEach>
                                            </div>

                                            <input id="current-page" type="hidden" name="page" value="1">
                                            <input id="limit-item" type="hidden" name="limit" value="5">

                                            <button type="button" class="btn btn-sm btn-grey" id="btnReset">
                                                <i class="fa fa-undo" aria-hidden="true"></i>
                                                Làm mới
                                            </button>

                                            <button id="submit-search" type="submit" class="btn btn-sm btn-success" form="form-find-building">
                                                Tìm kiếm
                                                <i class="fa fa-arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </form:form>
                                    </div>
                                </div>
                            </div>

                            <div class="table-btn-controls">
                                <div style="margin-top:10px">
                                    <security:authorize access="hasRole('manager')">
                                        <button id="deleteBuilding" class="btn btn-white btn-info btn-bold pull-right" title="Xóa tòa nhà">
                                            <i class="fa fa-trash-o bigger-110" aria-hidden="true"></i>
                                        </button>
                                        <a href="<c:url value='/admin/building-create'/>" class="btn btn-white btn-info btn-bold pull-right" style="margin-right: 2px" title="Tạo mới tòa nhà">
                                            <i class="fa fa-plus-circle bigger-110" aria-hidden="true"></i>
                                        </a>
                                    </security:authorize>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12 col-sm-12">
                            <div class="table-responsive">

                                <table id="building-list" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Thời gian tạo</th>
                                            <th>Tên</th>
                                            <th>Địa chỉ</th>
                                            <th>Nhân viên quản lý</th>
                                            <th>Diện tích sàn</th>
                                            <th>Diện tích thuê</th>
                                            <th>Loại tòa nhà</th>
                                            <th>Giá thuê</th>
                                            <th>Trạng thái</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach var="item" items="${pageableBuildings.items}">
                                            <tr id="row-building${item.id}">
                                                <td class="center">
                                                    <label class="pos-rel">
                                                        <input type="checkbox" class="ace" value="${item.id}"/>
                                                        <span class="lbl"></span>
                                                    </label>
                                                </td>
                                                <td>${item.createDate}</td>
                                                <td>${item.name}</td>
                                                <td>${item.address}</td>
                                                <td id="col-manager-info">${item.managersInfo}</td>
                                                <td>${item.floorArea} <c:if test="${item.floorArea != null}"> m^2</c:if> </td>
                                                <td>${item.rentAreas} <c:if test="${item.rentAreas.length() > 0}"> m^2</c:if> </td>
                                                <td>${item.typeName}</td>
                                                <td>${item.rentPrice} <c:if test="${item.rentPrice != null}"> triệu</c:if> </td>
                                                <td>${item.status}</td>
                                                <td>
                                                    <a href='<c:url value='/admin/building-edit?id=${item.id}'/>' class="btn btn-xs btn-info" title="Chỉnh sửa thông tin">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </a>
                                                    <security:authorize access="hasRole('manager')">
                                                        <button class="btn btn-xs btn-success" onclick="openModalAssignmentBuilding(${item.id}, '${item.name}')" title="Giao tòa nhà cho nhân viên quản lý">
                                                            <i class="ace-icon fa fa-users bigger-120"></i>
                                                        </button>
                                                    </security:authorize>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <ul class="pagination" id="pagination"></ul>
                            </div>
                        </div>
                    </div>
                </div><!-- /.col -->
            </div><!-- /.row -->

        </div><!-- /.page-content -->
    </div>
</div><!-- /.main-content -->

<div id="assignmentBuildingModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 id="modal-title" class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-responsive" id="staffList">
                    <thead>
                        <tr>
                            <th style="text-align: center">Chọn nhân viên</th>
                            <th style="text-align: center">Thông tin nhân viên</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <img src="/img/loading.gif" style="display: none; height: 50px" id="loading_image2" alt="">
                <button type="button" class="btn btn-default" id="assign-building" >Giao tòa nhà</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
            </div>
        </div>

        <input type="hidden" id="buildingAssignedId"/>
    </div>
</div>

<img src="/img/loading.gif" style="display: none; height: 100px; position: fixed; top: 40%; left: 50%" id="loading_image1">

<script>
    function openModalAssignmentBuilding(buildingId, buildingName) {
        loadStaff(buildingId)
        $("#assignmentBuildingModal #modal-title").html("Danh sách nhân viên quản lý " + buildingName)
        $("#assignmentBuildingModal").modal();
        $("#buildingAssignedId").val(buildingId)
    }

    function loadStaff(buildingId) {
        $('#loading_image1').show();
        $.ajax({
            type: 'GET',
            url: '${getStaffsAPI}/?buildingId=' + buildingId,
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                let row = '';
                $.each(response, function (index, item){
                    row += '<tr>';
                    row += '<td class="text-center"><input type="checkbox" name="checkList" value="' + item.id + '" id="checkbox_' + item.id + '" class="check-box-element" ' + item.checked + '/></td>';
                    row += '<td class="text-center">' + item.info + '</td>';
                    row += '</tr>';
                });
                $("#staffList tbody").html(row);
                $('#loading_image1').hide();
            },
            error: function () {
                $('#loading_image1').hide();
                showAlertError("Load nhân viên thất bại")
            }
        });
    }

    $("#assign-building").click(function () {
        $('#loading_image2').show();
        let buildingId = $("#buildingAssignedId").val()
        let staffsId = $("#staffList").find("tbody input[type=checkbox]:checked").map(function () {
            return $(this).val()
        }).get()
        let data = {}
        data.id = buildingId
        data.staffsId = staffsId
        $.ajax({
            type: 'POST',
            url: '${assignBuildingAPI}',
            data: JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                let staffInfo = '';
                $.each(response, function (index, item) {
                    staffInfo += item.info + ', ';
                })
                $("#row-building" + buildingId + " #col-manager-info").html(staffInfo.substring(0, staffInfo.length - 2))
                $('#loading_image2').hide();
                $("#assignmentBuildingModal").modal('hide')
                showAlert("Bạn đã cập nhật nhân viên quản lý tòa nhà!")
            },
            error: function () {
                $('#loading_image2').hide();
                $("#assignmentBuildingModal").modal('hide')
                showAlertError("Cập nhật nhân viên quản lý tòa nhà thất bại!")
            }
        });
    })

    $("#deleteBuilding").click(function () {
        let buildingIds = $("#building-list").find("tbody input[type=checkbox]:checked").map(function () {
            return $(this).val();
        }).get();
        if (buildingIds.length === 0) {
            showAlertError("Bạn chưa chọn tòa nhà nào")
        } else {
            showAlertBeforeDelete(function () {
                $('#loading_image1').show();
                $.ajax({
                    type: 'DELETE',
                    url: '${deleteBuildingAPI}',
                    data: JSON.stringify(buildingIds),
                    dataType: 'json',  // định dạng mong muốn từ server (nếu server trả void thì phải để 'text')
                    contentType: 'application/json', // định dạng gửi cho server
                    success: function () {
                        buildingIds.forEach(function (buildingId) {
                            $('#row-building' + buildingId).remove()
                        })
                        $('#loading_image1').hide();
                        showAlert("Bạn đã xóa thành công tòa nhà!")
                    },
                    error: function () {
                        $('#loading_image1').hide();
                        showAlertError("Xóa tòa nhà không thành công!")
                    }
                });
            })
        }
    })
</script>

<script>
    $(function () {
        let currentPage = ${pageableBuildings.currentPage};
        let totalPages = ${pageableBuildings.totalPages};
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
                    $('#form-find-building')[0].reset();
                    $("#form-find-building").submit()
                }
            }
        })
    })

    $("#btnReset").click(function () {
        $("#building-name").val("")
        $("#dien-tich-san").val("")
        $("#district").val("")
        $("#ward").val("")
        $("#street").val("")
        $("#level").val("")
        $("#direction").val("")
        $("#numberofbasement").val("")
        $("#rent-area-from").val("")
        $("#rent-area-to").val("")
        $("#gia-thue-tu").val("")
        $("#gia-thue-den").val("")
        if (! $('#manager-name').prop('disabled')) {
            $("#manager-name").val("")
        }
        if (! $('#manager-phone-number').prop('disabled')) {
            $("#manager-phone-number").val("")
        }
        $("#staffId").val("")
        $("#rentStatus").val("")
        $("#create-date-from").val("")
        $("#create-date-to").val("")
        $("input[name='buildingTypes']").each(function () {
            $(this).prop('checked', false)
        })


    })

</script>

</body>
</html>
