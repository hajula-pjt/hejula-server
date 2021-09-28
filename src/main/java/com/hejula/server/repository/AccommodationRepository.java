package com.hejula.server.repository;

import com.hejula.server.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, QueryDslCustom{

    public List<Accommodation> findByAdmin_Id(String adminId);

}