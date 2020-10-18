package raineduc.web3.beans;

import raineduc.web3.entities.Hit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("HitResults")
@SessionScoped
public class HitResults implements Serializable {
    @Inject
    private UserTransaction transaction;
    @PersistenceContext(unitName = "WebLab3")
    private EntityManager entityManager;

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
        try {
            transaction.begin();
            hits = entityManager.createQuery("SELECT hit FROM Hit hit", Hit.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
