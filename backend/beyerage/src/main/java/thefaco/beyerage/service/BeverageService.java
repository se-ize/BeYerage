package thefaco.beyerage.service;

import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;

import java.util.List;

public interface BeverageService {

    Long addBeverage(Beverage beverage);
    List<Beverage> findBeverages();
    List<Beverage> findBeveragesWithLoc();
    Beverage findOneById(Long id);
    List<Beverage> findOneByName(String name);
    List<Beverage> findOneByNameWithLoc(String name);
    void updateBeverage(Long id, String name, int price, BottleType type, int size, int row, int column);
    void addFrequency(String name);
    void deleteBeverage(Beverage beverage);
    List<Beverage> findMostFreqOne();
    List<Beverage> findMostFreqOneWithLoc();
}
