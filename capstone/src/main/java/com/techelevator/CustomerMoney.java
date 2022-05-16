package com.techelevator;

import com.techelevator.view.Menu;

import java.util.PrimitiveIterator;

/*
    Menu for Purchasing items, and for adding money to User's wallet
 */
public class CustomerMoney {
    private double wallet;
    private int numQuarters = 0;
    private int numDimes = 0;
    private int numNickles = 0;
    private static final String ONE_DOLLAR = "$1.00";
    private static final String TWO_DOLLARS = "$2.00";
    private static final String FIVE_DOLLARS = "$5.00";
    private static final String TEN_DOLLARS = "$10.00";
    private static final String STOP_FEEDING = "Return to Purchase Menu";
    private static final String[] INPUT_DOLLARS = {ONE_DOLLAR, TWO_DOLLARS, FIVE_DOLLARS, TEN_DOLLARS, STOP_FEEDING};
    private static final double ONE = 1.00;
    private static final double TWO = 2.00;
    private static final double FIVE = 5.00;
    private static final double TEN = 10.00;
    private static final double QUARTER = 0.25;
    private static final double DIMES = 0.10;
    private static final double NICKLES = 0.05;
    private static double BROKE = 0.0;
    private static String FEED_MONEY = "FEED MONEY";

    private Menu menu;

    /*
        Everyone's wallet starts out as BROKE
     */
    public CustomerMoney(){
        this.menu = new Menu(System.in, System.out);
        this.wallet = this.BROKE;
    }
    /*
        formats the wallet to maintain precision
        is this allowed in a getter? Not sure
        could this have been a separate method, sure
     */
    public double getWallet(){
        String formattedWallet = String.format("%.2f", this.wallet);
        this.wallet = Double.parseDouble(formattedWallet);
        return this.wallet;
    }
    public void setWallet(double newAmount){
        this.wallet = newAmount;
    }

    public void displayFeedMoney(){
        boolean end = false;
        String addedMoney = " ";
        while(!end) {
            System.out.println(displayWallet());
            String choice = (String) menu.getChoiceFromOptions(INPUT_DOLLARS );

            if (choice.equals(ONE_DOLLAR)) {
                wallet += ONE;
                addedMoney = "Added one dollar";
                Log.logTransactions(FEED_MONEY + ONE_DOLLAR + " $" + getWallet());
            } else if (choice.equals(TWO_DOLLARS)) {
                wallet += TWO;
                addedMoney = "Added two dollars";
                Log.logTransactions(FEED_MONEY + TWO_DOLLARS+ " $" + getWallet());
            } else if (choice.equals(FIVE_DOLLARS)){
                wallet += FIVE;
                addedMoney = "Added five dollars";
                Log.logTransactions(FEED_MONEY + FIVE_DOLLARS + " $" + getWallet());
            } else if(choice.equals(TEN_DOLLARS)){
                wallet += TEN;
                addedMoney = "Added ten dollars";
                Log.logTransactions(FEED_MONEY + FIVE_DOLLARS + " $" + getWallet());
            }
            else if (choice.equals(STOP_FEEDING)) {
                System.out.println("Returning to Purchase menu...");
                end = true;
            }
        }
    }
    public String displayWallet(){
        return "Current Money in Wallet " + this.wallet;
    }
    /*
        Gives change back to the user in the least amount of coins
        it prints out how many of each coin it gives to the user w/ proper syntax
        Logs that the change was given back to the user and sets the wallet to 0
        (in this case, wallet is set to BROKE, but is probably unnecessary)
     */
    public String giveChange(){
        String message = "Don't forget your change! ";
        double tempWallet = this.getWallet();
        numQuarters = (int)(tempWallet / QUARTER);
        tempWallet = tempWallet % QUARTER;
        numDimes = (int) (tempWallet / DIMES);
        tempWallet = tempWallet % DIMES;
        numNickles = (int)(tempWallet / NICKLES);
        tempWallet = tempWallet % NICKLES;

        if(numQuarters > 0){
            message += (numQuarters > 1) ? numQuarters + " Quarters " :  numQuarters + " Quarter ";
        }
        else if (numDimes > 0){
            message += (numDimes > 1) ? numDimes + " Dimes " : numDimes + " Dime ";
        }
        else if (numNickles > 0){
            message += (numNickles > 1) ? numNickles + " Nickles " : numNickles + " Nickle ";
        }
        Log.logTransactions("GIVE CHANGE $" + getWallet() + " $" + this.BROKE);
        setWallet(this.BROKE);
        return message;
    }
}