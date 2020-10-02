package raineduc.web3;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Collection;

@ManagedBean(name="gameBean")
@SessionScoped
public class GameInteraction {
    private Collection<Float> xCoordinate;
    private float yCoordinate;
    private int radius;

    public void setxCoordinate(Collection<Float> xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Collection<Float> getxCoordinate() {
        return xCoordinate;
    }

    public float getyCoordinate() {
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
