package thefaco.beyerage.repository;

import thefaco.beyerage.domain.Beverage;

import java.util.List;

public interface BeverageRepository {

    void save(Beverage beverage);
    Beverage findById(Long id);
    List<Beverage> findByIdWithLoc(Long id);
    List<Beverage> findByName(String name);
    List<Beverage> findByNameWithLoc(String name);
    List<Beverage> findAll();
    List<Beverage> findAllWithLoc();
    void delete(Beverage beverage);
    List<Beverage> findMostFreq();
    List<Beverage> findMostFreqWithLoc();
    List<Beverage> findByRowAndColumn(int row, int column);
}
