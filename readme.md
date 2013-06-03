# Summary

This is a simple tool for managing books in small library. It is implemented as a web application.

# Getting Started

## What You Need

This project is written in Java, so, you'll need [JDK 7](http://www.oracle.com/technetwork/java/javase/overview/index.html) or later.

Then you'll need [MySQL](http://www.mysql.com/) to store persistent data.

For managing project we've used [Maven](http://maven.apache.org/).

As a web server we've used [Tomcat](http://tomcat.apache.org/).

As a directory service we've used [OpenDJ](http://opendj.forgerock.org/).

At last, to get a copy of this project you'll need [Git](http://git-scm.com/).

## How To

Get a copy of project using [Git](http://git-scm.com/):
```bash
git clone https://github.com/grytsenko/library.git
```

Then build web application using [Maven](http://maven.apache.org/):
```bash
cd /modules/webapp
mvn clean package
```

Install and configure persistent storage ([MySQL](http://www.mysql.com/)) and directory service ([OpenDJ](http://opendj.forgerock.org/)).

At last deploy application on web server.
