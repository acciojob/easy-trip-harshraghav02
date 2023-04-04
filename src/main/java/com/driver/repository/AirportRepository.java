package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AirportRepository {
    HashMap<String, Airport> airportHashMap = new HashMap<>();
    HashMap<Integer, Flight> flightHashMap = new HashMap<>();
    HashMap<Integer, Passenger> passengerHashMap = new HashMap<>();

    HashMap<Integer, List<Integer>> flightPassengerHashMap = new HashMap<>();

    public void addAirport(Airport airport){
        String key = airport.getAirportName();
        airportHashMap.put(key,airport);

    }
    public void addFlight(Flight flight){
        int flightId = flight.getFlightId();
        flightHashMap.put(flightId,flight);
    }

    public  void addPassenger(Passenger passenger){
        int passengerId = passenger.getPassengerId();
        passengerHashMap.put(passengerId,passenger);
    }

    public double getFlightDuration(City from,City to){

        double maxDuration = Double.MAX_VALUE;
        for(Flight flight : flightHashMap.values()){
            City fromCity = flight.getFromCity();
            City toCity = flight.getToCity();
            double duration = flight.getDuration();
            if(fromCity==from && toCity==to ){
                if(maxDuration > duration){
                    maxDuration = duration;
                }
            }
        }

        return maxDuration==Double.MAX_VALUE?-1:maxDuration;
    }
    public String getLargestAirport(){
        int max = -1;
        String name = "";

        for( Airport airport : airportHashMap.values()){
            int noOfTerminal = airport.getNoOfTerminals();
            String airportName = airport.getAirportName();

            if(noOfTerminal >= max){
                if(max == noOfTerminal){
                    int value = name.compareTo(airportName);
                    if(value > 0) name = airportName;
                }
                else {
                    max = noOfTerminal;
                    name = airportName;
                }
            }
        }
        return name;
    }

    public int getNumberOfPeople(Date date,String airportName){
        if(airportHashMap.containsKey(airportName)){
            City fromCity = airportHashMap.get(airportName).getCity();

            int ans = 0;
            for(Flight flight : flightHashMap.values()){
                if(fromCity==flight.getFromCity() || fromCity==flight.getToCity()){
                    if(flight.getFlightDate().equals(date)){
                        ans += flightPassengerHashMap.get(flight.getFlightId()).size();
                    }
                }
            }
            return ans;
        }
        return 0;
    }

    public String bookingTicket(int flightId,int passengerId){
        List<Integer> numberOfpassengers;
        if(flightPassengerHashMap.containsKey(flightId)){
            numberOfpassengers = flightPassengerHashMap.get(flightId);
            int maxPassengers = flightHashMap.get(flightId).getMaxCapacity();

            if(numberOfpassengers.size() == maxPassengers || numberOfpassengers.contains(passengerId)) return "FAILURE";


        }
        else{
            numberOfpassengers = new ArrayList<>();
            if(numberOfpassengers.size() == flightHashMap.get(flightId).getMaxCapacity()) return "FAILURE";
        }
        numberOfpassengers.add(passengerId);
        flightPassengerHashMap.put(flightId,numberOfpassengers);
        return "SUCCESS";
    }

    public String cancelingTicket(int flightId,int passengerId){

        if(!flightPassengerHashMap.containsKey(flightId)){
            return "FAILURE";
        }
        List<Integer> numberOfPassengers = flightPassengerHashMap.get(flightId);
        if(!numberOfPassengers.contains(passengerId)){
            return "FAILURE";
        }
        numberOfPassengers.remove(Integer.valueOf(passengerId));
        return "SUCCESS";
    }

    public int calculateFare(int flightId){
        int currentPrice = 3000;

        if(flightPassengerHashMap.containsKey(flightId)){
            currentPrice += flightPassengerHashMap.get(flightId).size()*50;
        }
        return currentPrice;
    }

    public int getCountOfBookingDoneByPassenger(int passengerId){
        int count = 0;

        for(List<Integer> numofpassegers : flightPassengerHashMap.values()){
            if(numofpassegers.contains(passengerId))
                count++;
        }
        return count;
    }
    public String getName(int flightId){
        if(flightHashMap.containsKey(flightId)){
            City currentCity = flightHashMap.get(flightId).getFromCity();

            for(Airport airport : airportHashMap.values()){
                if(airport.getCity() == currentCity){
                    return airport.getAirportName();
                }
            }
        }
        return null;
    }

    public int getTotalRevenue(int flightId){
        int totalMoney = 0;

        if(flightPassengerHashMap.containsKey(flightId)){
            int numberOfPassengers = flightPassengerHashMap.get(flightId).size();

            for(int i=1;i<=numberOfPassengers;i++){
                totalMoney += 3000 + (i-1)*50;
            }
        }
        return totalMoney;



    }
}
