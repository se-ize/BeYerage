package thefaco.beyerage.repository;

import thefaco.beyerage.domain.Beverage;

import java.util.List;

public interface BeverageRepository {

    void save(Beverage beverage);
    Beverage findById(Long id);
    Beverage findByName(String name);
    List<Beverage> findAll();
    void delete(Beverage beverage);
    Beverage findMostFreq();
}
