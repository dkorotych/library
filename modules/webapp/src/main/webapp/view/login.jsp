<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title>Library</title>

<link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet"
    media="screen" />
<link href="<c:url value="/assets/css/login.css" />" rel="stylesheet"
    media="screen" />
</head>

<body>
    <div class="container">
        <c:if test="${not empty lastLoginFailed}">
            <!-- Last attempt to login has failed. -->
            <div class="alert alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <fmt:message key="app.login.error" />
            </div>
        </c:if>

        <!--  Form for login. -->
        <form name="loginForm" action="<c:url value="j_spring_security_check"/>"
            method="POST" class="form-signin">
            <h2 class="form-signin-heading">
                <fmt:message key="app.login.title" />
            </h2>

            <input name="username" type="text" class="input-block-level"
                placeholder="<fmt:message key="app.login.username" />" />
            <input name="password" type="password" class="input-block-level"
                placeholder="<fmt:message key="app.login.password" />" />
            <button name="submit" type="submit" class="btn btn-primary">
                <fmt:message key="app.login" />
            </button>
        </form>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>