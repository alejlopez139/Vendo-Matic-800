package com.techelevator.view;

import java.util.Locale;

/*Chips, Gum and Drinks will extend from this class
This way if extra item types are introduced, we can just introduce a new class.
is it an improvement over having list of everything and checking if it contains a snack type?
not sure
 */
public abstract class Snack {
    private String slotLocation = " ";
    private String itemName = " ";
    private double price = 0.0;
    private int quantity;
    //The most items can be stocked to
    private static final int MAX_INVENTORY = 5;
    //Creates a constructor of snack where parameters are assigned to variables from the class.
    // everytime the snack is stocked for the first time, it's stocked to the max, 5.
    public Snack(String slotLocation, String itemName, double price){
        this.slotLocation = slotLocation;
        this.itemName = itemName;
        this.price = price;
        this.quantity = MAX_INVENTORY;
    }
    //returns item name
    public String getItemName(){
        return this.itemName;
    }
    //returns price of item
    public double getPrice(){
        return this.price;
    }
    //returns quantity of item
    public int getQuantity(){
        return this.quantity;
    }
    public int setQuantity(int quantity){
        return this.quantity = quantity;
    }
    /*
        Checks if item is Sold Out or not
        if it is, returns the name of Item plus SOLD OUT is displayed as quantity
        Otherwise, returns the name of item and it's quantity
     */
    public String displayQuantity(){
        String message = " ";
        if(this.quantity == 0){
            message = this.itemName + " Quantity: SOLD OUT";
        }
        else {
            message = this.itemName + " Quantity: " + this.quantity;
        }
        return message;
    }
    public String displayPrice(){
        return "$" + this.getPrice();
    }
    public String dispenseSnack(){
        this.quantity--;
        return this.getItemName() + " Cost: " + this.displayPrice();
    }
    //returns locations of item
    public String getSlotLocation(){
        return this.slotLocation.toUpperCase(Locale.ROOT);
    }
    //is abstract because each snack type will override this with their own dispensingMessage() to output a different message
    public abstract String dispensingMessage();
}
