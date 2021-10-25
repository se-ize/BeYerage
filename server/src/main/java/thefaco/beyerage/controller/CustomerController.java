package thefaco.beyerage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import thefaco.beyerage.domain.Customer;
import thefaco.beyerage.dto.view.CustomerListDto;
import thefaco.beyerage.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 고객의 소리 목록
     * 목록들을 전체적으로 다 보여줌
     */
    @GetMapping("/customer_service")
    public String boardInfo(Model model){
        List<Customer> customers = customerService.findAllText();
        List<CustomerListDto> customerListDtos = customers.stream()
                .map(c -> new CustomerListDto(c.getId(), c.getText(), c.getLike()))
                .collect(Collectors.toList());
        model.addAttribute("customers", customerListDtos);

        log.info("고객의 소리 목록 조회 access");

        return "customer/boardList";
    }

    /**
     * 고객의 소리 삭제
     */
    @GetMapping("/customer_service/{id}/delete")
    public String deleteCustomerVoice(@PathVariable("id") Long id){
        Optional<Customer> findCustomer = customerService.findOneText(id);
        findCustomer.ifPresent(customerService::deleteText);

        log.info("delete customerVoice id={}", id);

        return "redirect:/customer_service";
    }

    /**
     * 좋아요 눌렀을 때 좋아요 수 증가
     */
    @PostMapping("/customer_service/{id}/like")
    public String addLike(@PathVariable("id") Long id){

        customerService.addLike(id);
        log.info("좋아요 수 증가 id={}", id);

        return "redirect:/customer_service";
    }



}
