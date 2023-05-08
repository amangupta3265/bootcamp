package com.group6.bootcampplayground.repository;

import com.group6.bootcampplayground.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note,Long> {
    List<Note> findByCreatorEmail(String email);

    List<Note> findByIsSharedTrue();
}
