package com.cfa.objects.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class LetterService {
    @Autowired
    private LetterRepository letterRepository;

    public void saveLetter(Letter input) {
        letterRepository.save(input);
    }
}