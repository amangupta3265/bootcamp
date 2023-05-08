package com.group6.bootcampplayground.repository;

import com.group6.bootcampplayground.model.Note;
import com.group6.bootcampplayground.model.NoteAccess;
//import com.group6.bootcampplayground.model.NoteAccessId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteAccessRepository extends JpaRepository<NoteAccess, Long> {
    @Query("select na from NoteAccess na where na.noteId=?1 and na.accessEmail=?2")
    Optional<NoteAccess> accessgiven(Long noteId, String email);

    List<NoteAccess> findByAccessEmail(String email);
}
