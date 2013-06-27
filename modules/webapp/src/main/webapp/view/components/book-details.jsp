<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Details. -->
<div class="media">
    <div class="pull-left">
        <img class="media-object" src="${bookDetails.imageUrl}"
            onerror="$(this).hide();" />
    </div>
    <div class="media-body">
        <h4>${bookDetails.title}</h4>
        <p>${bookDetails.authors}</p>

        <br />

        <ul class="unstyled">
            <li>
                <strong>
                    <fmt:message key="book.details.publisher" />
                </strong>
                ${bookDetails.publisher}
            </li>
            <li>
                <strong>
                    <fmt:message key="book.details.year" />
                </strong>
                ${bookDetails.year}
            </li>
            <li>
                <strong>
                    <fmt:message key="book.details.language" />
                </strong>
                ${bookDetails.language}
            </li>
            <li>
                <strong>
                    <fmt:message key="book.details.pages" />
                </strong>
                ${bookDetails.pages}
            </li>
            <li>
                <strong>
                    <fmt:message key="book.details.isbn" />
                </strong>
                ${bookDetails.isbn}
            </li>
        </ul>
    </div>
</div>
