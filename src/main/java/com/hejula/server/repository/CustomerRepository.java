package com.hejula.server.repository;

import com.hejula.server.entities.Customer;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
        public List<Customer> findById(String id);
        public boolean existsById(String id);
        public Customer findByIdAndPassword(String id, String password);
}
