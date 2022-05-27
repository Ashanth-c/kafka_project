package com.cfa.objects.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/letter",produces = "application/json;charset=utf-8")
public class LetterController  {
    @Autowired
    public LetterRepository letterRepository;

    @GetMapping("/getAll")
    public List<Letter> getAll() {
        return letterRepository.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Letter> getById(@PathVariable(value = "id") final Integer input) {
        return letterRepository.findById(input);
    }

    @PostMapping("/postLetter")
    public void postLetter(@RequestBody Letter input) {
        letterRepository.save(input);
    }

    public void saveLetter(@RequestBody Letter input) {
        letterRepository.save(input);
    }
}