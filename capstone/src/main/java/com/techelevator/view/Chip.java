package com.techelevator.view;
/*
    extends Snack, overrides dispenseMessage()
    calls super method in constructor
 */
public class Chip extends Snack{
    //Calls super constructor
    public Chip(String location, String name, double price){
        super(location, name, price);
    }
    /*
        Each snack type will override this method to return a different message
        This method will return "Crunch Crunch, Yum!"
     */
    @Override
    public String dispensingMessage(){
        return "Crunch Crunch, Yum!";
    }
}
