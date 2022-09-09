<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Đăng nhập</title>
</head>
<body>
	<div class="container">
		<!-- <h1 class="form-heading">login Form</h1> -->
		<div class="login-form">
			<div class="main-div">
				<c:choose>
					<c:when test="${param.incorrectAccount != null}">
						<div class="alert alert-danger">
							Tài khoản hoặc mật khẩu không chính xác
						</div>
					</c:when>

					<c:when test="${param.accountIsLocked != null}">
						<div class="alert alert-danger">
							Tài khoản đã bị khóa
						</div>
					</c:when>

					<c:when test="${param.accessDenied != null}">
						<div class="alert alert-danger">
							you Not authorize
						</div>
					</c:when>

					<c:when test="${param.sessionExpired != null}">
						<div class="alert alert-danger">
							multiple concurrent logins being attempted as the same user
						</div>
					</c:when>

					<c:when test="${param.error != null}">
						<div class="alert alert-danger">
							${SPRING_SECURITY_LAST_EXCEPTION.message}
						</div>
					</c:when>
				</c:choose>

				<form action="process-login" id="formLogin" method="post">
					<div class="form-group">
						<input type="text" class="form-control" id="userName" name="j_username" placeholder="Tên đăng nhập">
					</div>

					<div class="form-group">
						<input type="password" class="form-control" id="password" name="j_password" placeholder="Mật khẩu">
					</div>
					<security:csrfInput/>
					<button type="submit" class="btn btn-primary" >Đăng nhập</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>