# Summary

This application is a tool for managing shared books in small library.

# Getting Started

### Frameworks and Libraries

This application is developed with [Spring][].

Also it uses:

1. Spring MVC and [Twitter Bootstrap][] (also [jQuery][] and [fancyBox][]) - for user interface.
2. [Hibernate][] and [Spring JPA][] - for data.
3. [StringTemplate][] - for creating emails.

### Tools

The following tools are needed:

1. [Maven][] - to build app.
2. [Tomcat][] - to run app.
4. [MySQL][] - to store data.

Also we've used [OpenDJ][] to store data about users.

### How To Start

Get a copy of project using [Git][]:
```bash
git clone https://github.com/grytsenko/library.git
```

Then build web application using [Maven](http://maven.apache.org/):
```bash
cd /modules/webapp
mvn clean package
```

Install and configure RDBMS ([MySQL][]) and DS ([OpenDJ][]).

At last deploy application on web server.

[Spring]: http://www.springsource.org/
[Spring JPA]: http://www.springsource.org/spring-data/jpa
[Hibernate]: http://www.hibernate.org/
[StringTemplate]: http://www.stringtemplate.org/

[Twitter Bootstrap]: http://twitter.github.io/bootstrap/
[jQuery]: http://jquery.com/
[fancyBox]: http://fancyapps.com/fancybox/

[Maven]: http://maven.apache.org/
[Tomcat]: http://tomcat.apache.org/
[Git]: http://git-scm.com/

[MySQL]: http://www.mysql.com/
[OpenDJ]: http://opendj.forgerock.org/
