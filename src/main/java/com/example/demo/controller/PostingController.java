package com.example.demo.controller;

import com.example.demo.dto.PostingDTO;
import com.example.demo.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/postings")
public class PostingController {
    private final PostingService postingService;

    @Autowired
    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

//    @GetMapping("/init")
//    public ResponseEntity init(){
//        postingService.initTable();
//        return ResponseEntity.ok().build();
//    }

    @GetMapping
    public ResponseEntity<List<PostingDTO>> init() {
        return ResponseEntity.ok(postingService.getAll());
    }
}
