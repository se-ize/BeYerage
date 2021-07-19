package thefaco.beyerage.dto.beverage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import thefaco.beyerage.domain.BottleType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter @Setter
@AllArgsConstructor
public class BeverageUpdateDto {

    private Long id;

    @NotEmpty(message = "음료 이름은 필수입니다")
    private String name;

    @NotNull(message = "음료 가격은 필수입니다")
    @Positive(message = "음료 가격은 양수입니다")
    private int price;

    @NotNull(message = "음료 종류는 필수입니다")
    private BottleType bottleType;

    @NotNull(message = "음료 크기는 필수입니다")
    @Positive(message = "음료 크기는 양수입니다")
    private int size;

    private int row;
    private int column;
}