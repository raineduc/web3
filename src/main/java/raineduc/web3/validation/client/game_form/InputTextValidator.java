package raineduc.web3.validation.client.game_form;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator("game_form.InputTextValidator")
public class InputTextValidator implements Validator {
    private static final String PARSE_ERROR = "В поле должно быть введено число с не более чем 8 целыми и 5" +
            " дробными цифрами";
    private static final String INTERVAL_ERROR = "Число должно лежать в пределах (-3, 5)";

    private String valuePattern;
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Locale locale = context.getViewRoot().getLocale();
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(locale);

        char decimalSeparator = decimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
        valuePattern = "^\\d{1,8}([\\\\" + decimalSeparator + "]\\d{0,5})?$";
        pattern = Pattern.compile(valuePattern);

        UIInput input = (UIInput) component;
        String inputValue = input.getSubmittedValue().toString();

        matcher = pattern.matcher(inputValue);

        try {
            if (!matcher.matches()) {
                throw new ParseException("parse error", 0);
            }

            double number = decimalFormat.parse(inputValue).doubleValue();
            boolean bool = number < -2.99999 || number > 4.99999;
            if (bool) {
                FacesMessage msg = new FacesMessage("Число должно лежать в пределах (-3, 5)");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        } catch (ParseException e) {
            FacesMessage msg = new FacesMessage(PARSE_ERROR);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
