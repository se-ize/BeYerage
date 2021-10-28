package thefaco.beyerage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String text;

    /**
     * 고객의 소리 생성 메서드
     */
    public static Customer createCustomer(String text){
        Customer customer = new Customer();
        customer.setText(text);
        customer.setCreatedDate(LocalDateTime.now());
        customer.setLastModifiedDate(LocalDateTime.now());
        return customer;
    }

    /**
     * 고객의 소리 수정 메서드
     */
    public void updateCustomer(String text){
        this.setText(text);
        this.setLastModifiedDate(LocalDateTime.now());
    }
}
