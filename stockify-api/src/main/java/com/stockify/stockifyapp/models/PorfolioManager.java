package com.stockify.stockifyapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class PorfolioManager {

    ArrayList<PorfolioMovement> movements;
    
    public PorfolioManager(ArrayList<PorfolioMovement> movements) {
        this.movements = movements;
    }

    public PorfolioManager()  {
        // this(new ArrayList<>());
        ArrayList<PorfolioMovement> movements = new ArrayList<>();
        // create synthetic data
        movements.add(new PorfolioMovement(new Stock("AAPL"), 10, 100, new Date()));
        movements.add(new PorfolioMovement(new Stock("MSFT"), 10, 100, new Date()));
        this.movements = movements;
    }



    public void addMovement(PorfolioMovement movement) {
        this.movements.add(movement);
    }

    public void removeMovement(PorfolioMovement movement) {
        this.movements.remove(movement);
    }

    public void removeMovement(UUID id) {
        this.movements.removeIf(movement -> movement.getId().equals(id));
    }



    public ArrayList<HashMap<String,Object>> getPortfolioMap() {
        ArrayList<HashMap<String,Object>> movementsMap = new ArrayList<>();
        for (PorfolioMovement movement : movements) {
            movementsMap.add(movement.getPortfolioMovementMap());
        }
        return movementsMap;
    }

    // public Optional<List<PorfolioMovementModel>> getPortfolio() {
    //     return Optional.ofNullable(movements.size() == 0 ? null : movements);
    // }

    public List<PorfolioMovement> getPortfolio() {
        return movements;
    }




}
