package raineduc.web3;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {
    @SuppressWarnings("unused")
    @PersistenceContext(unitName = "WebLab3")
    @Produces
    private EntityManager entityManager;
}
