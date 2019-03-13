package jarvizz.project.sevices;

import jarvizz.project.dao.UserDao;
import jarvizz.project.models.AccountCredentials;
import jarvizz.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    UserDao userdao;


    public User findByName (String username){
        return userdao.findByUsername(username);
    }

    public void  save(User user){
        userdao.save(user);
    }

}
