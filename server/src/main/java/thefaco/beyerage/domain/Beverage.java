package thefaco.beyerage.domain;

import lombok.*;

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

    private Long frequency;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "beverage_location_id")
    private BeverageLocation beverageLocation;

    /**
     * 연관관계 메서드
     */
    public void setBeverageLocation(BeverageLocation beverageLocation){
        if(this.beverageLocation != null){
            this.beverageLocation = null;
        }
        this.beverageLocation = beverageLocation;
        beverageLocation.setBeverage(this);
    }

    /**
     * 음료 생성 메서드
     */
    public static Beverage createBeverage(String name, int price, BottleType type, int size, Long frequency, BeverageLocation beverageLocation){

        Beverage beverage = new Beverage();
        beverage.setName(name);
        beverage.setPrice(price);
        beverage.setType(type);
        beverage.setSize(size);
        beverage.setFrequency(frequency);
        beverage.setBeverageLocation(beverageLocation);

        return beverage;
    }

    /**
     * 음료 수정 메서드
     */
    public void updateBeverage(Beverage prevBeverage, String name, int price, BottleType type, int size, int row, int column){
        prevBeverage.setName(name);
        prevBeverage.setPrice(price);
        prevBeverage.setType(type);
        prevBeverage.setSize(size);
        prevBeverage.getBeverageLocation().setRow(row);
        prevBeverage.getBeverageLocation().setColumn(column);
    }

    /**
     * 모바일 앱에서 음료 검색시 Frequency 애트리뷰트를 1 증가해주는 메서드
     */
    public void addFrequency(Beverage prevBeverage, Long frequency){
        prevBeverage.setFrequency(frequency + 1);
    }
}
