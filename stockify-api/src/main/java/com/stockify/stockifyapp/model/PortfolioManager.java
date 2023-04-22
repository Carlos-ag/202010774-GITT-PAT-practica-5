// package com.stockify.stockifyapp.model;

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.UUID;

// import com.stockify.stockifyapp.common.Converter;

// import java.text.DecimalFormat;
// import java.text.DecimalFormatSymbols;


// public class PortfolioManager {
//     public static String CSV_FILE = "data/portfolioMovements.csv";

//     ArrayList<PortfolioMovement> movements;
    
//     public PortfolioManager(ArrayList<PortfolioMovement> movements) {
//         this.movements = movements;
//     }

//     public PortfolioManager()  {
//         ArrayList<PortfolioMovement> movements = getPortfolioMovementsFromCSV();
//         this.movements = movements;
//     }


// private ArrayList<PortfolioMovement> getPortfolioMovementsFromCSV() {
//     ArrayList<PortfolioMovement> movements = new ArrayList<>();
//     String csvFile = CSV_FILE;
//     String line;
//     String csvSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

//     try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//         while ((line = br.readLine()) != null) {
//             if (line.trim().isEmpty()) { // check if the line is empty
//                 continue; // skip to next iteration of loop
//             }
//             String[] movementData = line.split(csvSplitBy);

//             Integer id = Integer.parseInt(movementData[0].replaceAll("\"", ""));
//             String ticker = movementData[1].replaceAll("\"", "");
//             Integer quantity = Integer.parseInt(movementData[2].replaceAll("\"", ""));
//             double price = Double.parseDouble(movementData[3].replaceAll("\"", ""));
//             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//             Date date = sdf.parse(movementData[4].replaceAll("\"", ""));
//             //TODO:
//             // PortfolioMovement movement = new PortfolioMovement(id, ticker, quantity, price, date);
//             // movements.add(movement);
//         }
//     } catch (IOException | ParseException e) {
//         e.printStackTrace();
//     }

//     return movements;
// }

// // public void appendPortfolioMovementToCsv(PortfolioMovement movement) {
// //     String csvFile = CSV_FILE;
// //     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
// //     DecimalFormatSymbols dfs = new DecimalFormatSymbols();
// //     dfs.setDecimalSeparator('.');
// //     DecimalFormat df = new DecimalFormat("#.00", dfs);

// //     try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
// //         String movementData = String.format("\"%s\",\"%s\",\"%d\",\"%s\",\"%s\"",
// //                 movement.id.toString(),
// //                 movement.ticker,
// //                 movement.quantity,
// //                 df.format(movement.price),
// //                 sdf.format(movement.date));

// //         // Check if the file exists and is not empty
// //         File file = new File(csvFile);
// //         if (file.exists() && file.length() > 0) {
// //             bw.newLine(); // Add new line only if the file is not empty
// //         }

// //         bw.write(movementData);
// //     } catch (IOException e) {
// //         e.printStackTrace();
// //     }
// // }
    


//     public void addMovement(PortfolioMovement movement) {
//         appendPortfolioMovementToCsv(movement);
//         this.movements.add(movement);
//     }

//     public void removeMovement(PortfolioMovement movement) {
//         this.movements.remove(movement);
//     }

//     public void removeMovement(Integer id) {
//         this.movements.removeIf(movement -> movement.getId().equals(id));
//     }



//     public ArrayList<HashMap<String,Object>> getPortfolioMap() {
//         ArrayList<HashMap<String,Object>> movementsMap = new ArrayList<>();
//         for (PortfolioMovement movement : movements) {
//             movementsMap.add(movement.getPortfolioMovementMap());
//         }
//         return movementsMap;
//     }

//     // public Optional<List<PorfolioMovementModel>> getPortfolio() {
//     //     return Optional.ofNullable(movements.size() == 0 ? null : movements);
//     // }

//     public List<PortfolioMovement> getPortfolio() {
//         return movements;
//     }




// }
