# CashCards Java Spring Boot REST API with Gradle

#### Notes:

- Even though `@Autowired` is a form of Spring dependency injection, it’s best used only in tests.

- The `@Configuratio`n annotation tells Spring to use this class to configure Spring and Spring Boot itself. Any Beans specified in this class will now be available to Spring's Auto Configuration engine.

- Apart from being useful feature for the end user, pagination and sorting accomplish two important things:

  - ensure that the data received from the server is in a predictable and understandable order
  - protect the client and server from being overwhelmed by a large amount of data (the page size puts a cap on the amount of data that can be returned in a single response)

- When we add the Spring Security dependency to our application, security gets enabled by default. If we don't specify how authentication and authorization should be performed, Spring Security completely locks down our API. Better safe than sorry.

- It's important to understand that any information returned from our application might be useful to a bad actor attempting to violate our application's security. For example: knowledge about actions that causes our application to crash -- a 500 INTERNAL_SERVER_ERROR. In order to avoid "leaking" information about our application, Spring Security has configured Spring Web to return a generic 403 FORBIDDEN in most error conditions. If almost everything results in a 403 FORBIDDEN response then an attacker doesn't really know what's going on.
