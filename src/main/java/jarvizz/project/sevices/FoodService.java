package jarvizz.project.sevices;

import jarvizz.project.dao.FoodDao;
import jarvizz.project.models.Food;
import jarvizz.project.models.Type;
import jarvizz.project.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {
    @Autowired
    FoodDao foodDao;
    public List<Food> findAll(){
       return foodDao.findAll();
    }
    public void save(Food food){
        if (food != null){
            foodDao.save(food);
        }
    }

    public List<Food> findAllByType(Type type){
        if (type != null) {
        return foodDao.findAllByType(type);
    }
        else return new ArrayList<Food>();
}
    public Food findById(int id){
        return foodDao.findById(id);
    }

    public void  deleteByid(int id){
         foodDao.deleteById(id);
    }
    public Food  findByName(String name){
        return foodDao.findByName(name);
    }
}
