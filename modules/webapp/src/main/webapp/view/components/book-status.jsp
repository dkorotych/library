<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Book Status -->
<c:if test="${book.available}">
    <span class="label label-success">
        <fmt:message key="book.status.available" />
    </span>
</c:if>
<c:if test="${book.reserved}">
    <span class="label label-info">
        <fmt:message key="book.status.reserved" />
    </span>
</c:if>
<c:if test="${book.borrowed}">
    <span class="label label-info">
        <fmt:message key="book.status.borrowed" />
    </span>
</c:if>

