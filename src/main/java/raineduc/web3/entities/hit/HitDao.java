package raineduc.web3.entities.hit;

import java.util.List;

public interface HitDao {
    List<Hit> getAllHits();
    void addHits(List<Hit> hits);
}
