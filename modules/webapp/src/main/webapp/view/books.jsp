<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title>Books</title>

<link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet"
    media="screen" />
</head>

<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <!-- Actions over books. -->
        <div class="row-fluid">
            <div class="pull-right">
                <!-- Filter for books. -->
                <form action="<c:url value="books/filter"/>" method="POST"
                    class="form-inline">
                    <select name="selectedFilter">
                        <option value="ALL">
                            <fmt:message key="book.filter.all" />
                        </option>
                        <option value="RELATED">
                            <fmt:message key="book.filter.related" />
                        </option>
                        <option value="AVAILABLE">
                            <fmt:message key="book.filter.available" />
                        </option>
                    </select>
                    <button name="filter" type="submit" class="btn">
                        <i class="icon-filter"></i>
                    </button>
                </form>
            </div>
        </div>

        <c:if test="${not empty lastOperationFailed}">
            <!-- Alert about failed action. -->
            <div class="alert alert-error">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <fmt:message key="book.message.error" />
            </div>
        </c:if>

        <c:if test="${books.isEmpty()}">
            <!-- Message about empty list of books. -->
            <p class="text-info">
                <fmt:message key="book.message.empty" />
            </p>
        </c:if>

        <!-- List of books. -->
        <c:forEach items="${books}" var="book" varStatus="loop">
            <div class="row-fluid">
                <c:set var="userIsManager" value="${user.role eq 'MANAGER'}" />

                <c:set var="bookIsAvailable"
                    value="${book.status eq 'AVAILABLE'}" />
                <c:set var="bookIsReserved" value="${book.status eq 'RESERVED'}" />
                <c:set var="bookIsReservedByThisUser"
                    value="${bookIsReserved and book.reservedBy.equals(user.name)}" />
                <c:set var="bookIsBorrowed" value="${book.status eq 'BORROWED'}" />

                <c:url var="bookAction" value="books">
                    <c:param name="id" value="${book.id}" />
                </c:url>

                <!-- Book. -->
                <form action="${bookAction}" method="POST">
                    <!-- Status of book. -->
                    <div class="pull-right">
                        <c:if test="${bookIsAvailable}">
                            <span class="label label-success"><fmt:message
                                    key="book.status.available" /></span>
                        </c:if>
                        <c:if test="${bookIsReserved}">
                            <span class="label label-warning"><fmt:message
                                    key="book.status.reserved" /></span>
                        </c:if>
                        <c:if test="${bookIsBorrowed}">
                            <span class="label label-inverse"><fmt:message
                                    key="book.status.borrowed" /></span>
                        </c:if>
                    </div>

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
                                <fmt:param value="${book.reservedBy}" />
                                <fmt:param>
                                    <fmt:formatDate type="date"
                                        dateStyle="short"
                                        value="${book.reservedSince}" />
                                </fmt:param>
                            </fmt:message>
                        </p>
                    </c:if>

                    <c:if test="${bookIsBorrowed}">
                        <!-- Information about user, who borrowed this book. -->
                        <p>
                            <fmt:message key="book.message.borrowed">
                                <fmt:param value="${book.borrowedBy}" />
                                <fmt:param>
                                    <fmt:formatDate type="date"
                                        dateStyle="short"
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
                </form>
                <hr>
            </div>
        </c:forEach>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
    <script>
        // Selects current filter.
        jQuery(document).ready(function() {
            jQuery('select[name="selectedFilter"]').val("${filter}");
        });
    </script>
</body>

</html>