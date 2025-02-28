package com.lm.cvmaker.persistance;

import com.lm.cvmaker.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvRepository extends JpaRepository<CV, Long> {
    List<CV> findByUserId(Long UserId);
}
