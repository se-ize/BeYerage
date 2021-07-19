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
import thefaco.beyerage.dto.beverage.BeverageUpdateDto;
import thefaco.beyerage.service.BeverageService;

import javax.validation.Valid;
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

        Long id = beverageService.addBeverage(beverage);

        log.info("음료 추가: id={} name={}", id, beverage.getName());

        return "redirect:/";
    }

    /**
     * 음료 조회
     */
    @GetMapping("/beverages")
    public String beverageInfo(Model model){
        List<Beverage> beverages = beverageService.findBeveragesWithLoc();
        List<BeverageListDto> beverageListDtos = beverages.stream()
                .map(b -> new BeverageListDto(b.getId(),b.getName(), b.getPrice(), b.getType(), b.getSize(), b.getBeverageLocation()))
                .collect(Collectors.toList());

        model.addAttribute("beverages", beverageListDtos);

        log.info("음료조회 form access");
        return "beverage/beverageList";
    }

    /**
     * 음료 수정 Form
     */
    @GetMapping("/beverages/{id}/edit")
    public String updateBeverageForm(@PathVariable("id") Long id, Model model){
        Beverage beverage = beverageService.findOneById(id);
        BeverageUpdateDto beverageUpdateDto = new BeverageUpdateDto(
                beverage.getId(),
                beverage.getName(),
                beverage.getPrice(),
                beverage.getType(),
                beverage.getSize(),
                beverage.getBeverageLocation().getRow(),
                beverage.getBeverageLocation().getColumn()
        );

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
        Beverage beverage = beverageService.findOneById(id);
        beverageService.deleteBeverage(beverage);

        log.info("음료삭제: id={} name={}", id, beverage.getName());

        return "redirect:/beverages";
    }
}
