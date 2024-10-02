package net.Anshul.journalApp.service;

import net.Anshul.journalApp.entity.JournalEntry;
import net.Anshul.journalApp.entity.User;
import net.Anshul.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

@Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
    try {
        User user = userService.findByUsername(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }
    catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException("an error occured while saving the entry",e);
    }
    }

    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId myId, String userName) {
        User user=userService.findByUsername(userName);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(myId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(myId);
    }
}
