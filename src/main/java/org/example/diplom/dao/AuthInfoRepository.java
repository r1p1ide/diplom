package org.example.diplom.dao;

import org.example.diplom.model.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

//    @Query(
//            value = "SELECT ai FROM auth_info ai WHERE ai.login=:login",
//            nativeQuery = true)
    List<AuthInfo> findByLogin(String login);

    @Modifying
    @Query(
            value = "Delete * from auth_info where login=:login",
            nativeQuery = true)
    void deleteByLogin(@Param("login") String login);
}
