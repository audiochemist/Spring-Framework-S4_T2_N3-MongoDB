package cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.controller;

import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.exceptions.EmptyFruitException;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.exceptions.FruitNoFound;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.domain.Fruit;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.services.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("fruits")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @PostMapping("/add")
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit) {
        try {
            Fruit newFruit = fruitService.createFruit(fruit);
            return new ResponseEntity<>(newFruit, HttpStatus.CREATED);
        } catch (EmptyFruitException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (FruitNoFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable int id, @RequestBody Fruit fruit) {
        Fruit thisFruit = null;
        try {
            thisFruit = fruitService.getOneFruitById(id);
        } catch (FruitNoFound e) {
            throw new RuntimeException(e);
        }

        thisFruit.setName(fruit.getName());
        thisFruit.setQuantity(fruit.getQuantity());

        Fruit updatedFruit = null;
        try {
            updatedFruit = fruitService.createFruit(thisFruit);
        } catch (EmptyFruitException | FruitNoFound e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(updatedFruit, HttpStatus.ACCEPTED);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFruit(@PathVariable int id) {
        try {
            fruitService.deleteFruit(id);
        } catch (FruitNoFound e) {
            throw new RuntimeException(e);
        }

        HashMap<String, Boolean> fruitDeletedState = new HashMap<String, Boolean>();
        fruitDeletedState.put("Deleted", true);

        return ResponseEntity.ok(fruitDeletedState);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Fruit> getOneFruitByID(@PathVariable int id) {
        Fruit thisFruit = null;
        try {
            thisFruit = fruitService.getOneFruitById(id);
        } catch (FruitNoFound e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(thisFruit);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Fruit>> getAllFruits() {
        try {
            return ResponseEntity.ok(fruitService.getAllFruits());
        } catch (EmptyFruitException e) {
            throw new RuntimeException(e);
        }
    }

}
