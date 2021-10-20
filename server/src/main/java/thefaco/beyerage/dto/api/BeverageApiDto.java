package thefaco.beyerage.dto.api;

import lombok.Getter;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BottleType;

@Getter
public class BeverageApiDto {

    private Long id;
    private String name;
    private int price;
    private BottleType type;
    private int size;

    public BeverageApiDto(Beverage beverage) {
        id = beverage.getId();
        name = beverage.getName();
        price = beverage.getPrice();
        type = beverage.getType();
        size = beverage.getSize();
    }
}
