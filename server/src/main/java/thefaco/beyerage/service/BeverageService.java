package thefaco.beyerage.service;

import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;

import java.util.List;
import java.util.Optional;

public interface BeverageService {

    Long addBeverage(Beverage beverage);
    List<Beverage> findBeveragesWithLoc();
    Beverage findOneById(Long id);
    Optional<Beverage> findOneByNameWithLoc(String name);
    void updateBeverage(Long id, String name, int price, BottleType type, int size, int row, int column);
    void addFrequency(String name);
    void deleteBeverage(Beverage beverage);
    Optional<Beverage> findMostFreqOneWithLoc();
    Beverage checkDuplicateRowColumn(int row, int column);
}
