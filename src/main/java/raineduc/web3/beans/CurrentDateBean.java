package raineduc.web3.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named("dateBean")
@SessionScoped
public class CurrentDateBean implements Serializable {
    private Date currentDate;
    private String timezone;

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String requestCurrentDate() {
        currentDate = new Date();
        return null;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @PostConstruct
    public void init() {
        currentDate = new Date();
    }
}
