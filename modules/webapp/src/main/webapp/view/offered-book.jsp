<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8">

<title><fmt:message key="app.title" /></title>

<link href="<c:url value="/libs/bootstrap/css/bootstrap.css" />"
    rel="stylesheet" media="screen" />
<link href="<c:url value="/libs/fancybox/css/jquery.fancybox.css" />"
    rel="stylesheet" media="screen" />
<link href="<c:url value="/libs/library/css/common.css" />" rel="stylesheet" />
</head>

<body>
    <%@include file="components/navbar.jsp"%>

    <div class="container">
        <div class="row-fluid">
            <c:set var="bookDetails" value="${book.details}" />
            <%@include file="components/book-details.jsp"%>
        </div>

        <hr />

        <c:if test="${not empty bookNotUpdated}">
            <!-- Notifications -->
            <div class="row-fluid">
                <!-- Book was not updated. -->
                <div class="alert alert-error">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <fmt:message key="warning.book.not.updated" />
                </div>
            </div>
        </c:if>

        <!-- Actions -->
        <div class="row-fluid">
            <form:form method="POST">
                <!-- Manage -->
                <c:if test="${currentUser.manager}">
                    <div class="pull-right">
                        <p>
                            <button name="share" type="submit"
                                class="btn btn-success">
                                <fmt:message key="book.action.share" />
                            </button>
                            <button name="remove" type="submit"
                                class="btn btn-danger">
                                <fmt:message key="book.action.remove" />
                            </button>
                        </p>
                    </div>
                </c:if>

                <!-- Vote -->
                <c:if test="${not book.hasVoter(currentUser)}">
                    <p>
                        <button name="vote" type="submit"
                            class="btn btn-success">
                            <fmt:message key="book.action.vote" />
                        </button>
                    </p>
                </c:if>
            </form:form>
        </div>

        <c:if test="${book.votersNum > 0}">
            <div class="row-fluid">
                <p>
                    <span class="text-info">
                        <fmt:message key="book.message.voters" />
                    </span>
                    <c:forEach items="${book.voters}" var="voter"
                        varStatus="loop">
                        <c:choose>
                            <c:when test="${currentUser.equals(voter)}">
                                <c:set var="badgeStyle" value="badge badge-info" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="badgeStyle" value="badge" />
                            </c:otherwise>
                        </c:choose>
                        <span class="${badgeStyle}">${voter.readableName}</span>
                    </c:forEach>
                </p>
            </div>
        </c:if>
    </div>

    <script src="<c:url value="/libs/jquery/jquery.js" />"></script>
    <script src="<c:url value="/libs/bootstrap/js/bootstrap.js" />"></script>
    <script src="<c:url value="/libs/fancybox/js/jquery.fancybox.js" />"></script>
    <script src="<c:url value="/libs/library/js/fancybox.init.js" />"></script>
</body>

</html>
