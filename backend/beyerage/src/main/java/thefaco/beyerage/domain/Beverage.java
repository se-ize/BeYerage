package thefaco.beyerage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Beverage Entity
 * 음료 상세정보를 담은 엔티티
 */
@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Beverage {

    @Id @GeneratedValue
    @Column(name = "beverage_id")
    private Long id;

    private String name;

    private int price;

    @Enumerated(value = EnumType.STRING)
    private BottleType type;

    private int size;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "beverage_location_id")
    private BeverageLocation beverageLocation;

    /**
     * 연관관계 메서드
     */
    public void setBeverageLocation(BeverageLocation beverageLocation){
        this.beverageLocation = beverageLocation;
        beverageLocation.setBeverage(this);
    }

}
