package thefaco.beyerage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import thefaco.beyerage.domain.Beverage;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Long> {

    @Query("select b from Beverage b join fetch b.beverageLocation where b.id = :id")
    Optional<Beverage> findByIdWithLoc(@Param("id") Long id);

    @Query("select b from Beverage b join fetch b.beverageLocation where b.name = :name")
    Optional<Beverage> findByNameWithLoc(@Param("name") String name);

    @Query("select b from Beverage b join fetch b.beverageLocation order by b.beverageLocation.row asc, b.beverageLocation.column asc")
    List<Beverage> findAllWithLoc();

    @Query("select b from Beverage b join fetch b.beverageLocation where b.frequency in (select max(b.frequency) from Beverage b)")
    Optional<Beverage> findMostFreqWithLoc();

    @Query("select b from Beverage b join fetch b.beverageLocation where b.beverageLocation.row = :row and b.beverageLocation.column = :column")
    Optional<Beverage> findByRowAndColumn(@Param("row") int row, @Param("column") int column);
}
