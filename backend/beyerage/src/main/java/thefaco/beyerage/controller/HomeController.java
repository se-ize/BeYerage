package thefaco.beyerage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    /**
     * 홈 화면
     */
    @RequestMapping("/")
    public String home(){
        log.info("Home access");
        return "home";
    }
}
