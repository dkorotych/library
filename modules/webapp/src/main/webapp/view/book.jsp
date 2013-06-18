<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
            <!-- Information about book. -->
            <div class="media">
                <div class="pull-left">
                    <img class="media-object" src="${book.details.imageUrl}"
                        onerror="$(this).hide();" />
                </div>
                <div class="media-body">
                    <h4>${book.details.title}</h4>
                    <p>${book.details.authors}</p>

                    <br />

                    <!-- Details. -->
                    <ul class="unstyled">
                        <li>
                            <strong>
                                <fmt:message key="book.details.publisher" />
                            </strong>
                            ${book.details.publisher}
                        </li>
                        <li>
                            <strong>
                                <fmt:message key="book.details.year" />
                            </strong>
                            ${book.details.year}
                        </li>
                        <li>
                            <strong>
                                <fmt:message key="book.details.language" />
                            </strong>
                            ${book.details.language}
                        </li>
                        <li>
                            <strong>
                                <fmt:message key="book.details.pages" />
                            </strong>
                            ${book.details.pages}
                        </li>
                        <li>
                            <strong>
                                <fmt:message key="book.details.isbn" />
                            </strong>
                            ${book.details.isbn}
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <hr />

        <!-- Status -->
        <div class="row-fluid">
            <c:if test="${not empty bookNotUpdated}">
                <!-- Book was not updated. -->
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <fmt:message key="warning.book.not.updated" />
                </div>
            </c:if>
            <c:if test="${not empty userNotNotified}">
                <!-- User was not notified. -->
                <div class="alert alert-warning">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <fmt:message key="warning.user.not.notified" />
                </div>
            </c:if>

            <form:form method="POST">
                <!-- Status Message -->
                <c:choose>
                    <c:when test="${book.available}">
                        <p>
                            <fmt:message key="book.message.available" />
                        </p>
                    </c:when>
                    <c:when test="${book.reserved}">
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
                    <c:when test="${book.borrowed}">
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
                    <c:if
                        test="${book.isManagedBy(user) and (book.reserved or book.borrowed)}">
                        <button name="remind" type="submit" class="btn btn-info">
                            <i class="icon-bell"></i>
                            <fmt:message key="book.action.remind" />
                        </button>
                    </c:if>

                    <c:if test="${book.canBeReserved()}">
                        <button name="reserve" type="submit"
                            class="btn btn-primary">
                            <fmt:message key="book.action.reserve" />
                        </button>
                    </c:if>

                    <c:if test="${book.canBeReleasedBy(user)}">
                        <button name="release" type="submit"
                            class="btn btn-warning">
                            <fmt:message key="book.action.release" />
                        </button>
                    </c:if>
                    <c:if test="${book.canBeTakenOutBy(user)}">
                        <button name="takeOut" type="submit"
                            class="btn btn-warning">
                            <fmt:message key="book.action.takeout" />
                        </button>
                    </c:if>
                    <c:if test="${book.canBeTakenBackBy(user)}">
                        <button name="takeBack" type="submit"
                            class="btn btn-warning">
                            <fmt:message key="book.action.takeback" />
                        </button>
                    </c:if>
                </p>
            </form:form>
        </div>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>
