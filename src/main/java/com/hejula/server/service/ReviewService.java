package com.hejula.server.service;

import com.hejula.server.dto.ReviewSearchDto;
import com.hejula.server.entities.Review;
import com.hejula.server.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author jooyeon
 * @since 2021.08.14
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Page<Review> getReviewList(ReviewSearchDto reviewSearchDto){
        return reviewRepository.findByAccommodation_accommodationSeqAndCommentContaining(
                reviewSearchDto.getAccommodationSeq(),
                reviewSearchDto.getKeyword(),
                PageRequest.of(reviewSearchDto.getPageNo(), reviewSearchDto.getRows()));
    }
}
