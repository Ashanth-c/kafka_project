package com.cfa.objects.letter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Integer> {
    List<Letter> getByCreationDate(final Date date);
    List<Letter> getByTreatmentDate(final Date date);
}