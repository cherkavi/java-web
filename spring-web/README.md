# J1 - Smava recruitment tasks for Java back-end developer

This repository provides a multi-layer Maven project built on Spring framework version 4.1.0.RELEASE.

To build the project execute maven "clean package" commands from the project root.

To run the project in an embedded Tomcat instance execute maven "tomcat7:run" command from the **recrt-rest** package 
base.

## Project description

The project provides a RESTful API to manage the *de.smava.recrt.model.AppUser* and *de.smava.recrt.model.BankAccount* 
entities. 

The back-end is an embedded H2 Database. Project data is initialized upon application start-up as per the 
*sql/init.sql* script at **recrt-peristence** package.

The RESTful API provides the following interfaces:

* Login

```
POST http://localhost:8080/rest/login

Request:
========
Headers:
Accept: application/json
Content-Type: application/json

Request Body:
{ "username":"user1", "password":"1111" }

Login Success Response:
=======================
Code: 200

Response Body:
{"username":"user1","loggedIn":true}

Login Failed Response:
=======================
Code: 200

Response Body:
{"loggedIn":false}

Error Response:
===============
Code: 400

```

* Logout

```
DELETE http://localhost:8080/rest/login

Logout Success Response:
=======================
Code: 200

Error Response:
===============
Code: 400

```

* Get users

```
GET http://localhost:8080/rest/users




```

* Get accounts

```
GET http://localhost:8080/rest/accounts




```

* Create account

```
POST http://localhost:8080/rest/accounts




```

## Procedure

You will be asked to execute some of the tasks presented below. Each task suggests an estimated time for completion 
( **ETC** ).

First, examine the code to understand the project structure. For a proficient Java+Spring developer this phase
should not require more than 20-30 minutes.

Then git clone the project to your development machine.

To execute a task:

1. Branch off the master using the following naming convention.

    [your initials]/task-[task number]

    For example if your name is John Doe, to execute task 1, your branch name should be **JD/task-1**

1. Once you complete the implementation, commit with an appropriate commit message that describes your work
and push your changes to the remote repository.

1. Create a pull request and assign the administrator, who provided you with the tasks, for code review.

## Tasks

1. By using the available implementation and minimum implementation effort, introduce a third layer between the
web and persistence layers for the **create bank account API** so that the create account function is load balanced 
via the use of JMS. 

    ETC: 15 mins.

1. With minimum effort, introduce caching for the 
**de.smava.recrt.service.AppUserRoleService.getByAppUser** service.

    ETC: 15 mins.

1. Provide a "GET http://localhost:8080/rest/users/[userName]/accounts" REST endpoint in the 
**de.smava.recrt.rest.AppUserApi** implementation that gets the bank accounts that belong to a specific user. 
The method should only be callable by a user who has the **ROLE_ADMIN** role.

    ETC: 30 mins.

1. Without adding any new libraries to the project, complete the **recrt-persistence** package unit tests

    ETC: 30 mins.
    
1. Without adding any new libraries to the project, complete the **recrt-service** package unit tests

    ETC: 30 mins.

1. Other tasks?...
