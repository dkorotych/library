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

        <c:if test="${not empty relatedBooks}">
            <hr />

            <!-- Related Books. -->
            <div class="row-fluid">
                <table class="table">
                    <thead>
                        <tr>
                            <th>
                                <fmt:message key="user.book.title" />
                            </th>
                            <th>
                                <fmt:message key="user.book.status" />
                            </th>
                            <th>
                                <fmt:message key="user.book.since" />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${relatedBooks}" var="book"
                            varStatus="loop">
                            <tr>
                                <c:url var="bookDetailsUrl" value="/book">
                                    <c:param name="bookId" value="${book.id}" />
                                </c:url>
                                <td>
                                    <a href="${bookDetailsUrl}">${book.details.title}</a>
                                </td>
                                <c:choose>
                                    <c:when test="${book.reserved}">
                                        <td>
                                            <fmt:message
                                                key="book.status.reserved" />
                                        </td>
                                        <td>
                                            <fmt:formatDate type="date"
                                                dateStyle="short"
                                                value="${book.reservedSince}" />
                                        </td>
                                    </c:when>
                                    <c:when test="${book.borrowed}">
                                        <td>
                                            <fmt:message
                                                key="book.status.borrowed" />
                                        </td>
                                        <td>
                                            <fmt:formatDate type="date"
                                                dateStyle="short"
                                                value="${book.borrowedSince}" />
                                        </td>
                                    </c:when>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>
