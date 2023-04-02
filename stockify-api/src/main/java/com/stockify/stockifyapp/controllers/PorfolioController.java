package com.stockify.stockifyapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.Resource;

import com.stockify.stockifyapp.models.PorfolioMovement;
import com.stockify.stockifyapp.restservices.PortfolioService;




@RestController
public class PorfolioController {

    private static final Logger logger = LoggerFactory.getLogger(PorfolioController.class);

    @Autowired
    private PortfolioService portfolioService = new PortfolioService();

    @GetMapping("/portfolio")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Optional<ArrayList<HashMap<String, Object>>>> getPortfolio() {
        Optional<ArrayList<HashMap<String, Object>>>  json = portfolioService.getPortfolioMap();
        // log message with the portfolio
        logger.info("Portfolio: " + json.toString());
        return new ResponseEntity<Optional<ArrayList<HashMap<String, Object>>>> (json, HttpStatus.OK);
    }

    @PostMapping(path = "/movement", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> addMovement(@RequestBody Map<String, Object> payload) {
        // public void addMovement(@RequestBody Map<String, Object> payload) {
    
            PorfolioMovement movement;
            try {
                movement = portfolioService.addPorfolioMovement(payload);
                logger.info("Added movement: " + movement.toString()); 
                return ResponseEntity.ok(movement);
            } catch (Exception e) {
                logger.error("Error adding movement: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }



    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            portfolioService.processCSVFile(file);
            logger.info("File uploaded and processed successfully");
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded and processed successfully");
        } catch (Exception e) {
            logger.error("Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    @CrossOrigin(origins = "*")
public ResponseEntity<Resource> downloadFile() {
    try {
        Resource resource = portfolioService.loadCSVFileAsResource();
        String contentType = Files.probeContentType(resource.getFile().toPath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        logger.info("File downloaded successfully");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    } catch (FileNotFoundException e) {
        logger.error("File not found: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (IOException e) {
        logger.error("Failed to download file: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}


}
