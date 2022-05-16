package com.techelevator.view;
/*
    extends Snack, overrides dispenseMessage()
    calls super method in constructor
 */
public class Gum extends Snack{
    //Calls super constructor
    public Gum(String location, String name, double price){
        super(location, name, price);
    }
    /*
        Each snack type will override this method to return a different message
        This method will return "Chew Chew, Yum!"
     */
    @Override
    public String dispensingMessage(){
        return "Chew Chew, Yum!";
    }
}
