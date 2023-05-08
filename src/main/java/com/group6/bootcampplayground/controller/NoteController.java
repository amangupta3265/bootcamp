package com.group6.bootcampplayground.controller;

import com.group6.bootcampplayground.auth.JWTService;
import com.group6.bootcampplayground.model.Note;
import com.group6.bootcampplayground.model.NoteAccess;
import com.group6.bootcampplayground.model.request.NotePinRequest;
import com.group6.bootcampplayground.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {

    @Autowired
    private NoteService notesService;
    private final JWTService jwtService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String createNote(@RequestBody Note newNote,@RequestHeader("Authorization") String header) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        newNote.setCreatorEmail(email);
        notesService.addNote(newNote);
        return "Added";
    }

    @GetMapping("/getpublishednotes")
    public ResponseEntity<List<Note>> getAllSharedNotes() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(notesService.getAllSharedNotes());
    }

    @GetMapping("/getmynotes")
    public ResponseEntity<List<Note>> getMyNotes(@RequestHeader("Authorization") String header) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(notesService.getMyNotes(email));
    }

    @GetMapping("/sharedwithme")
    public ResponseEntity sharedWithMw(@RequestHeader("Authorization") String header)throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        List<Note> noteList = notesService.sharedWithMe(email);
        if(noteList.isEmpty()) return ResponseEntity.badRequest().body("No notes shared with you");
        else return ResponseEntity.ok(noteList);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@RequestHeader("Authorization") String header,@PathVariable long id){
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        boolean deleted = notesService.deleteById(email,id);
        if(deleted) return ResponseEntity.ok("Deleted");
        return ResponseEntity.badRequest().body("Note id "+id+" doesn't belong to the requested user.");

    }

    @PutMapping("/upvote/{id}")
    public ResponseEntity upVote(@PathVariable long id){
        boolean upvote = notesService.upVote(id);
        if(upvote)return ResponseEntity.ok("UpVoted Successfully");
        return ResponseEntity.ok("Note with id: "+id+" not shared");
//        Currently same user can upvote multiple times, and downvote must remove his upvote
//        IsProtected to be used for pin authorisation
    }

    @PutMapping("/downvote/{id}")
    public ResponseEntity downVote(@PathVariable long id){
        boolean downvote = notesService.downVote(id);
        if(downvote)return ResponseEntity.ok("DownVoted Successfully");
        return ResponseEntity.ok("Note with id: "+id+" not shared");
    }


    @PutMapping("/setpin/{id}")
    public ResponseEntity setPin(@RequestHeader("Authorization") String header, @PathVariable long id, @RequestBody NotePinRequest pinRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String notePin = pinRequest.getNotePin();
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String response = notesService.setPin(email, id, notePin);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/deletepin/{id}")
    public ResponseEntity deletePin(@RequestHeader("Authorization") String header,@PathVariable long id,@RequestBody NotePinRequest pinRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String notePin = pinRequest.getNotePin();
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String response = notesService.deletePin(email, id, notePin);
        return ResponseEntity.ok(response);

    }
    @PutMapping("/changepin/{id}")
    public ResponseEntity changePin(@RequestHeader("Authorization") String header,@PathVariable long id,@RequestBody NotePinRequest pinRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String notePin = pinRequest.getNotePin();
        String newPin = pinRequest.getNewPin();
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String response = notesService.changePin(email, id, notePin, newPin);
        return ResponseEntity.ok(response);
    }

    @PutMapping("forgotpin/{id}")
    public ResponseEntity forgotPin(@RequestHeader("Authorization") String header,@PathVariable long id) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String response = notesService.forgotPin(email,id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("resetpin/{id}")
    public ResponseEntity resetPin(@RequestHeader("Authorization") String header,@PathVariable long id,@RequestBody NotePinRequest pinRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String otp = pinRequest.getOtp();
        String newPin = pinRequest.getNewPin();
        if(notesService.checkOtpExpiry(id)){
            String response = notesService.resetPin(email,id,otp,newPin);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.ok("OTP Expired, Please resend the New OTP");
        }

    }

    @PostMapping("sendnoteonemail/{id}")
    public ResponseEntity sendNoteOnEmail(@RequestHeader("Authorization") String header,@PathVariable long id,@RequestBody NotePinRequest sendNoteRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String toEmail = sendNoteRequest.getToEmail();
        String response = notesService.sendNoteOnEmail(email,toEmail,id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("giveaccess")
    public ResponseEntity giveAccess(@RequestHeader("Authorization") String header, @RequestBody NoteAccess noteAccess){
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String response = notesService.noteShareWith(email,noteAccess);
        return ResponseEntity.ok(response);
    }

    @PutMapping("removeaccess")
    public ResponseEntity removeAccess(@RequestHeader("Authorization") String header, @RequestBody NoteAccess noteAccess){
        String token = header.substring(7);
        String email = jwtService.extractUsername(token);
        String response = notesService.removeAccess(email,noteAccess);
        return ResponseEntity.ok(response);
    }



}
