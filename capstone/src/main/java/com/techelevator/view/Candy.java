package com.techelevator.view;

public class Candy extends Snack{
    //Calls super constructor
    public Candy(String location, String name, double price){
        super(location, name, price);
    }
    /*
        Each snack type will override this method to return a different message
        This method will return "Munch Munch, Yum!"
     */
    @Override
    public String dispensingMessage(){
        return "Munch Munch, Yum!";
    }
}
