package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;

import java.util.Date;

public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }

    public void addFlight(Flight flight){
        airportRepository.addFlight(flight);
    }

    public void addPassenger(Passenger passenger){
        airportRepository.addPassenger(passenger);
    }
    public String getLargestAirport(){
        String ans = airportRepository.getLargestAirport();
        return ans;
    }

    public double getSmallestDistance(City fromCity,City toCity){
       return airportRepository.getFlightDuration(fromCity,toCity);

    }

    public int getNumberOfPeople(Date date, String airportName){
        return airportRepository.getNumberOfPeople(date,airportName);
    }

    public String bookTicket(int flightId,int passengerId){
        String ans = airportRepository.bookingTicket(flightId,passengerId);
        return ans;
    }

    public String cancelTicket(int flightId,int passengerId){
        return airportRepository.cancelingTicket(flightId,passengerId);
    }

    public int calculateFare(int flightId){
        return airportRepository.calculateFare(flightId);
    }

    public int getCountOfBookingDoneByPassenger(int passengerId){
        return airportRepository.getCountOfBookingDoneByPassenger(passengerId);
    }

    public int getTotalRevenue(int flightId){
        return airportRepository.getTotalRevenue(flightId);
    }
    public String findAirportName(int flightId){
        return  airportRepository.getName(flightId);
    }


}
