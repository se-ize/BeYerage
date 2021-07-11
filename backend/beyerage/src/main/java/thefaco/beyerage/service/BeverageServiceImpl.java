package thefaco.beyerage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;
import thefaco.beyerage.repository.BeverageRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BeverageServiceImpl implements BeverageService {

    private final BeverageRepository beverageRepository;

    @Override
    @Transactional
    public Long addBeverage(Beverage beverage) {
        beverageRepository.save(beverage);
        return beverage.getId();
    }

    @Override
    public List<Beverage> findBeverages() {
        return beverageRepository.findAll();
    }

    @Override
    public Beverage findOneById(Long id) {
        return beverageRepository.findById(id);
    }

    @Override
    public Beverage findOneByName(String name) {
        return beverageRepository.findByName(name);
    }

    @Override
    @Transactional
    public void updateBeverage(Long id, String name, int price, BottleType type, int size, Long frequency, BeverageLocation beverageLocation) {
        Beverage findBeverage = beverageRepository.findById(id);
        findBeverage.updateBeverage(findBeverage, name, price, type, size, frequency, beverageLocation);
    }

    @Override
    @Transactional
    public void deleteBeverage(Beverage beverage) {
        beverageRepository.delete(beverage);
    }
}
