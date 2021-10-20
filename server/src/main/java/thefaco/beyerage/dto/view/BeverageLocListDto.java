package thefaco.beyerage.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import thefaco.beyerage.domain.BeverageLocation;

@Getter @Setter
@AllArgsConstructor
public class BeverageLocListDto {

    private Long id;
    private String name;
    private BeverageLocation beverageLocation;

}
