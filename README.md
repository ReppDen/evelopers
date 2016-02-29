# evelopers
This app is an implementation one of the test tasks that is have.

Date of implementation: Feb 2016

Description:
- [Russian](Russian.txt)
- English (coming soon)

What you can find in this app:
+  Spiring boot
+  A little java concurrency
+  Java 8
+  RESTful application
+  Logback logging

What you will not find in this app (yet):
+  unit test
+  there is no UI... at all ... just REST API with JSON output

How to run:
* build project using maven command `mvn clean install`.
* deploy `target/evelopers-0.0.1-SNAPSHOT.war` to application server. E.g Tomcat
* open deployed application in browser
* follow the instructions on the home page

REST end points are:
+ Post new task for execution
POST /request/ payload="%MONTH%" Content-Type: application/json
+ get the results of a task by key
GET /request/{key}
