package raineduc.web3.beans;

import raineduc.web3.entities.hit.Hit;
import raineduc.web3.entities.hit.HitDao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("HitResults")
@ApplicationScoped
public class HitResults {
    @Inject
    private UserTransaction transaction;

    @PersistenceContext(unitName = "WebLab3")
    private EntityManager entityManager;

    @Inject
    private HitDao hitDao;

    private List<Hit> hits;

    public List<Hit> getHits() {
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
        hits = hitDao.getAllHits();
    }
}
