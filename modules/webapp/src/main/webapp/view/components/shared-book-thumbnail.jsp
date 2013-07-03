<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Shared Book Thumbnail -->
<div class="thumbnail">
    <c:url var="bookDetailsUrl" value="/shared-book">
        <c:param name="bookId" value="${book.id}" />
    </c:url>

    <!-- Cover -->
    <div class="text-center">
        <a href="${bookDetailsUrl}">
            <img src="${book.details.thumbnailUrl}" onerror="$(this).hide();" />
        </a>
    </div>

    <!-- Details -->
    <div class="caption">
        <div class="pull-right">
            <%@include file="book-status.jsp"%>
        </div>

        <br />

        <h4>${book.details.title}</h4>
        <p>${book.details.authors}</p>

        <p>
            <a href="${bookDetailsUrl}" class="btn btn-info">
                <fmt:message key="book.action.view" />
            </a>
        </p>
    </div>
</div>
