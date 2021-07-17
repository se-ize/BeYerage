package thefaco.beyerage.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;
import thefaco.beyerage.repository.BeverageRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BeverageServiceImplTest {

    @Autowired BeverageService beverageService;
    @Autowired BeverageRepository beverageRepository;

    @Test
    public void addBeverage() throws Exception{
        //given
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation(1, 1);
        Beverage beverage = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 250, 0L, beverageLocation);
        //when
        Long id = beverageService.addBeverage(beverage);
        Beverage findBeverage = beverageRepository.findById(id);
        //then
        assertThat(beverage).isEqualTo(findBeverage);
        assertThat(beverage.getBeverageLocation().getRow()).isEqualTo(findBeverage.getBeverageLocation().getRow());
    }

    @Test
    public void updateBeverage() throws Exception {
        //given
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation(1, 1);
        Beverage beverage = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 250, 0L, beverageLocation);
        //when
        Long id = beverageService.addBeverage(beverage);
        beverageService.updateBeverage(id, "사이다", 2000, BottleType.CAN, 380, 0L, beverageLocation);
        //then
        assertThat(beverage.getName()).isEqualTo("사이다");
    }

    @Test
    public void deleteBeverage() throws Exception {
        //given
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation(1, 1);
        Beverage beverage = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 250, 0L, beverageLocation);
        Long id = beverageService.addBeverage(beverage);
        //when
        Beverage findBeverage = beverageService.findOneById(id);
        beverageService.deleteBeverage(findBeverage);
        //then
        assertThat(beverageService.findOneById(id)).isEqualTo(null);
    }

    @Test
    public void findMostFreqBeverage() throws Exception {
        //given
        BeverageLocation beverageLocation1 = BeverageLocation.createBeverageLocation(1, 1);
        Beverage beverage1 = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 250, 10L, beverageLocation1);
        Long id1 = beverageService.addBeverage(beverage1);

        BeverageLocation beverageLocation2 = BeverageLocation.createBeverageLocation(1, 2);
        Beverage beverage2 = Beverage.createBeverage("사이다", 1000, BottleType.CAN, 250, 5L, beverageLocation2);
        Long id2 = beverageService.addBeverage(beverage2);
        //when
        Beverage mostFreqBeverage = beverageService.findMostFreqOne();
        //then
        assertThat(mostFreqBeverage.getName()).isEqualTo("콜라");
    }
}