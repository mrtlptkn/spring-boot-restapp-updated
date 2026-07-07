package com.mertalptekin.springbootrestapp.infra.repository;
import com.mertalptekin.springbootrestapp.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    // JPA method naming convention kullanarak kullanıcı adı ile kullanıcıyı bulma metodu
    Optional<User> findByUsername(String username);
}
