package com.lm.cvmaker.persistence;

import com.lm.cvmaker.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {
    @Query("SELECT c FROM Cv c WHERE c.id = :id")
    Optional<Cv> findByIdBasic(@Param("id") Long id);
}


