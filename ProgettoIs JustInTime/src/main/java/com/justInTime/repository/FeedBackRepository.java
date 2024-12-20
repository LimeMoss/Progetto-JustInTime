package com.justInTime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justInTime.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long>{
    List<Feedback> findAll();
}
