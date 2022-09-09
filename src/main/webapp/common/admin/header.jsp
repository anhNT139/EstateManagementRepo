<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<%--<%@ page import="com.kepler.security.utils.SecurityUtils" %>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div id="navbar" class="navbar navbar-default          ace-save-state">
    <div class="navbar-container ace-save-state" id="navbar-container">
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>

            <span class="icon-bar"></span>
        </button>
        <div class="navbar-header pull-left">
            <a href="<c:url value='/admin/home'/> " class="navbar-brand">
                <small>
                    <i class="fa fa-leaf"></i>
                    Trang quản trị
                </small>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        Xin chào, <security:authentication property="principal.fullName"/>
<%--                        <%=SecurityUtils.getPrincipal().getFullName()%>--%>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="/admin/profile-<security:authentication property="principal.username"/>">
                                <i class="ace-icon fa fa-user"></i>
                                Thông tin tài khoản
                            </a>
                        </li>
                        <li>
                            <a href="<c:url value="/admin/profile-password"/>">
                                <i class="ace-icon fa fa-key"></i>
                                Đổi mật khẩu
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="<c:url value='/logout'/>">
                                <i class="ace-icon fa fa-power-off"></i>
                                Thoát
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>