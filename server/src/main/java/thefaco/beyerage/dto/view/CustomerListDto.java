package thefaco.beyerage.dto.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CustomerListDto {

    private Long id;
    private String text;
    private int like;
}
