package com.club.repositories;

import com.club.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Customer, String> {

    Customer findByUsername(String nickname);

}
