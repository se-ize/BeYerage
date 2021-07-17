package thefaco.beyerage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * BeverageLocation Entity
 * 음료 위치정보를 담은 엔티티
 */
@Entity
@Getter @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeverageLocation {

    @Id @GeneratedValue
    @Column(name = "beverage_location_id")
    private Long id;

    @Column(name = "beverage_row")
    private int row;

    @Column(name = "beverage_column")
    private int column;

    @OneToOne(mappedBy = "beverageLocation")
    private Beverage beverage;

    public static BeverageLocation createBeverageLocation(int row, int column){
        BeverageLocation beverageLocation = new BeverageLocation();
        beverageLocation.setRow(row);
        beverageLocation.setColumn(column);

        return beverageLocation;
    }
}
