<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<body>
    <div class="masthead">
        <div class="pull-right">
            <i class="icon-user"></i> <strong>${user.username}</strong>
            <a href="<c:url value="j_spring_security_logout" />"
                class="btn btn-inverse">
                <fmt:message key="app.logout" />
            </a>
        </div>
        <h3 class="muted">
            <fmt:message key="app.title" />
        </h3>
    </div>
</body>

</html>