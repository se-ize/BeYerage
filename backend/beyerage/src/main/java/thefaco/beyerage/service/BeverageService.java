package thefaco.beyerage.service;

import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;

import java.util.List;

public interface BeverageService {

    Long addBeverage(Beverage beverage);
    List<Beverage> findBeverages();
    Beverage findOneById(Long id);
    Beverage findOneByName(String name);
    void updateBeverage(Long id, String name, int price, BottleType type, int size, Long frequency, BeverageLocation beverageLocation);
    void deleteBeverage(Beverage beverage);
    Beverage findMostFreqOne();
}
