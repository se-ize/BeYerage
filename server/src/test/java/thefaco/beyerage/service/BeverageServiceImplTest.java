package thefaco.beyerage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;
import thefaco.beyerage.repository.BeverageRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BeverageServiceImplTest {

    @Autowired BeverageService beverageService;
    @Autowired
    BeverageRepository beverageJpaRepository;

    @Autowired EntityManager em;

    private Beverage beverage;

    @BeforeEach
    void beforeEach(){
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation(4, 4);
        beverage = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 240, 1L, beverageLocation);
    }

    @Test
    @DisplayName("음료 추가 서비스 테스트")
    public void addBeverage() throws Exception{
        //given
        Long savedId = beverageService.addBeverage(beverage);
        em.flush();
        em.clear();
        //when
        Beverage findBeverage = beverageJpaRepository.findById(savedId).get();
        //then
        assertThat(findBeverage.getId()).isEqualTo(savedId);
    }

    @Test
    @DisplayName("음료 조회 서비스 테스트")
    public void findBeveragesWithLoc() throws Exception {
        //when
        List<Beverage> findBeverages = beverageService.findBeveragesWithLoc();
        //then
        assertThat(findBeverages.size()).isEqualTo(9);
    }

    @Test
    @DisplayName("음료 수정 서비스 테스트")
    public void updateBeverage() throws Exception {
        //given
        Long savedId = beverageService.addBeverage(beverage);
        //when
        beverageService.updateBeverage(savedId, "사이다", 2000, BottleType.CAN, 380, 4, 4);
        em.flush();
        em.clear();
        //then
        assertThat(beverage.getName()).isEqualTo("사이다");
    }

    @Test
    @DisplayName("음료 삭제 서비스 테스트")
    public void deleteBeverage() throws Exception {
        //given
        Long savedId = beverageService.addBeverage(beverage);
        //when
        Beverage findBeverage = beverageService.findOneById(savedId);
        beverageService.deleteBeverage(findBeverage);
        em.flush();
        em.clear();
        //then
        assertThat(beverageService.findOneById(savedId)).isNull();
    }

    @Test
    @DisplayName("가장 많이찾는 음료 조회 테스트")
    public void findMostFreqBeverage() throws Exception {
        //when
        Optional<Beverage> findBeverage = beverageService.findMostFreqOneWithLoc();
        //then
        assertThat(findBeverage.get().getName()).isEqualTo("코카콜라");
    }

}