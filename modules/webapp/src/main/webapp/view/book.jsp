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
            <!-- Information about book. -->
            <h3>${book.info.title}</h3>
            <p>${book.info.authors}</p>
        </div>

        <hr />

        <!-- Details. -->
        <div class="row-fluid">
            <h4 class="text-info">
                <fmt:message key="book.details" />
            </h4>
            <ul class="unstyled">
                <li>
                    <strong>
                        <fmt:message key="book.details.publisher" />
                    </strong>
                    ${book.info.publisher}
                </li>
                <li>
                    <strong>
                        <fmt:message key="book.details.year" />
                    </strong>
                    ${book.info.year}
                </li>
                <li>
                    <strong>
                        <fmt:message key="book.details.language" />
                    </strong>
                    ${book.info.language}
                </li>
                <li>
                    <strong>
                        <fmt:message key="book.details.pages" />
                    </strong>
                    ${book.info.pages}
                </li>
                <li>
                    <strong>
                        <fmt:message key="book.details.isbn" />
                    </strong>
                    ${book.info.isbn}
                </li>
            </ul>
        </div>

        <hr />

        <!-- Status -->
        <div class="row-fluid">
            <h4 class="text-info">
                <fmt:message key="book.status" />
            </h4>
            <form:form method="POST">
                <c:set var="userIsManager" value="${user.role eq 'MANAGER'}" />

                <c:set var="bookIsAvailable"
                    value="${book.status eq 'AVAILABLE'}" />
                <c:set var="bookIsReserved" value="${book.status eq 'RESERVED'}" />
                <c:set var="bookIsReservedByThisUser"
                    value="${bookIsReserved and user.id.equals(book.reservedBy.id)}" />
                <c:set var="bookIsBorrowed" value="${book.status eq 'BORROWED'}" />

                <!-- Status Message -->
                <c:choose>
                    <c:when test="${bookIsAvailable}">
                        <p>
                            <fmt:message key="book.message.available" />
                        </p>
                    </c:when>
                    <c:when test="${bookIsReserved}">
                        <p>
                            <fmt:message key="book.message.reserved">
                                <fmt:param value="${book.reservedBy.username}" />
                                <fmt:param>
                                    <fmt:formatDate type="date"
                                        dateStyle="short"
                                        value="${book.reservedSince}" />
                                </fmt:param>
                            </fmt:message>
                        </p>
                    </c:when>
                    <c:when test="${bookIsBorrowed}">
                        <p>
                            <fmt:message key="book.message.borrowed">
                                <fmt:param value="${book.borrowedBy.username}" />
                                <fmt:param>
                                    <fmt:formatDate type="date"
                                        dateStyle="short"
                                        value="${book.borrowedSince}" />
                                </fmt:param>
                            </fmt:message>
                        </p>
                    </c:when>
                </c:choose>

                <!-- Actions -->
                <p>
                    <c:if test="${bookIsAvailable}">
                        <button name="reserve" type="submit"
                            class="btn btn-primary">
                            <fmt:message key="book.action.reserve" />
                        </button>
                    </c:if>
                    <c:if
                        test="${bookIsReserved and (bookIsReservedByThisUser or userIsManager)}">
                        <button name="release" type="submit"
                            class="btn btn-warning">
                            <fmt:message key="book.action.release" />
                        </button>
                    </c:if>

                    <c:if test="${userIsManager}">
                        <c:if test="${bookIsReserved}">
                            <button name="takeOut" type="submit"
                                class="btn btn-primary">
                                <fmt:message key="book.action.takeout" />
                            </button>
                        </c:if>
                        <c:if test="${bookIsBorrowed}">
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
