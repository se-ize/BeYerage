package thefaco.beyerage.repository;

import org.assertj.core.api.Assertions;
import org.hibernate.TransientPropertyValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BeverageJpaRepositoryTest {

    @Autowired BeverageJpaRepository beverageJpaRepository;
    @Autowired EntityManager em;

    private Beverage beverage;
    private List<Beverage> beverages = new ArrayList<>(3);

    @BeforeEach
    void beforeEach(){
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation(4, 4);
        beverage = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 240, 1L, beverageLocation);
    }

    @Test
    @DisplayName("음료 저장 테스트")
    void save(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(this.beverage);
        //when
        String findName = savedBeverage.getName();
        //then
        assertThat(findName).isEqualTo("콜라");
        assertThat(savedBeverage).isInstanceOf(Beverage.class);
    }

    @Test
    @DisplayName("PK 값으로 음료 찾기 테스트")
    void findById(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(beverage);
        //when
        Optional<Beverage> findBeverage = beverageJpaRepository.findById(savedBeverage.getId());
        //then
        assertThat(findBeverage.get().getId()).isEqualTo(savedBeverage.getId());
    }

    @Test
    @DisplayName("PK 값으로 음료 조회 테스트 - 스프링 데이터 JPA 기본 메서드 사용")
    void findByIdWithLoc1(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(beverage);
        em.flush();
        em.clear();
        //when
        Optional<Beverage> findBeverage = beverageJpaRepository.findById(savedBeverage.getId());
        //then
        /**
         * 음료 상세정보만을 불러올 때 조인하지 않고 Query 가 날라간다 -> LAZY 로딩
         * 음료 위치정보를 불러올 때 외부조인으로 Query 가 날라간다
         * -> 스프링 데이터 JPA의 기본 메서드를 사용하지 말고 패치조인으로 쿼리가 한번 날라가게 최적화필요
         */
        assertThat(findBeverage.get().getId()).isEqualTo(savedBeverage.getId());
        assertThat(findBeverage.get().getBeverageLocation()).isInstanceOf(savedBeverage.getBeverageLocation().getClass());
        assertThat(findBeverage.get().getBeverageLocation().getRow()).isEqualTo(savedBeverage.getBeverageLocation().getRow());
    }

    @Test
    @DisplayName("PK 값으로 음료 조회 테스트 - 사용자 지정 메서드 사용")
    void findByIdWithLoc2(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(beverage);
        em.flush();
        em.clear();
        //when
        Optional<Beverage> findBeverage = beverageJpaRepository.findByIdWithLoc(savedBeverage.getId());
        //then
        //쿼리가 내부 조인으로 한번만 날라감
        assertThat(findBeverage.get().getId()).isEqualTo(savedBeverage.getId());
        assertThat(findBeverage.get().getBeverageLocation()).isInstanceOf(savedBeverage.getBeverageLocation().getClass());
        assertThat(findBeverage.get().getBeverageLocation().getRow()).isEqualTo(savedBeverage.getBeverageLocation().getRow());
    }

    @Test
    @DisplayName("음료 이름으로 조회 테스트")
    void findByNameOrBeverageLocation(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(beverage);
        em.flush();
        em.clear();
        //when
        Optional<Beverage> findBeverage = beverageJpaRepository.findByNameWithLoc(savedBeverage.getName());
        //then
        assertThat(findBeverage.get().getId()).isEqualTo(savedBeverage.getId());
        assertThat(findBeverage.get().getBeverageLocation()).isInstanceOf(savedBeverage.getBeverageLocation().getClass());
        assertThat(findBeverage.get().getBeverageLocation().getRow()).isEqualTo(savedBeverage.getBeverageLocation().getRow());
    }

    @Test
    @DisplayName("음료 전체 조회 테스트")
    void findAll(){
        //given
        List<Beverage> savedBeverages = beverageJpaRepository.saveAll(this.beverages);
        em.flush();
        em.clear();
        //when
        List<Beverage> findBeverages = beverageJpaRepository.findAllWithLoc();
        //then
        assertThat(findBeverages.size()).isEqualTo(9);
        assertThat(findBeverages.get(0).getBeverageLocation()).isInstanceOf(savedBeverages.get(0).getBeverageLocation().getClass());
    }

    @Test
    @DisplayName("음료 삭제 테스트")
    void delete(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(beverage);
        em.flush();
        em.clear();
        //when
        beverageJpaRepository.delete(savedBeverage);
        //then
        assertThat(beverageJpaRepository.findByIdWithLoc(savedBeverage.getId())).isEmpty();
    }

    @Test
    @DisplayName("가장 많이찾는 음료 조회 테스트")
    void findFirstByFrequency(){
        //when
        Optional<Beverage> findBeverage = beverageJpaRepository.findMostFreqWithLoc();
        //then
        assertThat(findBeverage.get().getName()).isEqualTo("코카콜라");
    }

    @Test
    @DisplayName("특정 행, 열의 음료 조회 테스트")
    void findByRowAndColumn(){
        //given
        Beverage savedBeverage = beverageJpaRepository.save(this.beverage);
        em.flush();
        em.clear();
        //when
        Optional<Beverage> findBeverage = beverageJpaRepository.findByRowAndColumn(4, 4);
        //then
        assertThat(findBeverage.get().getName()).isEqualTo("콜라");
        assertThat(findBeverage.get()).isInstanceOf(savedBeverage.getClass());
    }
}