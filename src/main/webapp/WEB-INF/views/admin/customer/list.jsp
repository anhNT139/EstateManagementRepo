<%@ taglib prefix="select" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/common/taglib.jsp" %>

<c:url var="searchCustomerURL" value="/admin/customer-list"/>
<c:url var="assignCustomerAPI" value="/api/customer/assignment"/>
<c:url var="getStaffsAPI" value="/api/user/staffs"/>
<c:url var="deleteCustomerAPI" value="/api/customer"/>
<html>
<head>
  <title>Danh sách khách hàng</title>
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
        <li class="active">Danh sách khách hàng</li>
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

              <div class="widget-box">
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
                    <form:form modelAttribute="modelSearch" action="${searchCustomerURL}" class="form-horizontal" id="form-find-customer" method="GET">

                      <div class="form-group">
                        <div class="col-xs-12 col-sm-4">
                          <label for="fullName">Tên khách hàng</label>
                          <input type="text" id="fullName" name="fullName" class="form-control" value="${modelSearch.fullName}"/>
                        </div>

                        <div class="col-xs-12 col-sm-4">
                          <label for="phone">Số điện thoại</label>
                          <input type="number" id="phone" name="phone" class="form-control" value="${modelSearch.phone}"/>
                        </div>

                        <div class="col-xs-12 col-sm-4">
                          <label for="email">Email</label>
                          <input type="email" id="email" name="email" class="form-control" value="${modelSearch.email}"/>
                        </div>
                      </div>

                      <div class="form-group">
                        <div class="col-xs-12 col-sm-3">
                          <label for="status">Trạng thái khách hàng</label>
                          <br>
                          <form:select path="status">
                            <form:option value="" label="--- Tất cả ---"/>
                            <form:options items="${statusMap}" />
                          </form:select>
                        </div>

                        <div class="col-xs-12 col-sm-3">
                          <label for="staffId">Chọn nhân viên phụ trách</label>
                          <br>
                          <security:authorize access="hasRole('staff')">
                            <input style="width: min-content" class="form-control" value="<security:authentication property="principal.fullName"/>" disabled/>
                          </security:authorize>

                          <security:authorize access="hasRole('manager')">
                            <form:select path="staffId">
                              <form:option value="" label="--- Tất cả ---"/>
                              <form:options items="${staffsMap}" />
                            </form:select>
                          </security:authorize>
                        </div>

                        <div class="col-xs-12 col-sm-3">
                          <label for="create-date-from">Thời gian tạo từ</label>
                          <input style="display: block" type="date" id="create-date-from" name="createDateFrom" value="${modelSearch.createDateFrom}">
                        </div>
                        <div class="col-xs-12 col-sm-3">
                          <label for="create-date-to">Thời gian tạo đến</label>
                          <input style="display: block" type="date" id="create-date-to" name="createDateTo" value="${modelSearch.createDateTo}">
                        </div>
                      </div>

                      <input id="current-page" type="hidden" name="page" value="1">
                      <input id="limit-item" type="hidden" name="limit" value="5">

                      <button type="button" class="btn btn-sm btn-grey" id="btnReset">
                        <i class="fa fa-undo" aria-hidden="true"></i>
                        Làm mới
                      </button>

                      <button type="submit" class="btn btn-sm btn-success" form="form-find-customer">
                        Tìm kiếm
                        <i class="fa fa-arrow-right" aria-hidden="true"></i>
                      </button>

                    </form:form>
                  </div>
                </div>
              </div>

              <div style="margin-top:10px">
                <security:authorize access="hasRole('manager')">
                  <button id="deleteCustomer" class="btn btn-white btn-info btn-bold pull-right" title="Xóa khách hàng" >
                    <i class="fa fa-trash" aria-hidden="true"></i>
                  </button>
                  <a href="<c:url value='/admin/customer-create'/>" class="btn btn-white btn-info btn-bold pull-right" title="Thêm mới khách hàng">
                    <i class="fa fa-plus-circle" aria-hidden="true"></i>
                  </a>
                </security:authorize>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-xs-12 col-sm-12">
              <div class="table-responsive">
                <table id="customers-table" class="table table-striped table-bordered table-hover">
                  <thead>
                    <tr>
                      <th></th>
                      <th>Thời gian tạo</th>
                      <th>Tên khách hàng</th>
                      <th>Số điện thoại KH</th>
                      <th>Nhân viên quản lý</th>
                      <th>Ghi chú</th>
                      <th>Trạng thái</th>
                      <th>Thao tác</th>
                    </tr>
                  </thead>

                  <tbody>
                    <c:forEach var="item" items="${pageableCustomers.items}">
                      <tr id="row-customer${item.id}">
                        <td class="center" >
                          <label class="pos-rel">
                            <input type="checkbox" class="ace" value="${item.id}"/>
                            <span class="lbl"></span>
                          </label>
                        </td>
                        <td>${item.createDate}</td>
                        <td>${item.fullName}</td>
                        <td>${item.phone}</td>
                        <td id="col-manager-info">${item.managersInfo}</td>
                        <td>${item.note}</td>
                        <td id="col-status-name">${item.statusName}
                        <td>
                          <a href='<c:url value='/admin/customer-edit?id=${item.id}'/>' class="btn btn-xs btn-info" title="Chỉnh sửa thông tin">
                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                          </a>
                          <security:authorize access="hasRole('manager')">
                            <button class="btn btn-xs btn-success" onclick="openModalAssignment(${item.id}, '${item.fullName}')" title="Nhân viên hỗ trợ khách hàng">
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
          <!-- PAGE CONTENT ENDS -->
        </div><!-- /.col -->
      </div><!-- /.row -->

    </div><!-- /.page-content -->
  </div>
</div><!-- /.main-content -->

<div id="assignmentModal" class="modal fade" role="dialog">
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
        <button type="button" class="btn btn-default" id="assign" >Xác nhận</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
      </div>
    </div>

    <input type="hidden" id="customerAssignedId"/>
  </div>
</div>

<img src="/img/loading.gif" style="display: none; height: 100px; position: fixed; top: 40%; left: 50%" id="loading_image1">

<script>
  function openModalAssignment(customerId, customerName) {
    loadStaff(customerId)
    $("#assignmentModal #modal-title").html("Danh sách nhân viên hỗ trợ " + customerName)
    $("#assignmentModal").modal();
    $("#customerAssignedId").val(customerId)
  }

  function loadStaff(customerId) {
    $('#loading_image1').show();
    $.ajax({
      type: 'GET',
      url: '${getStaffsAPI}?customerId=' + customerId,
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

  $("#assign").click(function () {
    $('#loading_image2').show();
    let customerId = $("#customerAssignedId").val()
    let staffsId = $("#staffList").find("tbody input[type=checkbox]:checked").map(function () {
      return $(this).val()
    }).get()
    let data = {}
    data.id = customerId
    data.staffsId = staffsId
    $.ajax({
      type: 'POST',
      url: '${assignCustomerAPI}',
      data: JSON.stringify(data),
      contentType: 'application/json',
      dataType: 'json',
      success: function (response) {
        let staffInfo = ''
        $.each(response, function (index, item) {
          staffInfo += item.info + ', ';
        })
        $("#row-customer" + customerId + " #col-manager-info").html(staffInfo.substring(0, staffInfo.length - 2))
        $("#assignmentModal").modal('hide')
        $('#loading_image2').hide();
        showAlert("Bạn đã cập nhật nhân viên hỗ trợ khách hàng!")
      },
      error: function () {
        $("#assignmentModal").modal('hide')
        $('#loading_image2').hide();
        showAlertError("Cập nhật nhân viên hỗ trợ khách hàng thất bại!")
      }
    });
  })

  $("#deleteCustomer").click(function () {
    let customersId = $("#customers-table").find("tbody input[type=checkbox]:checked").map(function () {
      return $(this).val();
    }).get();
    if (customersId.length === 0) {
      showAlertError("Bạn chưa chọn khách hàng nào!")
    } else {
      showAlertBeforeDelete(function () {
        $('#loading_image1').show();
        $.ajax({
          type: 'DELETE',
          url: '${deleteCustomerAPI}',
          data: JSON.stringify(customersId),
          dataType: 'text',
          contentType: 'application/json',
          success: function () {
            customersId.forEach(function (customerId) {
              $('#row-customer' + customerId).remove()
            })
            $('#loading_image1').hide();
            showAlert("Bạn đã xóa thành công khách hàng!")
          },
          error: function () {
            $('#loading_image1').hide();
            showAlertError("Xóa khách hàng không thành công")
          }
        });
      })
    }
  })

</script>

<script>
  $(function () {
    let currentPage = ${pageableCustomers.currentPage};
    let totalPages = ${pageableCustomers.totalPages};
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
          $('#form-find-customer')[0].reset();
          $("#form-find-customer").submit()
        }
      }
    })
  })

  $("#btnReset").click(function () {
    $("#fullName").val("")
    $("#phone").val("")
    $("#email").val("")
    $("#status").val("")
    $("#staffId").val("")
    $("#create-date-from").val("")
    $("#create-date-to").val("")
  })
</script>

</body>
</html>
