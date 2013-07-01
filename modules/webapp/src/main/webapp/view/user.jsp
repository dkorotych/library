<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title><fmt:message key="app.title" /></title>

<link href="<c:url value="/libs/bootstrap/css/bootstrap.css" />"
    rel="stylesheet" media="screen" />
<link href="<c:url value="/libs/library/css/common.css" />" rel="stylesheet" />
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
                    ${currentUser.firstname}
                </li>
                <li>
                    <strong>
                        <fmt:message key="user.details.lastname" />
                    </strong>
                    ${currentUser.lastname}
                </li>
                <li>
                    <strong>
                        <fmt:message key="user.details.mail" />
                    </strong>
                    ${currentUser.mail}
                </li>
            </ul>
        </div>

        <c:if test="${not usedBooks.isEmpty()}">
            <hr />

            <!-- Used Books -->
            <h4>
                <fmt:message key="book.message.used" />
            </h4>
            <div class="row-fluid">
                <table class="table">
                    <thead>
                        <tr>
                            <th class="book-title">
                                <fmt:message key="user.book.title" />
                            </th>
                            <th>
                                <fmt:message key="user.book.status" />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${usedBooks}" var="book"
                            varStatus="loop">
                            <tr>
                                <c:url var="bookDetailsUrl" value="/shared-book">
                                    <c:param name="bookId" value="${book.id}" />
                                </c:url>
                                <td>
                                    <a href="${bookDetailsUrl}">${book.details.title}</a>
                                </td>
                                <td>
                                    <%@include file="components/book-status.jsp"%>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <c:if test="${not expectedBooks.isEmpty()}">
            <hr />

            <!-- Expected Books -->
            <h4>
                <fmt:message key="book.message.expected" />
            </h4>
            <div class="row-fluid">
                <table class="table">
                    <thead>
                        <tr>
                            <th class="book-title">
                                <fmt:message key="user.book.title" />
                            </th>
                            <th>
                                <fmt:message key="user.book.status" />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${expectedBooks}" var="book"
                            varStatus="loop">
                            <tr>
                                <c:url var="bookDetailsUrl" value="/shared-book">
                                    <c:param name="bookId" value="${book.id}" />
                                </c:url>
                                <td>
                                    <a href="${bookDetailsUrl}">${book.details.title}</a>
                                </td>
                                <td>
                                    <%@include file="components/book-status.jsp"%>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <script src="<c:url value="/libs/jquery/jquery.js" />"></script>
    <script src="<c:url value="/libs/bootstrap/js/bootstrap.js" />"></script>
</body>

</html>
