package com.stockify.stockifyapp.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stockify.stockifyapp.model.PortfolioMovement;
// import com.stockify.stockifyapp.model.PortfolioManager;
import com.stockify.stockifyapp.repository.PortfolioRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;




@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    public PortfolioService() {
    }

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    // private PortfolioManager portfolioManager;

    // public Optional<ArrayList<HashMap<String, Object>>> getPortfolioMap() {
    //     ArrayList<HashMap<String, Object>> map = portfolioManager.getPortfolioMap();
    //     return Optional.ofNullable(map.size() == 0 ? null : map);
    // }


    public List<PortfolioMovement> getPortfolio(Integer userId) {
        return portfolioRepository.findByUserId(userId);
    }

    public void addPortfolioMovement(PortfolioMovement movement) {
        try {
            checkIfPayloadIsValid(movement);
            portfolioRepository.save(movement);
            

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unexpected error adding movement: " + e.getMessage());
        }
    }



    public void checkIfPayloadIsValid(PortfolioMovement movement) throws ParseException {
        if (movement == null) {
            throw new IllegalArgumentException("Movement is null");
        }
    
        if (movement.getTicker() == null) {
            throw new IllegalArgumentException("Movement ticker is null");
        }
    
        if (movement.getQuantity() <= 0) {
            throw new IllegalArgumentException("Movement quantity is not > 0");
        }
    
        if (movement.getPrice() <= 0) {
            throw new IllegalArgumentException("Movement price is not > 0");
        }
    
        if (movement.getDate() == null) {
            throw new IllegalArgumentException("Movement date is null");
        }
    
        if (movement.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Movement date is in the future");
        }
    }
    



    // public void processCSVFile(MultipartFile file) throws IOException {
    //     // Define the path to the existing file that you want to replace
    //     Path targetFileLocation = Paths.get("data/portfolioMovements.csv").toAbsolutePath().normalize();
    
    //     // Make a backup of the existing file
    //     Path backupFileLocation = Paths.get("data/portfolioMovements_backup.csv").toAbsolutePath().normalize();
    //     Files.copy(targetFileLocation, backupFileLocation, StandardCopyOption.REPLACE_EXISTING);
    
    //     // Replace the contents of the existing file with the received file
    //     try {
    //         Files.copy(file.getInputStream(), targetFileLocation, StandardCopyOption.REPLACE_EXISTING);
    //         this.portfolioManager = new PortfolioManager();
    
    //         // Delete the file if there were no errors
    //         Files.deleteIfExists(backupFileLocation);
    //     } catch (Exception e) {
    //         // Restore the previous file in case an exception occurs
    //         Files.copy(backupFileLocation, targetFileLocation, StandardCopyOption.REPLACE_EXISTING);
    //         throw new IOException("\"Probablemente el archivo CSV no tiene el formato correcto, por favor revisa que siga el formato siguiente: \"UUID\",\"Ticker\",\"Cantidad\",\"Precio compra\",\"AAAA-MM-DD\"");
    //     }
    // }

    @Transactional
    public void addMovementsFromCSV(MultipartFile file) throws IOException {
        // create a temporary file from the input stream
        Path tempFile = Files.createTempFile("temp", null);
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        // read the file, line by line and add the movements
        List<String> lines = Files.readAllLines(tempFile);
        List<PortfolioMovement> movements = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) { // start at index 1 to skip header line
            String line = lines.get(i);
            String[] values = line.split(",");
            PortfolioMovement movement = new PortfolioMovement();
            movement.setUserId(Integer.parseInt(values[5]));
            movement.setTicker(values[1]);
            movement.setQuantity(Integer.parseInt(values[2]));
            movement.setPrice(Double.parseDouble(values[3]));
            movement.setDate(LocalDate.parse(values[4]));
            movements.add(movement);
        }

        // Save all movements in a transaction
        portfolioRepository.saveAllMovements(movements);

        // delete the temporary file
        Files.deleteIfExists(tempFile);
    }
    

    public Resource downloadMovementsCSV(Integer userID) throws IOException {
        List<PortfolioMovement> movements = getPortfolio(userID);
    
        // Create a temporary CSV file
        Path tempFile = Files.createTempFile("movements-", ".csv");
        File csvFile = tempFile.toFile();
    
        // Write header and data to the CSV file
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write header
            writer.write("ID,TICKER,QUANTITY,PRICE,DATE,USER_ID\n");
    
            // Write data
            for (PortfolioMovement movement : movements) {
                writer.write(String.format(Locale.US, "%d,%s,%d,%.2f,%s,%d%n",
                        movement.getId(),
                        movement.getTicker(),
                        movement.getQuantity(),
                        movement.getPrice(),
                        movement.getDate().toString(),
                        movement.getUserId()));
            }
        }
    
        // Return the file as a Resource
        return new FileSystemResource(csvFile);
    }
    
    
    
    // public Resource loadCSVFileAsResource() throws IOException {
    //     Path filePath = Paths.get("data/portfolioMovements.csv").toAbsolutePath().normalize();
    //     Resource resource = new UrlResource(filePath.toUri());
    
    //     if (resource.exists()) {
    //         return resource;
    //     } else {
    //         throw new FileNotFoundException("File not found: " + filePath);
    //     }
    // }

}
