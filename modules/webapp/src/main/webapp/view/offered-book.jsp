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
            <c:set var="bookDetails" value="${book.details}" />
            <%@include file="components/book-details.jsp"%>
        </div>

        <c:if test="${not book.hasVoteFrom(user)}">
            <hr />

            <!-- Notifications -->
            <div class="row-fluid">
                <c:if test="${not empty bookNotUpdated}">
                    <!-- Book was not updated. -->
                    <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <fmt:message key="warning.book.not.updated" />
                    </div>
                </c:if>
            </div>

            <!-- Status -->
            <p class="text-info">
                <fmt:message key="book.message.vote" />
            </p>

            <!-- Actions -->
            <div class="row-fluid">
                <form:form method="POST">
                    <p>
                        <button name="addVote" type="submit"
                            class="btn btn-success">
                            <fmt:message key="book.action.vote" />
                        </button>
                    </p>
                </form:form>
            </div>
        </c:if>

        <c:if test="${book.votesNum > 0}">
            <hr />

            <div class="row-fluid">
                <c:forEach items="${book.votedUsers}" var="votedUser"
                    varStatus="loop">
                    <c:choose>
                        <c:when test="${user.identicalTo(votedUser)}">
                            <c:set var="badgeStyle" value="badge badge-success" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="badgeStyle" value="badge badge-info" />
                        </c:otherwise>
                    </c:choose>
                    <span class="${badgeStyle}">${votedUser.readableName}</span>
                </c:forEach>
            </div>
        </c:if>

    </div>

    <script src="<c:url value="/assets/js/jquery.js" />"></script>
    <script src="<c:url value="/assets/js/bootstrap.js" />"></script>
</body>

</html>
