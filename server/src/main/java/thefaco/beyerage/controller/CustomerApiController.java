package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import thefaco.beyerage.domain.Customer;
import thefaco.beyerage.dto.view.CustomerListDto;
import thefaco.beyerage.service.CustomerService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping(value = "/addCustomerVoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerListDto addCustomerVoice(@RequestBody String text){
        Customer newCustomer = Customer.createCustomer(text);
        Long savedId = customerService.addText(newCustomer);

        CustomerListDto customerListDto = new CustomerListDto(savedId, text);

        log.info("Add new text savedId={}", savedId);

        return customerListDto;
    }
}
