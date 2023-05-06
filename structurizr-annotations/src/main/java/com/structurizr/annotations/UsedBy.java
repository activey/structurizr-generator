package com.structurizr.annotations;

public @interface UsedBy {
    String source() default "";
    String value() default "Uses";
    String technology() default "";
    String[] tags() default {};
}
