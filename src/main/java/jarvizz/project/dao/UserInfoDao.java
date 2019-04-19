package jarvizz.project.dao;

import jarvizz.project.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo,Integer> {
}
