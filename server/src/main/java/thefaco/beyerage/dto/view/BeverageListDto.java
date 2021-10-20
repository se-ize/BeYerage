package thefaco.beyerage.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import thefaco.beyerage.domain.BeverageLocation;
import thefaco.beyerage.domain.BottleType;

@Getter @Setter
@AllArgsConstructor
public class BeverageListDto {

    private Long id;
    private String name;
    private int price;
    private BottleType type;
    private int size;
    private BeverageLocation beverageLocation;
}
