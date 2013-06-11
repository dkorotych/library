<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title>Books</title>

<link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet"
    media="screen" />
<link href="<c:url value="/assets/css/books.css" />" rel="stylesheet"
    media="screen" />
</head>

<body>
    <%@include file="navbar.jsp"%>

    <div class="container">
        <c:if test="${not empty lastOperationFailed}">
            <!-- Alert about failed action. -->
            <div class="alert alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <fmt:message key="book.message.error" />
            </div>
        </c:if>

        <div class="row-fluid">
            <c:set var="userIsManager" value="${user.role eq 'MANAGER'}" />

            <c:set var="bookIsAvailable" value="${book.status eq 'AVAILABLE'}" />
            <c:set var="bookIsReserved" value="${book.status eq 'RESERVED'}" />
            <c:set var="bookIsReservedByThisUser"
                value="${bookIsReserved and user.id.equals(book.reservedBy.id)}" />
            <c:set var="bookIsBorrowed" value="${book.status eq 'BORROWED'}" />

            <!-- Book. -->
            <form:form method="POST">
                <!-- Information about book. -->
                <p>
                    <strong>${book.info.title}</strong>
                    <br />
                    ${book.info.authors}
                </p>

                <c:if test="${bookIsReserved}">
                    <!-- Information about user, who reserved this book. -->
                    <p>
                        <fmt:message key="book.message.reserved">
                            <fmt:param value="${book.reservedBy.username}" />
                            <fmt:param>
                                <fmt:formatDate type="date" dateStyle="short"
                                    value="${book.reservedSince}" />
                            </fmt:param>
                        </fmt:message>
                    </p>
                </c:if>

                <c:if test="${bookIsBorrowed}">
                    <!-- Information about user, who borrowed this book. -->
                    <p>
                        <fmt:message key="book.message.borrowed">
                            <fmt:param value="${book.borrowedBy.username}" />
                            <fmt:param>
                                <fmt:formatDate type="date" dateStyle="short"
                                    value="${book.borrowedSince}" />
                            </fmt:param>
                        </fmt:message>
                    </p>
                </c:if>

                <p>
                    <c:if test="${bookIsAvailable}">
                        <!-- Book is available, so it can be reserved. -->
                        <button name="reserve" type="submit"
                            class="btn btn-primary">
                            <fmt:message key="book.action.reserve" />
                        </button>
                    </c:if>
                    <c:if
                        test="${bookIsReserved and (bookIsReservedByThisUser or userIsManager)}">
                        <!-- User, who has reserved a book, or manager can release it. -->
                        <button name="release" type="submit"
                            class="btn btn-warning">
                            <fmt:message key="book.action.release" />
                        </button>
                    </c:if>

                    <c:if test="${userIsManager}">
                        <c:if test="${bookIsReserved}">
                            <!-- Manager can take out a book, which is reserved. -->
                            <button name="takeOut" type="submit"
                                class="btn btn-primary">
                                <fmt:message key="book.action.takeout" />
                            </button>
                        </c:if>
                        <c:if test="${bookIsBorrowed}">
                            <!-- Manager can take back a book, which is borrowed. -->
                            <button name="takeBack" type="submit"
                                class="btn btn-primary">
                                <fmt:message key="book.action.takeback" />
                            </button>
                        </c:if>
                    </c:if>
                </p>
            </form:form>
        </div>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>
