package thefaco.beyerage.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import thefaco.beyerage.domain.Customer;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired CustomerRepository customerRepository;
    @Autowired EntityManager em;

    private Customer customer;

    @BeforeEach
    void beforeEach(){
        customer = Customer.createCustomer("A매장의 음료 위치가 설명과 다릅니다.");
    }

    @Test
    @DisplayName("고객의 소리 저장 테스트")
    void save(){
        //given
        Customer savedCustomer = customerRepository.save(customer);
        em.flush();
        em.clear();
        //when
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomer.getId());
        //then
        assertThat(findCustomer.get().getId()).isEqualTo(savedCustomer.getId());
    }

    @Test
    @DisplayName("고객의 소리 조회 테스트")
    void findAllOrderByCreatedDate(){
        //given
        Customer customerA = Customer.createCustomer("A");
        Customer customerB = Customer.createCustomer("B");
        Customer customerC = Customer.createCustomer("C");
        customerRepository.save(customerA);
        em.flush();
        customerRepository.save(customerB);
        em.flush();
        customerRepository.save(customerC);
        em.flush();
        em.clear();
        //when
        List<Customer> findCustomers = customerRepository.findAllOrderByCreatedDate();
        //then
        assertThat(findCustomers.size()).isEqualTo(5);
        assertThat(findCustomers.get(0)).isInstanceOf(customerA.getClass());
    }

    @Test
    @DisplayName("고객의 소리 수정 테스트")
    void update(){
        //given
        Customer savedCustomer = customerRepository.save(customer);
        em.flush();
        em.clear();
        //when
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomer.getId());
        findCustomer.get().updateCustomer("수정된 텍스트");
        em.flush();
        em.clear();
        //then
        Optional<Customer> findCustomer2 = customerRepository.findById(savedCustomer.getId());
        assertThat(findCustomer.get().getText()).isEqualTo(findCustomer2.get().getText());
    }

    @Test
    @DisplayName("고객의 소리 삭제 테스트")
    void delete(){
        //given
        Customer savedCustomer = customerRepository.save(customer);
        em.flush();
        em.clear();
        //when
        Optional<Customer> findCustomer = customerRepository.findById(savedCustomer.getId());
        customerRepository.delete(findCustomer.get());
        em.flush();
        em.clear();
        //then
        assertThat(customerRepository.findById(savedCustomer.getId())).isEmpty();
    }
}