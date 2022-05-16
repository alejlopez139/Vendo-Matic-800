package com.techelevator.view;

public class Drink extends Snack{
    //Calls super constructor
    public Drink(String location, String name, double price){
        super(location, name, price);
    }
    /*
        Each snack type will override this method to return a different message
        This method will return "Glug Glug, Yum!"
     */
    @Override
    public String dispensingMessage(){
        return "Glug Glug, Yum!";
    }
}
