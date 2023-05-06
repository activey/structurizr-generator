package com.structurizr.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UsedBy {
    String source() default "";
    String value() default "Uses";
    String technology() default "";
    String[] tags() default {};
}
