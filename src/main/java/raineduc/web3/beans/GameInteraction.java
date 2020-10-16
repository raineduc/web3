package raineduc.web3.beans;


import org.hibernate.validator.constraints.UniqueElements;
import raineduc.web3.validation.server.game.InArray;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@ManagedBean(name="gameBean")
@SessionScoped
public class GameInteraction implements Serializable {
    @UniqueElements(message = "Значения X должны быть разными")
    @Size(min = 1, max = 9, message = "Количество значений X должно быть в пределах [1, 9]")
    private Collection<@InArray(array = {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2})  Double> xCoordinates;
    @NotNull(message = "Y координата не должна быть пустой")
    @DecimalMin(value = "-7.99999", message = "Y координата должна быть больше - 3")
    @DecimalMax(value = "7.99999", message = "Y координата должна быть меньше 5")
    @Digits(integer = 8, fraction = 5, message = "Y должнен быть числом с не более чем 8 цифрами в целой части и 5 цифрами в дробной части")
    private BigDecimal yCoordinate;
    @DecimalMin(value = "-7.99999", message = "X координата должна быть больше -8")
    @DecimalMax(value = "7.99999", message = "X координата должна быть меньше 8")
    @Digits(integer = 8, fraction = 5, message = "X должнен быть числом с не более чем 8 цифрами в целой части и 5 цифрами в дробной части")
    private BigDecimal xCoordinate;
    @NotNull(message = "Радиус должен быть указан")
    @Min(value = 1, message = "Радиус должен быть больше или равен 1")
    @Max(value = 5, message = "Радиус должен быть меньше или равен 5")
    private int radius;

    public BigDecimal getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(BigDecimal xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setxCoordinates(Collection<Double> xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public void setyCoordinate(BigDecimal yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Collection<Double> getxCoordinates() {
        return xCoordinates;
    }

    public BigDecimal getyCoordinate() {
        return yCoordinate;
    }

    public int getRadius() {
        return radius;
    }

    public void handleAjax(javax.faces.event.AjaxBehaviorEvent event) throws javax.faces.event.AbortProcessingException {
        int i = 3;
        System.out.println(yCoordinate);
    }

    public void handleGameAreaAjax() throws javax.faces.event.AbortProcessingException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        Double x = Double.valueOf(params.get("xCoordinates"));
//        ArrayList<Double> xList = new ArrayList<>();
//        xList.add(x);
//        setxCoordinates(xList);
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<GameInteraction>> violations = validator.validateProperty(this, "xCoordinates");
//        for (ConstraintViolation<GameInteraction> violation: violations) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, violation.getMessage(), "message error"));
//            return;
//        }
    }
}
