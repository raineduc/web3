package raineduc.web3.validation.game;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({ FIELD, PARAMETER, TYPE_USE, CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { InArrayValidator.class })
public @interface InArray {
    String message() default "Must be in ${joinedArray}";
    double[] array();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
