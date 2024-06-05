package cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.repository;

import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.domain.*;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.domain.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Integer> {
    List<Fruit> findByNameContaining(String name);
}
