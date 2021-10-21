package thefaco.beyerage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thefaco.beyerage.domain.Beverage;

import java.util.List;
import java.util.Optional;

public interface BeverageJpaRepository extends JpaRepository<Beverage, Long> {

    @Query("select b from Beverage b join fetch b.beverageLocation where b.id = :id")
    Optional<Beverage> findByIdWithLoc(@Param("id") Long id);

    @Query("select b from Beverage b join fetch b.beverageLocation where b.name = :name")
    Optional<Beverage> findByNameWithLoc(@Param("name") String name);

    @Query("select b from Beverage b join fetch b.beverageLocation")
    List<Beverage> findAllWithLoc();

    @Query("select b from Beverage b join fetch b.beverageLocation where b.frequency in (select max(b.frequency) from Beverage b)")
    Optional<Beverage> findMostFreqWithLoc();

    @Query("select b from Beverage b join fetch b.beverageLocation where b.beverageLocation.row = :row and b.beverageLocation.column = :column")
    Optional<Beverage> findByRowAndColumn(@Param("row") int row, @Param("column") int column);
}
