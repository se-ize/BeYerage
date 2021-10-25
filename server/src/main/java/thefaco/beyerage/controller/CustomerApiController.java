package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import thefaco.beyerage.domain.Customer;
import thefaco.beyerage.service.CustomerService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("/addCustomerVoice")
    public void addCustomerVoice(@RequestBody String text){
        Customer newCustomer = Customer.createCustomer(text, 0);
        Long savedId = customerService.addText(newCustomer);

        log.info("Add new text savedId={}", savedId);
    }
}
