<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Book -->
<div class="thumbnail">
    <c:url var="bookDetailsUrl" value="/shared-book">
        <c:param name="bookId" value="${book.id}" />
    </c:url>

    <!-- Cover -->
    <div class="text-center">
        <a href="${bookDetailsUrl}">
            <img src="${book.details.imageUrl}" onerror="$(this).hide();" />
        </a>
    </div>

    <div class="caption">
        <!-- Status -->
        <div class="pull-right">
            <c:if test="${book.available}">
                <span class="label label-success">
                    <fmt:message key="book.status.available" />
                </span>
            </c:if>
            <c:if test="${book.reserved}">
                <span class="label label-warning">
                    <fmt:message key="book.status.reserved" />
                </span>
            </c:if>
            <c:if test="${book.borrowed}">
                <span class="label label-warning">
                    <fmt:message key="book.status.borrowed" />
                </span>
            </c:if>
        </div>

        <br />

        <!-- Details -->
        <h4>${book.details.title}</h4>
        <p>${book.details.authors}</p>

        <p>
            <a href="${bookDetailsUrl}" class="btn btn-info">
                <fmt:message key="book.action.view" />
            </a>
        </p>
    </div>
</div>
