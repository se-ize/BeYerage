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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BeverageServiceImplTest {

    @Autowired BeverageService beverageService;
    @Autowired BeverageRepository beverageRepository;

    @Test
    public void addBeverage() throws Exception{
        //given
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation("콜라", 1, 1);
        Beverage beverage = Beverage.createBeverage("콜라", 1000, BottleType.CAN, 250, 0L, beverageLocation);
        //when
        Long id = beverageService.addBeverage(beverage);
        Beverage findBeverage = beverageRepository.findById(id);
        //then
        Assertions.assertThat(beverage).isEqualTo(findBeverage);
        Assertions.assertThat(beverage.getBeverageLocation().getRow()).isEqualTo(findBeverage.getBeverageLocation().getRow());
    }
}