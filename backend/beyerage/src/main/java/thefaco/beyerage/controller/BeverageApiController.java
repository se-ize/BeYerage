package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.dto.beverage.BeverageApiDto;
import thefaco.beyerage.dto.beverage.BeverageWithLocApiDto;
import thefaco.beyerage.service.BeverageService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BeverageApiController {

    private final BeverageService beverageService;

    /**
     * 음료 이름에 따른 상세정보 API
     * @param beverageName : 음료 이름
     * @return : 음료위치정보를 제외한 엔티티
     */
    @GetMapping(value = "/beverageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BeverageApiDto beverageInfo(@RequestParam("beverageName") String beverageName) {
        log.info("beverageInfoApi access: beverageName={}", beverageName);

        List<Beverage> findBeverage = beverageService.findOneByName(beverageName);
        List<BeverageApiDto> result = findBeverage.stream()
                .map(b -> new BeverageApiDto(b))
                .collect(Collectors.toList());

        if(result.isEmpty()) return null;
        else return result.get(0);

    }

    /**
     * 음료 이름에 따른 상세, 위치정보 API
     * @param beverageName :음료 이름
     * @return : 음료 엔티티
     * 아직 frequency++ 구현 X
     */
    @GetMapping(value = "/beverageWithLocInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BeverageWithLocApiDto beverageWithLocInfo(@RequestParam("beverageName") String beverageName) {
        log.info("beverageWithLocInfoApi access: beverageName={}", beverageName);

        List<Beverage> findBeverage = beverageService.findOneByNameWithLoc(beverageName);
        List<BeverageWithLocApiDto> result = findBeverage.stream()
                .map(b -> new BeverageWithLocApiDto(b))
                .collect(Collectors.toList());

        if(result.isEmpty()) return null;
        else return result.get(0);

    }

    /**
     * 가장 많이 찾는 음료 상세, 위치정보 API
     * @return : 음료 엔티티
     */
    @GetMapping(value = "/mostFreqBeverageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BeverageWithLocApiDto mostFreqBeverageWithLocInfo() {
        log.info("mostFreqBeverageWithLocInfoApi access");

        List<Beverage> findBeverage = beverageService.findMostFreqOneWithLoc();
        List<BeverageWithLocApiDto> result = findBeverage.stream()
                .map(b -> new BeverageWithLocApiDto(b))
                .collect(Collectors.toList());

        if(result.isEmpty()) return null;
        else return result.get(0);
    }

    /**
     * 모든 음료들에 대한 상세정보 API
     * @return : 음료 엔티티
     */
    @GetMapping(value = "/beveragesWithLocInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BeverageWithLocApiDto> beveragesWithLocInfo(){
        log.info("beveragesWithLocInfo access");

        List<Beverage> findBeverages = beverageService.findBeveragesWithLoc();
        List<BeverageWithLocApiDto> result = findBeverages.stream()
                .map(b -> new BeverageWithLocApiDto(b))
                .collect(Collectors.toList());

        return result;
    }
}
