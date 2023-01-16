package com.vaii_semestralka.users;

import com.vaii_semestralka.tip.TipEntity;
import com.vaii_semestralka.tipping_all.TippingAllEntity;
import com.vaii_semestralka.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Modifying
    @Query(value = "update users set credit = :credit where email = :email", nativeQuery = true)
    void updateUserCredit(double credit, String email);
}
