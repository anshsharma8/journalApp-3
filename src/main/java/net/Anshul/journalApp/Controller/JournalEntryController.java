package net.Anshul.journalApp.Controller;

import net.Anshul.journalApp.entity.JournalEntry;

import net.Anshul.journalApp.entity.User;
import net.Anshul.journalApp.service.JournalEntryService;
import net.Anshul.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

@Autowired
private JournalEntryService journalEntryService;
@Autowired
private UserService userService;



@GetMapping("/{username}")
public ResponseEntity <?> getAllJournalEntriesOfUser(@PathVariable String username)
 {
     User user=userService.findByUsername(username);
     List<JournalEntry> all = user.getJournalEntries();
    return new ResponseEntity<>(all, HttpStatus.OK);
}

@PostMapping("/{userName}")
    public ResponseEntity<?>CreateEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {
 try {

     myEntry.setDate(LocalDateTime.now());
    journalEntryService.saveEntry(myEntry,userName);
        return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
     }
    catch(Exception e){
     return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}



@GetMapping("id/{myId}")
public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {

    Optional<JournalEntry> journalEntry= journalEntryService.findById(myId);
    if(journalEntry.isPresent()) {
        return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


@DeleteMapping("id/{userName}/{myId}")
public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId,@PathVariable String userName) {
    journalEntryService.deleteById(myId,userName);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}


@PutMapping("id/{userName}/{myId}")
public ResponseEntity<?> updateEntryById(@PathVariable ObjectId myId    ,@RequestBody JournalEntry newEntry,@PathVariable String userName) {
    JournalEntry old = journalEntryService.findById(myId).orElse(null);
    if (old != null) {
        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
        old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());

        journalEntryService.saveEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
    }


    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
