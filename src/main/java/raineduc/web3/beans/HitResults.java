package raineduc.web3.beans;

import raineduc.web3.entities.Hit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;

@Named("HitResults")
@SessionScoped
public class HitResults implements Serializable {
    private ArrayList<Hit> hits;

    public ArrayList<Hit> getHits() {
        return hits;
    }

    public void setHits(ArrayList<Hit> hits) {
        this.hits = hits;
    }

    public void addHit(Hit hit) {
        hits.add(hit);
    }

    @PostConstruct
    public void init() {
        hits = new ArrayList<>();
    }
}
