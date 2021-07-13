package thefaco.beyerage.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import thefaco.beyerage.domain.Beverage;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BeverageRepositoryImpl implements BeverageRepository {

    private final EntityManager em;

    @Override
    public void save(Beverage beverage) {
        if(beverage.getId() == null){
            em.persist(beverage);
        } else {
            em.merge(beverage);
        }
    }

    @Override
    public Beverage findById(Long id) {
        return em.find(Beverage.class, id);
    }

    @Override
    public Beverage findByName(String name) {

        try{
            return em.createQuery("select b from Beverage b where b.name = :name", Beverage.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch(Exception e) {
            throw e;
        }

    }

    @Override
    public List<Beverage> findAll() {
        return em.createQuery("select b from Beverage b", Beverage.class)
                .getResultList();
    }

    @Override
    public void delete(Beverage beverage) {
        em.remove(beverage);
    }

    @Override
    public Beverage findMostFreq() {

        try{
            return em.createQuery("select b from Beverage b where b.frequency = (select max(b.frequency) from Beverage b)", Beverage.class)
                    .getSingleResult();
        } catch (Exception e){
            throw e;
        }
    }
}
