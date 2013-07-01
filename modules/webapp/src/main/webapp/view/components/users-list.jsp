<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:forEach items="${users}" var="user" varStatus="loop">
    <c:choose>
        <c:when test="${currentUser.equals(user)}">
            <c:set var="badgeStyle" value="badge badge-info" />
        </c:when>
        <c:otherwise>
            <c:set var="badgeStyle" value="badge" />
        </c:otherwise>
    </c:choose>
    <span class="${badgeStyle}">${user.readableName}</span>
</c:forEach>
