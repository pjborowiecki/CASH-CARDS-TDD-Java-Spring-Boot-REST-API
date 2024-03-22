# CashCards Java Spring Boot REST API with Gradle

#### Notes:

- Even though `@Autowired` is a form of Spring dependency injection, it’s best used only in tests.

- The `@Configuratio`n annotation tells Spring to use this class to configure Spring and Spring Boot itself. Any Beans specified in this class will now be available to Spring's Auto Configuration engine.

- Apart from being useful feature for the end user, pagination and sorting accomplish two important things:

  - ensure that the data received from the server is in a predictable and understandable order
  - protect the client and server from being overwhelmed by a large amount of data (the page size puts a cap on the amount of data that can be returned in a single response)

- When we add the Spring Security dependency to our application, security gets enabled by default. If we don't specify how authentication and authorization should be performed, Spring Security completely locks down our API. Better safe than sorry.

- It's important to understand that any information returned from our application might be useful to a bad actor attempting to violate our application's security. For example: knowledge about actions that causes our application to crash -- a 500 INTERNAL_SERVER_ERROR. In order to avoid "leaking" information about our application, Spring Security has configured Spring Web to return a generic 403 FORBIDDEN in most error conditions. If almost everything results in a 403 FORBIDDEN response then an attacker doesn't really know what's going on.

- Spring Security automatically configures your application to respond with best-practice settings for cache control and content-type fuzzing. It responds with secure headers for all requests (e.g., `X-Content-Type-Options: nosniff`, `X-Frame-Options: DENY`, and so on).

- All requests require at least authentication by default, but `PUT`s, `POST`s, and `DELETE`s require a higher level of security to be allowed, such as adding a CSRF token.

- Both `PUT` and `PATCH` can be used for updating, but they work in different ways. Essentially, `PUT` means “create or replace the complete record”, whereas `PATCH` means “update only some fields of the existing record” - in other words, a partial update. Partial updates free the client from having to load the entire record and then transmit the entire record back to the server. If the record is large enough, this can have a non-trivial impact on performance.

- The HTTP standard doesn't specify whether the `POST` or `PUT` verb is preferred for a Create operation! However, if you need the server to return the URI of the created resource (or the data you use to construct the URI), then you must use `POST`. Alternatively, when the resource URI is known at creation time, you can use `PUT`.

- When using `PUT` for updating, there are generally two approaches to what should be returned:

  - Return `201 CREATED` (if you created the object), or `200 OK` (if you replaced an existing object). In this case, it's recommended to return the object in the response body. This is useful if data was added to the object by the server (for example, if the server records the creation date).

  - Return `204 NO CONTENT`, and an empty response body. The rationale in this case is that since a PUT simply places an object at the URI in the request, the client doesn't need any information back - it knows that the object in the request has been saved, verbatim, on the server.

  - Additional info for debugging can be accessed by altering the `build.gradle` file:
    ```
      test {
        testLogging {
            ...
            showStandardStreams = true
        }
      }
    ```

- `	@DirtiesContext` should be added to all tests that change the data. If we don't add this annotation, these tests could affect the result of other tests in the file.

- Some authentication schemes are stateful, while others are stateless. Stateful means that your application remembers information about previous requests. Stateless means that your application remembers nothing about any previous requests.

  - **Form Login** is an example of a stateful authentication scheme. It stores the logged in user in a session. So long as the session's identifier is returned on subsequent requests, then the end user doesn't need to provide credentials again.

  - **HTTP Basic** is an example of a stateless authentication scheme. Since it remembers nothing from previous requests, you need to give it the username and password on every request.

- Spring Security activates HTTP Basic and Form Login authentication schemes by default. You can specify them and others directly with a custom `SecurityFilterChain` instance.

- JSON Web Token (JWT) is an industry standard format for encoding access tokens. In other words, when an authorization server mints an access token, it can write it in the widely-adopted JWT format. A decoded JWT at its most basic is a set of `headers` and `claims`:

  - `Headers`: contain metadata about the token, like how a resource server should process it.
  - `Claims`: facts that the token is asserting, like what principal the token represents. They are called "claims" because they still need to be verified by the resource server – the JWT "claims" these facts to be true. For example:

    - The `iss` claim identifies the authorization server that minted the token.
    - The `exp` claim indicates when the token expires.
    - The `scp` claim indicates the set of permissions the authorization server granted.
    - The `sub` claim is a reference to the principal the token represents.

- **It's a common temptation to want to use the JWT in stateful ways, like representing a session. Since this authentication scheme is stateless (like HTTP Basic), this is almost always a bad idea.**. JWTs expiry cannot be updated, but a session's expiry is updated on each request. Also consider that a person can log out and thus the session can be expired, but a JWT can't be expired (since its expiry cannot be edited). These important mismatches appear when we try to use a stateless token in stateful ways like session management.

- Using OAuth 2.0 terminology, replacing the Spring Security default HTTP Basic with OAuth 2.0 Bearer JWT Authentication turns our REST API ino an **OAuth 2.0 Resource Server**.

- Spring Security and Spring MVC provides multiple ways to access authentication information in your web application.

  - The `Authentication` method parameter type allows direct access to the authentication object.
  - The `@CurrentSecurityContext` annotation grants access to the entire security context, providing a comprehensive view of the authentication and other security-related information; remember that it provides the use of SpEL, type conversion and meta-annotations.
  - Finally, the `@AuthenticationPrincipal` annotation is suitable for extracting type-specific information from the principal, and you can see it as an alias of the `@CurrentSecurityContext(expression = "authentication.principal")`.

- Before authentication is attempted, Spring Security defends the application against malicious requests. These filters include:

  - `CsrfFilter`: the filter that checks incoming CSRF tokens and issues new ones
  - `HeaderFilter`: the filter that writes secure headers to the HTTP response

- Once the request is determined to be safe, the filter chain moves on to authenticating the request. Each authentication filter handles a single authentication scheme. For example:

  - `BasicAuthenticationFilter` - Handles HTTP Basic Authentication
  - `BearerTokenAuthenticationFilter` - Handles Bearer Token Authentication (including JWTs)
  - `UsernamePasswordAuthenticationFilter` - Handles Form Login Authentication
  - `AnonymousAuthenticationFilter` - Populates the context with a Null Object authentication instance

- `Authentication` is a Spring Security interface that represents both an authentication token (material to be authenticated, like a JWT) and an authentication result (authenticated material). If authentication fails, the `authenticationManager` throws an exception. This means that if the `authenticationManager` returns an `Authentication`, then the authentication succeeded.

- `AuthenticationManager` is an interface that tests an authentication token. If the test succeeds, then the `AuthenticationManager` constructs an authentication result. The `AuthenticationManager` is composed of several `AuthenticationProviders`, each of which handle a single authentication scheme, like authenticating a JWT.

- The `SecurityContext` is an object that holds the current `Authentication` (with Principal, Credentials, and Authorities)

- The reason for `SecurityContext` is so that applications can hold additional security information other than the current user, if they want to; however, this is a feature that is very rarely exercised in Spring Security.
