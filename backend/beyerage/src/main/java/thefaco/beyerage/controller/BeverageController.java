package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.dto.beverage.BeverageCreateDto;
import thefaco.beyerage.dto.beverage.BeverageListDto;
import thefaco.beyerage.dto.beverage.BeverageLocListDto;
import thefaco.beyerage.dto.beverage.BeverageUpdateDto;
import thefaco.beyerage.service.BeverageService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BeverageController {

    private final BeverageService beverageService;

    /**
     * 음료 상세정보 추가 Form
     */
    @GetMapping("/beverage/new")
    public String createBeverageForm(Model model){
        log.info("음료 상세정보 추가 form access");

        //model에 DTO를 담아서 View 로 보냄
        model.addAttribute("beverageCreateDto", new BeverageCreateDto());

        return "beverage/createBeverageForm";
    }

    /**
     * 음료 추가
     */
    @PostMapping("/beverage/new")
    public String createBeverage(@Valid BeverageCreateDto beverageCreateDto, BindingResult result){
        //오류 나면 오류처리
        if(result.hasErrors()) return "beverage/createBeverageForm";

        //사용자의 입력에 대해 실제 객체를 생성
        BeverageLocation beverageLocation = BeverageLocation.createBeverageLocation(
                beverageCreateDto.getRow(),
                beverageCreateDto.getColumn()
        );
        Beverage beverage = Beverage.createBeverage(
                beverageCreateDto.getName(),
                beverageCreateDto.getPrice(),
                beverageCreateDto.getBottleType(),
                beverageCreateDto.getSize(),
                0L,
                beverageLocation
        );

        //실제 생성한 객체를 DB에 반영
        Long id = beverageService.addBeverage(beverage);

        log.info("음료 추가: id={} name={}", id, beverage.getName());

        return "redirect:/";
    }

    /**
     * 음료 조회
     */
    @GetMapping("/beverages")
    public String beverageInfo(Model model){
        //DB에서 음료 상세,위치정보를 가져와서 실제 엔티티가 아닌 DTO로 매핑
        List<Beverage> beverages = beverageService.findBeveragesWithLoc();
        List<BeverageListDto> beverageListDtos = beverages.stream()
                .map(b -> new BeverageListDto(b.getId(),b.getName(), b.getPrice(), b.getType(), b.getSize(), b.getBeverageLocation()))
                .collect(Collectors.toList());

        //model에 DTO를 담아서 View로 전송
        model.addAttribute("beverages", beverageListDtos);

        log.info("음료조회 form access");

        return "beverage/beverageList";
    }

    /**
     * 음료위치 조회
     */
    @GetMapping("/beveragesLoc")
    public String beverageLocInfo(Model model){
        //DB에서 음료 상세,위치정보를 가져와서 실제 엔티티가 아닌 DTO로 매핑
        List<Beverage> beverages = beverageService.findBeveragesWithLoc();
        List<BeverageLocListDto> beverageLocListDtos = beverages.stream()
                .map(b -> new BeverageLocListDto(b.getId(), b.getName(), b.getBeverageLocation()))
                .collect(Collectors.toList());

        List<List<String>> locations = switchLocationList(beverageLocListDtos);

        //model에 DTO를 담아서 View로 전송
        model.addAttribute("beveragesLoc", beverageLocListDtos);
        //model에 DTO를 담아서 View로 전송
        model.addAttribute("locations", locations);

        log.info("음료위치조회 form access");

        return "beverage/beverageLocList";
    }

    //각 위치에 음료 이름 담는 알고리즘
    private List<List<String>> switchLocationList(List<BeverageLocListDto> beverageLocListDtos) {

        List<List<String>> locations = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            locations.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                locations.get(i).add("");
            }
        }
        for (BeverageLocListDto beverageLocListDto : beverageLocListDtos) {
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 4; j++){
                    int row = beverageLocListDto.getBeverageLocation().getRow();
                    int column = beverageLocListDto.getBeverageLocation().getColumn();
                    if(row-1 == i && column-1 == j) locations.get(i).set(j, beverageLocListDto.getName());
                }
            }
        }
        return locations;
    }

    /**
     * 음료 수정 Form
     */
    @GetMapping("/beverages/{id}/edit")
    public String updateBeverageForm(@PathVariable("id") Long id, Model model){
        //사용자가 수정하려고 하는 음료의 id(PK)를 가지고 실제 DB에서 데이터를 찾는다
        Beverage beverage = beverageService.findOneById(id);
        //실제 DB에 있는 원래의 값을 DTO객체를 생성하여 매핑
        BeverageUpdateDto beverageUpdateDto = new BeverageUpdateDto(
                beverage.getId(),
                beverage.getName(),
                beverage.getPrice(),
                beverage.getType(),
                beverage.getSize(),
                beverage.getBeverageLocation().getRow(),
                beverage.getBeverageLocation().getColumn()
        );

        //model에 DTO를 담아서 View로 전송
        model.addAttribute("beverageUpdateDto", beverageUpdateDto);

        log.info("음료수정 form access");

        return "beverage/updateBeverageForm";
    }

    /**
     * 음료 수정
     */
    @PostMapping("/beverages/{id}/edit")
    public String updateBeverage(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("beverageUpdateDto") BeverageUpdateDto beverageUpdateDto,
            BindingResult result){

        //오류가 나면 오류메시지 출력
        if(result.hasErrors()){
            return "beverage/updateBeverageForm";
        }

        //사용자가 수정한 DTO 객체를 실제 DB 에 업데이트 해서 반영
        beverageService.updateBeverage(
                id,
                beverageUpdateDto.getName(),
                beverageUpdateDto.getPrice(),
                beverageUpdateDto.getBottleType(),
                beverageUpdateDto.getSize(),
                beverageUpdateDto.getRow(),
                beverageUpdateDto.getColumn()
                );

        log.info("음료수정: id={} name={}", beverageUpdateDto.getId(), beverageUpdateDto.getName());

        return "redirect:/beverages";
    }

    /**
     * 음료 삭제
     */
    @GetMapping("/beverages/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        //사용자가 삭제버튼을 누른 음료에 대한 id(PK)값으로 실제 DB에 있는 데이터를 삭제
        Beverage beverage = beverageService.findOneById(id);
        beverageService.deleteBeverage(beverage);

        log.info("음료삭제: id={} name={}", id, beverage.getName());

        return "redirect:/beverages";
    }
}
