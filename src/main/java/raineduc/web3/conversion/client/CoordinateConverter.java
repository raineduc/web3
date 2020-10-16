package raineduc.web3.conversion.client;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

@FacesConverter("Coordinate")
public class CoordinateConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) throws ConverterException {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(new Locale("en", "US"));
        decimalFormat.setMaximumFractionDigits(5);
        decimalFormat.setMaximumIntegerDigits(3);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal number = (BigDecimal) decimalFormat.parse(newValue, new ParsePosition(0));
        if (number == null) {
            FacesMessage msg = new FacesMessage("Parse error", "Can not parse number from the given string");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        String result = decimalFormat.format(number);
        return decimalFormat.parse(result, new ParsePosition(0));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        return value.toString();
    }
}
