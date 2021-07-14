package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import thefaco.beyerage.service.BeverageService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BeverageController {

    private final BeverageService beverageService;


}
