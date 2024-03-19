# CashCards Java Spring Boot REST API with Gradle

#### Notes:

- Even though `@Autowired` is a form of Spring dependency injection, it’s best used only in tests.

- The `@Configuratio`n annotation tells Spring to use this class to configure Spring and Spring Boot itself. Any Beans specified in this class will now be available to Spring's Auto Configuration engine.

- Apart from being useful feature for the end user, pagination and sorting accomplish two important things:

  - ensure that the data received from the server is in a predictable and understandable order
  - protect the client and server from being overwhelmed by a large amount of data (the page size puts a cap on the amount of data that can be returned in a single response)

- When we add the Spring Security dependency to our application, security gets enabled by default. If we don't specify how authentication and authorization should be performed, Spring Security completely locks down our API. Better safe than sorry.

- It's important to understand that any information returned from our application might be useful to a bad actor attempting to violate our application's security. For example: knowledge about actions that causes our application to crash -- a 500 INTERNAL_SERVER_ERROR. In order to avoid "leaking" information about our application, Spring Security has configured Spring Web to return a generic 403 FORBIDDEN in most error conditions. If almost everything results in a 403 FORBIDDEN response then an attacker doesn't really know what's going on.

- Both `PUT` and `PATCH` can be used for updating, but they work in different ways. Essentially, `PUT` means “create or replace the complete record”, whereas `PATCH` means “update only some fields of the existing record” - in other words, a partial update. Partial updates free the client from having to load the entire record and then transmit the entire record back to the server. If the record is large enough, this can have a non-trivial impact on performance.

- The HTTP standard doesn't specify whether the `POST` or `PUT` verb is preferred for a Create operation! However, if you need the server to return the URI of the created resource (or the data you use to construct the URI), then you must use `POST`. Alternatively, when the resource URI is known at creation time, you can use `PUT`.

- When using `PUT` for updating, there are generally two approaches to what should be returned:

  - Return `201 CREATED` (if you created the object), or `200 OK` (if you replaced an existing object). In this case, it's recommended to return the object in the response body. This is useful if data was added to the object by the server (for example, if the server records the creation date).

  - Return `204 NO CONTENT`, and an empty response body. The rationale in this case is that since a PUT simply places an object at the URI in the request, the client doesn't need any information back - it knows that the object in the request has been saved, verbatim, on the server.
