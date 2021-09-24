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

    //음료 추가
    @Override
    @Transactional
    public Long addBeverage(Beverage beverage) {
        beverageRepository.save(beverage);
        return beverage.getId();
    }

    //모든 음료 상세정보 객체 조회
    @Override
    public List<Beverage> findBeverages() {
        return beverageRepository.findAll();
    }

    //모든 음료 상세정보와 위치정보 객체 조회
    @Override
    public List<Beverage> findBeveragesWithLoc() {
        return beverageRepository.findAllWithLoc();
    }

    //PK값으로 음료 상세정보 조회
    @Override
    public Beverage findOneById(Long id) {
        return beverageRepository.findById(id);
    }

    //이름으로 음료 상세정보 조회
    @Override
    public List<Beverage> findOneByName(String name) {
        return beverageRepository.findByName(name);
    }

    //이름으로 음료 상세정보와 위치정보 조회
    @Override
    public List<Beverage> findOneByNameWithLoc(String name) {
        return beverageRepository.findByNameWithLoc(name);
    }

    //음료 수정
    @Override
    @Transactional
    public void updateBeverage(Long id, String name, int price, BottleType type, int size, int row, int column) {
        Beverage findBeverage = beverageRepository.findByIdWithLoc(id).get(0);
        findBeverage.updateBeverage(
                findBeverage, name, price, type, size, row, column);
    }

    //Frequency 값 1 증가 메서드
    @Override
    @Transactional
    public void addFrequency(String name) {
        Beverage findBeverage = beverageRepository.findByName(name).get(0);
        findBeverage.addFrequency(findBeverage, findBeverage.getFrequency());
    }

    //음료 삭제
    @Override
    @Transactional
    public void deleteBeverage(Beverage beverage) {
        beverageRepository.delete(beverage);
    }

    //Frequency 값이 가장 높은 음료 하나를 조회하는 메서드
    @Override
    public List<Beverage> findMostFreqOne() {
        return beverageRepository.findMostFreq();
    }

    //Frequency 값이 가장 높은 음료 상세정보와 위치정보를 조회하는 메서드
    @Override
    public List<Beverage> findMostFreqOneWithLoc() {
        return beverageRepository.findMostFreqWithLoc();
    }

    //음료 위치가 겹치는지 여부를 확인하는 메서드
   @Override
    public Beverage checkDuplicateRowColumn(int row, int column) {
        List<Beverage> beverages = beverageRepository.findByRowAndColumn(row, column);
        if(!beverages.isEmpty()) return beverages.get(0);
        else return null;
    }
}
