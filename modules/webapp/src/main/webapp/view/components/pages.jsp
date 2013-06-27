<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${searchResults.pagesTotal gt 1}">
    <div class="pagination pagination-centered">
        <ul>
            <c:forEach var="i" begin="1" end="${searchResults.pagesTotal}">
                <c:set var="pageNum" value="${i - 1}" />
                <c:url var="pageUrl" value="${pagePath}">
                    <c:param name="pageNum" value="${pageNum}" />
                </c:url>

                <c:set var="pageStyle" value="" />
                <c:if test="${pageNum eq searchResults.pageNum}">
                    <c:set var="pageStyle" value="active" />
                </c:if>

                <li class="${pageStyle}">
                    <a href="${pageUrl}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:if>
