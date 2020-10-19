package raineduc.web3.entities.hit;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;

@Model
public class HitDaoImpl implements HitDao {
    @PersistenceContext(unitName = "WebLab3")
    private EntityManager entityManager;

    @Inject
    private UserTransaction transaction;

    public List<Hit> getAllHits() {
        try {
            transaction.begin();
            List<Hit> hits = entityManager.createQuery("SELECT hit FROM Hit hit", Hit.class).getResultList();
            transaction.commit();
            return hits;
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void addHits(List<Hit> hits) {
        try {
            transaction.begin();
            for (Hit hitObject : hits) {
                entityManager.persist(hitObject);
                entityManager.flush();
                entityManager.clear();
            }
            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (SystemException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
