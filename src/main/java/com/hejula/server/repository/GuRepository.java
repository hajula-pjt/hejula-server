package com.hejula.server.repository;

import com.hejula.server.entities.Gu;
import com.hejula.server.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface GuRepository extends JpaRepository<Gu, Long> {
    public List<Gu> findByNameContaining(String gu);
}


