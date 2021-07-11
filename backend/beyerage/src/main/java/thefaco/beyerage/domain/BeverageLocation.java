package thefaco.beyerage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeverageLocation {

    @Id @GeneratedValue
    @Column(name = "beverage_location_id")
    private Long id;

    private String name;

    @Column(name = "beverage_row")
    private int row;

    @Column(name = "beverage_column")
    private int column;

    @OneToOne(mappedBy = "beverageLocation")
    private Beverage beverage;

}
