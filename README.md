Aconsulting BE assignment


Basic parameters:

version of java: java 11
version of spring boot: 2.7.10
dependencies: Lombok, Spring Web, Spring Data JPA, H2 Database

The application is started via the main method.

For manual testing I used POSTMAN application, where I created a separate workspace.
In this workspace, I have used individual HTTP methods such as GET, POST, PUT, DELETE.

For automated testing I created unit and integrated tests. Specifically Unit tests to test 
repository and service classes and integration tests for controller class.

For data storage I used h2 (embedded) database which I implemented/connected to in application-properties. This database 
can be accessed via the following link: http://localhost:8080/h2-console. I used the basic testing database,
so the username is "sa", password is empty and jdbc url is "jdbc:h2:mem:testdb".