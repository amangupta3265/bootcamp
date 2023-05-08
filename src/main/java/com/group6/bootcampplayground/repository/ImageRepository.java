package com.group6.bootcampplayground.repository;

import com.group6.bootcampplayground.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {

}
