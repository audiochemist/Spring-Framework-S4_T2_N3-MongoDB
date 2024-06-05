package cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.services;

import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.exceptions.EmptyFruitException;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.exceptions.FruitNoFound;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.domain.Fruit;
import cat.itacademy.barcelonactiva.medina.german.s04.t02.n03.model.repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FruitService implements FruitServiceInterface {

    @Autowired
    private FruitRepository fruitRepository;

    @Override
    public Fruit createFruit(Fruit fruit) throws EmptyFruitException, FruitNoFound {
        if (fruit.getName().isEmpty() || fruit.getQuantity() <= 0) {
            throw new EmptyFruitException("Invalid Fruit - Empty");
        } else {
            return fruitRepository.save(fruit);
        }
    }

    @Override
    public void deleteFruit(int id) throws FruitNoFound {
        Optional<Fruit> optionalFruit = fruitRepository.findById(id);
        if (optionalFruit.isEmpty()){
            throw new FruitNoFound("The fruit was not found in the database");
        }else{
            fruitRepository.deleteById(id);
        }
    }

    @Override
    public Fruit getOneFruitById(int id) throws FruitNoFound {
        return fruitRepository.findById(id).orElse(null);
    }

    @Override
    public List<Fruit> getAllFruits() throws EmptyFruitException {
        return fruitRepository.findAll();
    }

}
