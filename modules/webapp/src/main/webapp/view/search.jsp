<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title>Search</title>

<link href="<c:url value="/assets/css/bootstrap.css" />" rel="stylesheet"
    media="screen" />
<link href="<c:url value="/assets/css/library.css" />" rel="stylesheet" />
</head>

<body>
    <%@include file="navbar.jsp"%>

    <div class="container">
        <div class="row-fluid">
            <div class="pull-right">
                <!-- Filter for books. -->
                <c:url var="doFilter" value="/search/filter" />
                <form action="${doFilter}" method="POST" class="form-inline">
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

        <div class="row-fluid">
            <c:if test="${not empty lastOperationFailed}">
                <!-- Alert about failed action. -->
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <fmt:message key="book.message.error" />
                </div>
            </c:if>
        </div>

        <div class="row-fluid">
            <c:if test="${books.isEmpty()}">
                <!-- Message about empty list of books. -->
                <p class="text-info">
                    <fmt:message key="book.message.empty" />
                </p>
            </c:if>

            <!-- List of books. -->
            <c:forEach items="${books}" var="book" varStatus="loop">
                <!-- Book. -->
                <div class="row-fluid">
                    <!-- Status of book. -->
                    <div class="pull-right">
                        <c:if test="${book.status eq 'AVAILABLE'}">
                            <span class="label label-success">
                                <fmt:message key="book.status.available" />
                            </span>
                        </c:if>
                        <c:if test="${book.status eq 'RESERVED'}">
                            <span class="label label-warning">
                                <fmt:message key="book.status.reserved" />
                            </span>
                        </c:if>
                        <c:if test="${book.status eq 'BORROWED'}">
                            <span class="label label-inverse">
                                <fmt:message key="book.status.borrowed" />
                            </span>
                        </c:if>
                    </div>

                    <!-- Information about book. -->
                    <p>
                        <strong>${book.details.title}</strong>
                        <br />
                        ${book.details.authors}
                    </p>

                    <div>
                        <c:url var="detailsUrl" value="book">
                            <c:param name="bookId" value="${book.id}" />
                        </c:url>
                        <p>
                            <a href="${detailsUrl}" class="btn btn-info">
                                <fmt:message key="book.action.view" />
                            </a>
                        </p>
                    </div>
                </div>
                <hr />
            </c:forEach>
        </div>
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