package com.group6.bootcampplayground.service;


import com.group6.bootcampplayground.model.Note;
import com.group6.bootcampplayground.model.NoteAccess;
import com.group6.bootcampplayground.repository.NoteAccessRepository;
import com.group6.bootcampplayground.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sound.midi.Receiver;
import javax.xml.crypto.Data;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;


@Service
public class NoteService {

    @Autowired
    private NotesRepository noteRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NoteAccessRepository noteAccessRepo;


    public void addNote(Note newNote) {
        noteRepo.save(newNote);
    }

    public List<Note> getMyNotes(String email) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Note> listOfNotes = noteRepo.findByCreatorEmail(email);
        return listOfNotes;
    }

    public List<Note> getAllSharedNotes() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        List<Note> listOfNotes =  noteRepo.findByIsSharedTrue();
        return listOfNotes;
    }

    public boolean deleteById(String email, long id) {
        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){
            noteRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean upVote(long id) {
        Note note = noteRepo.getReferenceById(id);
        if(note.isShared()){
            int upVoteCount = note.getUpVote();
            note.setUpVote(upVoteCount+1);
            if(note.getUpVote()%9==0)
            {
                note.setUpVote(upVoteCount+2);//  Bonus vote
            }
            noteRepo.save(note);
            return true;
        }
        return false;
    }

    public boolean downVote(long id) {
        Note note = noteRepo.getReferenceById(id);
        if(note.isShared()){
            int downVoteCount = note.getDownVote();
            note.setDownVote(downVoteCount+1);
            noteRepo.save(note);
            return true;
        }
        return false;
    }

    public String setPin(String email, long id, String notePin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){
            if(!note.isProtected())
            {
                note.setProtected(true);
                note.setNotePin(notePin);
                noteRepo.save(note);
                return "Pin set successfully for note id: "+id;
            }
            return "Pin already set for note: " +id;
        }
        return "Access Denied: Note "+id+" doesn't belong to "+ email;
    }

    public String deletePin(String email, long id, String notePin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){
            if(note.isProtected())
            {
                if(note.getNotePin().equals(notePin))
                {
                    note.setProtected(false);
                    note.setNotePin(null);
                    noteRepo.save(note);
                    return "Pin Deleted";
                }
              return "Wrong Pin";
            }
            return "Pin is not set";
        }
        return "Access Denied: Note "+id+" doesn't belong to "+ email;
    }

    public String changePin(String email, long id, String notePin, String newPin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){
            if(note.isProtected())
            {
                if(note.getNotePin().equals(notePin))
                {
                    note.setNotePin(newPin);
                    noteRepo.save(note);
                    return "Pin changed successfully";
                }
                return "Wrong Pin";
            }
            return "Pin is not set";
        }
        return "Access Denied: Note "+id+" doesn't belong to "+ email;
    }

    public Boolean sendMail(String toEmail,String subject,String body ){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ishandube@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        return true;
    }

    public String getOTP(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }


    public String forgotPin(String email, long id) {

        Date date = new Date();
        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){
            if(note.isProtected())
            {
                String otp = getOTP();
                String msg = "Please use the OTP: \""+otp+ "\" to reset pin for your note "+id;
                String sub = "PIN Reset OTP";
                note.setOneTimePassword(otp);
                note.setOtpRequestedTime(date.getTime());
                Boolean success = sendMail(email,sub,msg);
                if(success){
                    noteRepo.save(note);
                    return "OTP Sent successfully!!";
                }else{
                    return "Sending OTP Failed, Please resend OTP";
                }
            }
            return "Pin is not set";
        }
        return "Access Denied: Note "+id+" doesn't belong to "+ email;
    }

    public boolean checkOtpExpiry(long id) {
        final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes
        Note note = noteRepo.getReferenceById(id);
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = note.getOtpRequestedTime();
        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            return false;
        }
        return true;
    }


    public String resetPin(String email, long id, String otp, String newPin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){
            if(note.isProtected())
            {
                if(note.getOneTimePassword().equals(otp))
                {
                    note.setNotePin(newPin);
                    noteRepo.save(note);
                    return "New Pin set successfully";
                }
                return "Wrong OTP";
            }
            return "Pin is not set";
        }
        return "Access Denied: Note "+id+" doesn't belong to "+ email;
    }

    public String sendNoteOnEmail(String email, String toEmail, long id) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        Note note = noteRepo.getReferenceById(id);
        if(note.getCreatorEmail().equals(email)){

            String msg = note.getNoteTitle()+".......BY "+email+"\n\n"+ note.getNoteContent();
            String sub = "Note: "+id+" "+note.getNoteTitle()+".......BY "+email;
            Boolean success = sendMail(toEmail,sub,msg);
            if(success){
                return "Note send successfully on the mail: "+ toEmail;
            }else{
                return "Failed to send the mail";
            }
        }
        return "Access Denied: Note "+id+" doesn't belong to "+ email + "you cant send the note";
    }


    public String noteShareWith(String email, NoteAccess noteAccess) {
        Note note = noteRepo.getReferenceById(noteAccess.getNoteId());
        if(note.getCreatorEmail().equals(email)) {
            Optional<NoteAccess> exists = noteAccessRepo.accessgiven(noteAccess.getNoteId(),noteAccess.getAccessEmail());
            if(!exists.isPresent())
            {
                noteAccessRepo.save(noteAccess);
                return "Access set successfulluy";
            }
            return "Access already given to this id";
        }
        return "Note doesn't belong to the user";
    }

    public String removeAccess(String email, NoteAccess noteAccess) {
        Note note = noteRepo.getReferenceById(noteAccess.getNoteId());
        if(note.getCreatorEmail().equals(email)) {
            Optional<NoteAccess> exists = noteAccessRepo.accessgiven(noteAccess.getNoteId(),noteAccess.getAccessEmail());
            if(exists.isPresent())
            {
                noteAccessRepo.deleteById(exists.get().getId());
                return "Access remove successfulluy";
            }
            return "Access not given to this email: "+noteAccess.getAccessEmail();
        }
        return "Note doesn't belong to the user";
    }

    public List<Note> sharedWithMe(String email) {
        ArrayList<Long> noteIds = new ArrayList<>();
        List<NoteAccess> noteAccesses = noteAccessRepo.findByAccessEmail(email);
            for (NoteAccess na:noteAccesses
            ) {
                noteIds.add(na.getNoteId());

            }
        List<Note> returnNotes = new ArrayList<>();
        for (Long noteId:noteIds
             ) {returnNotes.add(noteRepo.findById(noteId).get());}
        return returnNotes;
    }

//    public boolean checkNoteExist(long id) {
//        Note note = noteRepo.getReferenceById(id);
//        if(note==null)
//        {}
//    }


}
