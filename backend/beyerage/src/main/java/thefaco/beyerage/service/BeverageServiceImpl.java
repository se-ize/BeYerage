package thefaco.beyerage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Beverage;
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
    public List<Beverage> findBeveragesWithLoc() {
        return beverageRepository.findAllWithLoc();
    }

    @Override
    public Beverage findOneById(Long id) {
        return beverageRepository.findById(id);
    }

    @Override
    public List<Beverage> findOneByName(String name) {
        return beverageRepository.findByName(name);
    }

    @Override
    public List<Beverage> findOneByNameWithLoc(String name) {
        return beverageRepository.findByNameWithLoc(name);
    }

    @Override
    @Transactional
    public void updateBeverage(Long id, String name, int price, BottleType type, int size, int row, int column) {
        Beverage findBeverage = beverageRepository.findByIdWithLoc(id).get(0);
        findBeverage.updateBeverage(
                findBeverage, name, price, type, size, row, column);
    }

    @Override
    @Transactional
    public void addFrequency(String name) {
        Beverage findBeverage = beverageRepository.findByName(name).get(0);
        findBeverage.addFrequency(findBeverage, findBeverage.getFrequency());
    }

    @Override
    @Transactional
    public void deleteBeverage(Beverage beverage) {
        beverageRepository.delete(beverage);
    }

    @Override
    public List<Beverage> findMostFreqOne() {
        return beverageRepository.findMostFreq();
    }

    @Override
    public List<Beverage> findMostFreqOneWithLoc() {
        return beverageRepository.findMostFreqWithLoc();
    }

    @Override
    public Beverage checkDuplicateRowColumn(int row, int column) {
        List<Beverage> beverages = beverageRepository.findByRowAndColumn(row, column);
        if(!beverages.isEmpty()) return beverages.get(0);
        else return null;
    }
}
