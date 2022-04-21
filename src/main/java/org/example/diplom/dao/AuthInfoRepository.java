package org.example.diplom.dao;

import org.example.diplom.model.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    List<AuthInfo> findByLogin(String login);
}
