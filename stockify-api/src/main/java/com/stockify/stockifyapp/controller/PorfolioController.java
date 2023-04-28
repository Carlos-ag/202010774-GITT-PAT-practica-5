package com.stockify.stockifyapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import com.stockify.stockifyapp.model.PortfolioMovement;
import com.stockify.stockifyapp.service.PortfolioService;




@RestController
public class PorfolioController {

    private PortfolioService portfolioService;

    private static final Logger logger = LoggerFactory.getLogger(PorfolioController.class);

    public PorfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }


    @GetMapping("/portfolio/{userID}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<PortfolioMovement>> getPortfolio(@PathVariable("userID") Integer userID) {
        try {
            List<PortfolioMovement> portfolio = portfolioService.getPortfolio(userID);
            return ResponseEntity.ok(portfolio);
        } catch (IllegalArgumentException e) {
            logger.error("Error getting porfolio: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting portfolio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/movement", 
        consumes = MediaType.APPLICATION_JSON_VALUE, 
        produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> addMovement(@RequestBody PortfolioMovement payload) {
        // public void addMovement(@RequestBody Map<String, Object> payload) {
    
            try {
                portfolioService.addPortfolioMovement(payload);
                logger.info("Added movement: " + payload.toString()); 
                return ResponseEntity.ok(payload);
            } catch (Exception e) {
                logger.error("Error adding movement: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
    }

    @PostMapping(path = "/movement/update",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> updateMovement(@RequestBody PortfolioMovement payload) {
        try {
            PortfolioMovement updatedMovement = portfolioService.updatePortfolioMovement(payload);
            logger.info("Updated movement: " + payload.toString());
            return ResponseEntity.ok(updatedMovement);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating movement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error updating movement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/movement/{movementID}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<PortfolioMovement> getMovement(@PathVariable("movementID") Integer movementID) {
        try {
            PortfolioMovement movement = portfolioService.getPortfolioMovement(movementID);
            return ResponseEntity.ok(movement);
        } catch (IllegalArgumentException e) {
            logger.error("Error getting movement: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting movement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            portfolioService.addMovementsFromCSV(file);
            logger.info("File uploaded and processed successfully");
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded and processed successfully");
        } catch (Exception e) {
            logger.error("Failed to upload file: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{userID}")
    @CrossOrigin(origins = "*")
public ResponseEntity<Resource> downloadFile(@PathVariable("userID") Integer userID) {
    try {
        Resource resource = portfolioService.downloadMovementsCSV(userID);
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


@DeleteMapping("/movement/{movementID}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Void> deleteMovement(@PathVariable("movementID") Integer movementID) {
        try {
            portfolioService.deletePortfolioMovement(movementID);
            logger.info("Deleted movement: " + movementID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting movement: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
