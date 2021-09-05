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

    //음료 상세정보와 위치정보를 동시에 찾는 메서드
    @Override
    public List<Beverage> findByIdWithLoc(Long id) {
        return em.createQuery("select b from Beverage b join fetch b.beverageLocation where b.id = :id", Beverage.class)
                .setParameter("id", id)
                .getResultList();
    }

    //음료 이름으로 음료 한개를 찾는 메서드
    @Override
    public List<Beverage> findByName(String name) {
        return em.createQuery("select b from Beverage b where b.name = :name", Beverage.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Beverage> findByNameWithLoc(String name) {
        return em.createQuery("select b from Beverage b join fetch b.beverageLocation where b.name = :name", Beverage.class)
                .setParameter("name", name)
                .getResultList();
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
    public List<Beverage> findMostFreq() {
        return em.createQuery("select b from Beverage b where b.frequency = (select max(b.frequency) from Beverage b)"
                , Beverage.class)
                .getResultList();
    }
    //사람들이 가장많이 찾는 음료상세정보와 위치정보를 가져오는메서드
    @Override
    public List<Beverage> findMostFreqWithLoc() {
        return em.createQuery("select b from Beverage b join fetch b.beverageLocation where b.frequency = (select max(b.frequency) from Beverage b)"
                , Beverage.class)
                .getResultList();
    }

    @Override
    public List<Beverage> findByRowAndColumn(int row, int column) {
        return em.createQuery("select b from Beverage b join fetch b.beverageLocation" +
                " where b.beverageLocation.row = :row and b.beverageLocation.column = :column", Beverage.class)
                .setParameter("row", row)
                .setParameter("column", column)
                .getResultList();
    }
}
