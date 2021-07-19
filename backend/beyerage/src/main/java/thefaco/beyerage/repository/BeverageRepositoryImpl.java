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

    //음료 저장 메서드
    @Override
    public void save(Beverage beverage) {
        if(beverage.getId() == null){
            em.persist(beverage);
        } else {
            em.merge(beverage);
        }
    }

    //PK로 음료 한개를 찾는 메서드
    @Override
    public Beverage findById(Long id) {
        return em.find(Beverage.class, id);
    }

    @Override
    public Beverage findByIdWithLoc(Long id) {

        try{
            return em.createQuery("select b from Beverage b join fetch b.beverageLocation where b.id = :id", Beverage.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            throw e;
        }
    }

    //음료 이름으로 음료 한개를 찾는 메서드
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

    //음료 상세정보 전체를 조회하는 메서드
    @Override
    public List<Beverage> findAll() {
        return em.createQuery("select b from Beverage b", Beverage.class)
                .getResultList();
    }

    //음료 상세정보와 위치정보를 같이 조회하는 메서드
    @Override
    public List<Beverage> findAllWithLoc() {
        return em.createQuery("select b from Beverage b join fetch b.beverageLocation", Beverage.class)
                .getResultList();
    }

    //음료 한개를 삭제하는 메서드
    @Override
    public void delete(Beverage beverage) {
        em.remove(beverage);
    }

    //사람들이 가장 많이 찾는 음료를 가져오는 메서드
    @Override
    public Beverage findMostFreq() {

        try{
            return em.createQuery("select b from Beverage b where b.frequency = (select max(b.frequency) from Beverage b)"
                    , Beverage.class)
                    .getSingleResult();
        } catch (Exception e){
            throw e;
        }
    }
}
