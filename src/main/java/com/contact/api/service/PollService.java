package com.contact.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.api.repository.UserRepository;

@Service
public class PollService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(PollService.class);
}
