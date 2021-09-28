package com.hejula.server.repository;

import com.hejula.server.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jooyeon
 * @since 2021.07.17
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, QueryDslCustom{
    Page<Review> findByAccommodation_accommodationSeqAndCommentContaining(long accommodationSeq, String comment, Pageable pageable);
}