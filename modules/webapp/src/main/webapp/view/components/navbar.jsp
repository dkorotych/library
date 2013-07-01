<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <div class="brand">
                <fmt:message key="app.title" />
            </div>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <c:url var="searchUrl" value="/search" />
                    <li>
                        <a href="${searchUrl}">
                            <i class="icon-search"></i>
                            <fmt:message key="app.page.search" />
                        </a>
                    </li>
                    <c:url var="voteUrl" value="/vote" />
                    <li>
                        <a href="${voteUrl}">
                            <i class="icon-thumbs-up"></i>
                            <fmt:message key="app.page.vote" />
                        </a>
                    </li>
                </ul>
                <div class="pull-right">
                    <ul class="nav">
                        <li>
                            <c:url var="userDetailsUrl" value="/user" />
                            <a href="${userDetailsUrl}">
                                <i class="icon-user"></i>
                                <strong>${currentUser.username}</strong>
                            </a>
                        </li>
                    </ul>

                    <c:url var="logoutUrl" value="j_spring_security_logout" />
                    <a href="${logoutUrl}" class="btn btn-inverse">
                        <fmt:message key="app.logout" />
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
