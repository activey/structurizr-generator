package com.structurizr.annotations;

public @interface Uses {
    String target() default "";
    String value() default "Uses";
    String technology() default "";
    String[] tags() default {};
}
