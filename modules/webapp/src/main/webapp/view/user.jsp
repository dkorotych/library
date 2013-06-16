<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title><fmt:message key="app.title" /></title>

<link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet"
    media="screen" />
<link href="<c:url value="/assets/css/library-common.css" />" rel="stylesheet" />
</head>

<body>
    <%@include file="components/navbar.jsp"%>

    <div class="container">
        <div class="row-fluid">
            <!-- Details. -->
            <ul class="unstyled">
                <li>
                    <strong>
                        <fmt:message key="user.details.firstname" />
                    </strong>
                    ${user.firstname}
                </li>
                <li>
                    <strong>
                        <fmt:message key="user.details.lastname" />
                    </strong>
                    ${user.lastname}
                </li>
                <li>
                    <strong>
                        <fmt:message key="user.details.mail" />
                    </strong>
                    ${user.mail}
                </li>
            </ul>
        </div>

        <c:if test="${not empty reservedBooks}">
            <hr />

            <!-- Reserved Books. -->
            <div class="row-fluid">
                <h4 class="text-info">
                    <fmt:message key="user.books.reserved" />
                </h4>
                <ul class="unstyled">
                    <c:forEach items="${reservedBooks}" var="book"
                        varStatus="loop">
                        <c:url var="bookDetailsUrl" value="/book">
                            <c:param name="bookId" value="${book.id}" />
                        </c:url>
                        <li>
                            <a href="${bookDetailsUrl}">${book.details.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <c:if test="${not empty borrowedBooks}">
            <hr />

            <!-- Borrowed Books. -->
            <div class="row-fluid">
                <h4 class="text-info">
                    <fmt:message key="user.books.borrowed" />
                </h4>
                <ul class="unstyled">
                    <c:forEach items="${borrowedBooks}" var="book"
                        varStatus="loop">
                        <c:url var="bookDetailsUrl" value="/book">
                            <c:param name="bookId" value="${book.id}" />
                        </c:url>
                        <li>
                            <a href="${bookDetailsUrl}">${book.details.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>
