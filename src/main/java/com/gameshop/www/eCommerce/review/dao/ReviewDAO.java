package com.gameshop.www.eCommerce.review.dao;

import com.gameshop.www.eCommerce.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, UUID> {
}
