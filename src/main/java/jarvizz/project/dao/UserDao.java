package jarvizz.project.dao;

import jarvizz.project.models.AccountCredentials;
import jarvizz.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

   User findByEmail(String email);
   User findByUsername(String username);
}

