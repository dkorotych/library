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
    <%@include file="navbar.jsp"%>

    <div class="container">
        <div class="row-fluid">
            <c:if test="${not empty lastOperationFailed}">
                <!-- Alert about failed action. -->
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <fmt:message key="book.message.error" />
                </div>
            </c:if>
        </div>

        <c:set var="books" value="${searchResults.content}" />

        <c:if test="${searchResults.pagesTotal gt 1}">
            <div class="pagination pagination-centered">
                <ul>
                    <c:forEach var="i" begin="1"
                        end="${searchResults.pagesTotal}">
                        <c:set var="pageNum" value="${i - 1}" />
                        <c:url var="pageUrl" value="/search">
                            <c:param name="pageNum" value="${pageNum}" />
                        </c:url>

                        <c:set var="pageIsActive" value="" />
                        <c:if test="${pageNum eq searchResults.pageNum}">
                            <c:set var="pageIsActive" value="active" />
                        </c:if>

                        <li class="${pageIsActive}">
                            <a href="${pageUrl}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <div class="row-fluid">
            <c:if test="${books.isEmpty()}">
                <!-- Message about empty list of books. -->
                <p class="text-info">
                    <fmt:message key="book.message.empty" />
                </p>
            </c:if>

            <ul class="thumbnails">
                <c:forEach items="${books}" var="book" varStatus="loop">
                    <li class="span4">
                        <%@include file="book-thumbnail.jsp"%>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>