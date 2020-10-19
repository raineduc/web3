package raineduc.web3.beans;

import org.hibernate.validator.constraints.UniqueElements;
import org.primefaces.PrimeFaces;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;
import raineduc.web3.entities.hit.Hit;
import raineduc.web3.entities.hit.HitDao;
import raineduc.web3.validation.server.game.InArray;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Named("gameBean")
@RequestScoped
public class HitBean implements Serializable {
    @Inject
    private HitDao hitDao;
    @Inject
    private UserTransaction transaction;
    @PersistenceContext(unitName = "WebLab3")
    private EntityManager entityManager;
    @UniqueElements(message = "Значения X должны быть разными")
    @Size(min = 1, max = 9, message = "Количество значений X должно быть в пределах [1, 9]")
    private Collection<@InArray(array = {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2}) Double> xCoordinates;
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
    @Inject
    private HitResults hitResults;

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
        try {
            List<Hit> hits = xCoordinates.stream()
                    .map(x -> new Hit(x, yCoordinate.doubleValue(), radius, isPointInArea(x, yCoordinate.doubleValue(), radius))).collect(Collectors.toList());
            hitDao.addHits(hits);
            for (Hit hitObject: hits) {
                hitResults.addHit(hitObject);
            }
            addParamsToResponse(hits);
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Database error", "Could not save records to the database"));
        }
        return null;
    }

    public void handleGameAreaAjax() throws javax.faces.event.AbortProcessingException {
        xCoordinates = new ArrayList<>();
        xCoordinates.add(xCoordinate.doubleValue());
        handleAjax();
    }

    @PostConstruct
    public void init() {
        xCoordinates = new ArrayList<>();
    }

    private void addParamsToResponse(List<Hit> hits) {
        PrimeFaces.Ajax ajax =  PrimeFaces.current().ajax();
        ajax.addCallbackParam("hits", buildHitsJsonArray(hits));
    }

    private boolean isPointInArea(double x, double y, int radius) {
        return isPointInCircle(x, y, radius) || isPointInSquare(x, y, radius) || isPointInTriangle(x, y, radius);
    }

    private boolean isPointInSquare(double x, double y, int radius) {
        return x >= 0 && y >= 0 && x <= radius && y <= radius;
    }

    private boolean isPointInTriangle(double x, double y, int radius) {
        return x <= 0 && y <= 0 && y >= (-x - radius / 2d);
    }

    private boolean isPointInCircle(double x, double y, int radius) {
        return x >= 0 && y <= 0 && Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(radius, 2);
    }

    private JSONArray buildHitsJsonArray(List<Hit> hits) {
        JSONArray jsonArray = new JSONArray();
        for (Hit hit: hits) {
            JSONObject jsonObject = new JSONObject()
                    .put("x", hit.getxCoordinate())
                    .put("y", hit.getyCoordinate())
                    .put("result", hit.isHit());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
