package com.group6.bootcampplayground.model;

//import com.group6.bootcampplayground.Security.PIIAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Note {

    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private String noteTitle;
    private int upVote;
    private int downVote;
    private String creatorEmail;
    private String noteUrl;
    private String noteContent;
    private String notePin;
    private boolean isProtected;
    private boolean isShared;
    private String oneTimePassword;
    private Long otpRequestedTime;







}
