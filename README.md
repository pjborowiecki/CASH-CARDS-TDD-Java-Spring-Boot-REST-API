# CashCards Java Spring Boot REST API with Gradle

#### Notes:

- Even though `@Autowired` is a form of Spring dependency injection, it’s best used only in tests.

- Apart from being useful feature for the end user, pagination and sorting accomplish two important things:

  - ensure that the data received from the server is in a predictable and understandable order
  - protect the client and server from being overwhelmed by a large amount of data (the page size puts a cap on the amount of data that can be returned in a single response)
