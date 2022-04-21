package org.example.diplom.dao;

import org.example.diplom.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    List<UserInfo> findByLogin(String login);

    @Modifying
    @Query(
            value = "Insert into user_info (login, first_name, last_name, email, phone, role) values " +
                    "(:login, :firstName, :lastName, :email, :phone, :role)",
            nativeQuery = true)
    Optional<UserInfo> addUser(@Param("login") String login,
                               @Param("firstName") String firstName,
                               @Param("lastName") String lastName,
                               @Param("email") String email,
                               @Param("phone") String phone,
                               @Param("role") Integer role);
}
