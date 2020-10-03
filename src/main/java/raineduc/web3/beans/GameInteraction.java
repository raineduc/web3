package raineduc.web3.beans;


import org.hibernate.validator.constraints.UniqueElements;
import raineduc.web3.validation.game.InArray;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.*;
import java.util.Collection;

@ManagedBean(name="gameBean")
@SessionScoped
public class GameInteraction {
    @UniqueElements(message = "Значения X должеы быть разными")
    @Size(min = 1, max = 9, message = "Количество значений X должно быть в пределах [1, 9]")
    private Collection<@InArray(array = {1, 2})  Double> xCoordinate;
    @NotNull(message = "Y координата не должна быть пустой")
    @DecimalMin(value = "-2.99999", message = "Y координата должна быть меньше 5")
    @DecimalMax(value = "4.99999", message = "Y координата должна быть больше - 3")
    @Digits(integer = 8, fraction = 5, message = "Y должнен быть числом с не более чем 8 цифрами в целой части и 5 цифрами в дробной части")
    private double yCoordinate;
    @NotNull(message = "Радиус должен быть указан")
    @Min(value = 1, message = "Радиус должен быть больше или равен 1")
    @Max(value = 5, message = "Радиус должен быть меньше или равен 5")
    private int radius;

    public void setxCoordinate(Collection<Double> xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Collection<Double> getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public int getRadius() {
        return radius;
    }

    public void handleAjax(javax.faces.event.AjaxBehaviorEvent event) throws javax.faces.event.AbortProcessingException {
        int i = 3;
        System.out.println(yCoordinate);
    }
}
