package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.dto.api.BeverageWithLocApiDto;
import thefaco.beyerage.service.BeverageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BeverageApiController {

    private final BeverageService beverageService;

    /**
     * 음료 이름에 따른 상세, 위치정보 API -> 모바일 앱에서 사용
     * @param beverageName :음료 이름
     * @return : 음료 엔티티
     */
    @GetMapping(value = "/beverageWithLocInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BeverageWithLocApiDto beverageWithLocInfo(@RequestParam("beverageName") String beverageName) {
        log.info("beverageWithLocInfoApi access: beverageName={}", beverageName);

        Optional<Beverage> findBeverage = beverageService.findOneByNameWithLoc(beverageName);

        if(findBeverage.isPresent()){
            List<BeverageWithLocApiDto> result = findBeverage.stream()
                    .map(BeverageWithLocApiDto::new)
                    .collect(Collectors.toList());

            //모바일 앱에서 호출 시 frequency 1 증가
            beverageService.addFrequency(beverageName);
            return result.get(0);
        } else {
            return null;
        }
    }

    /**
     * 가장 많이 찾는 음료 상세, 위치정보 API -> 모바일 앱에서 사용
     * @return : 음료 엔티티
     */
    @GetMapping(value = "/mostFreqBeverageInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public BeverageWithLocApiDto mostFreqBeverageWithLocInfo() {
        log.info("mostFreqBeverageWithLocInfoApi access");

        Optional<Beverage> findBeverage = beverageService.findMostFreqOneWithLoc();
        if(findBeverage.isPresent()){
            List<BeverageWithLocApiDto> result = findBeverage.stream()
                    .map(BeverageWithLocApiDto::new)
                    .collect(Collectors.toList());
            return result.get(0);
        } else {
            return null;
        }
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
                .map(BeverageWithLocApiDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
