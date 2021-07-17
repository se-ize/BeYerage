package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.dto.beverage.BeverageCreateDto;
import thefaco.beyerage.service.BeverageService;

import javax.validation.Valid;

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

}
