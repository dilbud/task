package com.dbx.task.service;

import com.dbx.task.model.Event;
import com.dbx.task.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }
}
