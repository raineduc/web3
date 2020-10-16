package raineduc.web3.beans;

import org.hibernate.validator.constraints.UniqueElements;
import raineduc.web3.entities.Hit;
import raineduc.web3.validation.server.game.InArray;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Named("gameBean")
@RequestScoped
public class HitBean implements Serializable {
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
    private boolean hit;
    @Inject
    private HitResults hitResults;

    public boolean isHit() {
        return hit;
    }

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

    public String handleAjax() throws javax.faces.event.AbortProcessingException {
        for (Double xCoord: xCoordinates) {
            hit = isPointInArea(xCoord, yCoordinate.doubleValue(), radius);
            Hit hitObject = new Hit(xCoord, yCoordinate.doubleValue(), radius, hit);
            hitResults.addHit(hitObject);
        }
        return null;
    }

    public void handleGameAreaAjax() throws javax.faces.event.AbortProcessingException {
        hit = isPointInArea(xCoordinate.doubleValue(), yCoordinate.doubleValue(), radius);
        xCoordinates.add(xCoordinate.doubleValue());
        Hit hitObject = new Hit(xCoordinate.doubleValue(), yCoordinate.doubleValue(), radius, hit);
        hitResults.addHit(hitObject);
    }

    @PostConstruct
    public void init() {
        xCoordinates = new ArrayList<>();
    }

    private boolean isPointInArea(double x, double y, int radius) {
        return isPointInCircle(x, y, radius) || isPointInSquare(x, y, radius) || isPointInTriangle(x, y, radius);
    }

    private boolean isPointInSquare(double x, double y, int radius) {
        return x >= 0 && y >= 0 && x <= radius && y <= radius;
    }

    private boolean isPointInTriangle(double x, double y, int radius) {
        return x <= 0 && y <= 0 && y >= (-x - radius/2d);
    }

    private boolean isPointInCircle(double x, double y, int radius) {
        return x >= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(radius, 2);
    }
}
