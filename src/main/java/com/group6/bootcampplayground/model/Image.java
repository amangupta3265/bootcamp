package com.group6.bootcampplayground.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "_image")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    private String email;
    @Lob
    private byte[] imageData;


}
