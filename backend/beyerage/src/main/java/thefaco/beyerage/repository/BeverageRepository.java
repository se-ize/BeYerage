package thefaco.beyerage.repository;

import thefaco.beyerage.domain.Beverage;

import java.util.List;

public interface BeverageRepository {

    void save(Beverage beverage);
    Beverage findById(Long id);
    Beverage findByIdWithLoc(Long id);
    Beverage findByName(String name);
    List<Beverage> findAll();
    List<Beverage> findAllWithLoc();
    void delete(Beverage beverage);
    Beverage findMostFreq();
}
