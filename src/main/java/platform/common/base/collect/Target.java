package platform.common.base.collect;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention( RetentionPolicy.RUNTIME )
public @interface Target {

  String desc() default "";

  String code() default "";
}
