package thefaco.beyerage.dto.beverage;

import lombok.Getter;
import thefaco.beyerage.domain.Beverage;
import thefaco.beyerage.domain.BottleType;

@Getter
public class BeverageWithLocApiDto {

    private Long id;
    private String name;
    private int price;
    private BottleType type;
    private int size;
    private int row;
    private int column;

    public BeverageWithLocApiDto(Beverage beverage) {
        id = beverage.getId();
        name = beverage.getName();
        price = beverage.getPrice();
        type = beverage.getType();
        size = beverage.getSize();
        row = beverage.getBeverageLocation().getRow();
        column = beverage.getBeverageLocation().getColumn();
    }

}
