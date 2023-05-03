package com.sms.service;


import com.sms.config.SMSProps;
import com.sms.constants.SMSConstants;
import com.sms.model.Contact;
import com.sms.repository.ContactRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final SMSProps smsProps;
    public ContactService(ContactRepository contactRepository, SMSProps smsProps) {
        this.contactRepository = contactRepository;
        this.smsProps = smsProps;
    }


    /**
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(SMSConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(null != savedContact && savedContact.getContactId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum,String sortField, String sortDir){
        int pageSize = smsProps.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        return contactRepository.findByStatusWithQuery(
                SMSConstants.OPEN,pageable);
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        /*Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(EazySchoolConstants.CLOSE);
        });
        Contact updatedContact = contactRepository.save(contact.get());
        */
        int rows = contactRepository.updateMsgStatus(SMSConstants.CLOSE, contactId);
        if(rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }

}
