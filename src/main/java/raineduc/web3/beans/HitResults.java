package raineduc.web3.beans;

import raineduc.web3.entities.hit.Hit;
import raineduc.web3.entities.hit.HitDao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayDeque;
import java.util.Deque;

@Named("HitResults")
@ApplicationScoped
public class HitResults {
    @Inject
    private HitDao hitDao;

    private Deque<Hit> hits;

    public Deque<Hit> getHits() {
        return hits;
    }

    public void setHits(Deque<Hit> hits) {
        this.hits = hits;
    }

    public void addHit(Hit hit) {
        hits.addFirst(hit);
    }

    @PostConstruct
    public void init() {
        hits = new ArrayDeque<>(hitDao.getAllHits());
    }
}
