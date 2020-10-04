package raineduc.web3.validation.server.game;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class InArrayValidator implements ConstraintValidator<InArray, Double> {
    protected double[] array;

    @Override
    public void initialize(InArray inArray) {
        this.array = inArray.array();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("joinedArray", Arrays.toString(array));
        if (value == null) return false;
        boolean valid = DoubleStream.of(array).anyMatch(x -> x == value);
        return valid;
    }
}
