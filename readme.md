## Summary

This application is a tool for managing shared books in small library.

## Getting Started

#### Frameworks and Libraries

This application is developed with [Spring][] and [Spring MVC][].

Also it uses:

* [Hibernate][] and [Spring JPA][] for working with database.
* [Twitter Bootstrap][] as set of components for building user interface.
* [Thymeleaf][] as view technology.
* [StringTemplate][] for working with templates of emails.

#### Tools

We use the following tools:

* [Maven][] for build automation.
* [Tomcat][] as web server.
* [MySQL][] as storage for persistent data.
* [OpenDJ][] as storage of sensitive data about users.

#### How To Start

Get a copy of project using [Git][]:
```bash
git clone https://github.com/grytsenko/library.git
```

Then build web application using [Maven](http://maven.apache.org/):
```bash
cd /modules/webapp
mvn clean package
```

Install and configure [MySQL][] and [OpenDJ][].

At last deploy application on web server.

[Spring]: http://www.springsource.org/

[Spring JPA]: http://www.springsource.org/spring-data/jpa
[Hibernate]: http://www.hibernate.org/

[Spring MVC]: http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/mvc.html
[Thymeleaf]: http://www.thymeleaf.org/
[Twitter Bootstrap]: http://twitter.github.io/bootstrap/

[StringTemplate]: http://www.stringtemplate.org/

[Maven]: http://maven.apache.org/
[Tomcat]: http://tomcat.apache.org/
[Git]: http://git-scm.com/

[MySQL]: http://www.mysql.com/
[OpenDJ]: http://opendj.forgerock.org/
