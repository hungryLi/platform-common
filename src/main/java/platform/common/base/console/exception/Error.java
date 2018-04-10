package platform.common.base.console.exception;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Error {

    String msg() default "";

    String code() default "";
}
