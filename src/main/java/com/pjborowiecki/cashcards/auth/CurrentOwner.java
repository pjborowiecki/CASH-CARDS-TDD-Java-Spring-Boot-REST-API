package com.pjborowiecki.cashcards.auth;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

import org.springframework.security.core.annotation.CurrentSecurityContext;

@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@CurrentSecurityContext(expression = "authentication.name")
public @interface CurrentOwner {
}
