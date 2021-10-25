package thefaco.beyerage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Customer;
import thefaco.beyerage.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Long addText(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }

    public Optional<Customer> findOneText(Long id){
        return customerRepository.findById(id);
    }

    public List<Customer> findAllText(){
        return customerRepository.findAllOrderByCreatedDate();
    }

    @Transactional
    public void deleteText(Customer customer){
        customerRepository.delete(customer);
    }

    @Transactional
    public void addLike(Long id){
        Optional<Customer> findCustomer = customerRepository.findById(id);
        findCustomer.ifPresent(Customer::addLike);
    }
}
