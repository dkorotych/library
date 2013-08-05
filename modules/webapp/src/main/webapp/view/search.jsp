<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title><fmt:message key="app.title" /></title>

<link href="<c:url value="/favicon.ico" />" rel="shortcut icon">
<link href="<c:url value="/libs/bootstrap/css/bootstrap.css" />"
    rel="stylesheet" media="screen" />
<link href="<c:url value="/libs/library/css/common.css" />" rel="stylesheet" />
</head>

<body>
    <%@include file="components/navbar.jsp"%>

    <div class="container">
        <c:set var="pagePath" value="/search" />
        <%@include file="components/pages.jsp"%>

        <c:set var="books" value="${searchResults.content}" />
        <div class="row-fluid">
            <c:if test="${books.isEmpty()}">
                <!-- Message about empty list of books. -->
                <p class="text-info">
                    <fmt:message key="warning.books.not.found" />
                </p>
            </c:if>

            <ul class="thumbnails">
                <c:forEach items="${books}" var="book" varStatus="loop">
                    <li class="span4">
                        <%@include file="components/shared-book-thumbnail.jsp"%>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <script src="<c:url value="/libs/jquery/jquery.js" />"></script>
    <script src="<c:url value="/libs/bootstrap/js/bootstrap.js" />"></script>
</body>

</html>