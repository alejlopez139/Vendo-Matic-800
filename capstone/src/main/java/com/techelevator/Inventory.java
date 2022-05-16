package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.techelevator.view.*;

public class Inventory {
    //List for snacks, should take in each snack type
    private List<Snack> inventoryList = new ArrayList<Snack>();
    //These are used for Menu options
    private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
    private static final String FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT, FINISH_TRANSACTION};
    private Menu purchaseMenu;
    private CustomerMoney virtualWallet;
    /*  create Strings that contain snack types, to use later to compare Uppercase values, snack types need to convert to Uppercase
        also gets rid of magic values
     */
    private static final String CHIPS = "CHIP";
    private static final String GUM = "GUM";
    private static final String CANDY = "CANDY";
    private static final String Drink = "DRINK";

    public Inventory(){
        this.purchaseMenu = new Menu(System.in, System.out);
        this.virtualWallet = new CustomerMoney();
    }
    public void displayPurchaseMenu(){
        boolean end = false;
        while(!end) {
            System.out.println(this.virtualWallet.displayWallet());
            String choice = (String) purchaseMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
            if (choice.equals(PURCHASE_MENU_FEED_MONEY)) {
                // feed money
                virtualWallet.displayFeedMoney();

            } else if (choice.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
                // select product
                purchaseItem();

            } else if (choice.equals(FINISH_TRANSACTION)) {
                System.out.println(virtualWallet.giveChange());
                System.out.println("Finished!");
                end = true;
            }
        }
    }
    /*
        Goes through the purchase menu
        Accepts user input, checks if user input equals any of the slot locations from the snacks
        if it does, checks if wallet and quantity are enough, if it is, quantity is decreased and so is wallet
        if neither are true, it won't continue with the purchase
        it logs the transaction in the Log File
     */
    public void purchaseItem() {
        displayInventory();
        double currentWallet = 0.0;
        double tempWallet = 0.0;
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter item slot(A1): ");
        String userSlotLocation = userInput.nextLine();
        userSlotLocation = userSlotLocation.toUpperCase(Locale.ROOT);
        /*
            Goes through the entire inventory List to check userInput, and if it equals any slotLocation of any snack object
         */
        for (Snack snackInventory : inventoryList) {
            if (userSlotLocation.equals(snackInventory.getSlotLocation())) {
                //If there's a match
                if (snackInventory.getQuantity() == 0) {
                    System.out.println(snackInventory.displayQuantity() + "\nPick another item!");

                }
                //If the user's wallet has more than or equal to the item's price.
                //This is also where we dispense the snack, changing it's quantity and giving a message depending on it's snack type
                else if (virtualWallet.getWallet() >= snackInventory.getPrice()) {
                    tempWallet = virtualWallet.getWallet();
                    currentWallet = virtualWallet.getWallet() - snackInventory.getPrice();
                    virtualWallet.setWallet(currentWallet);
                    System.out.println(snackInventory.dispenseSnack() + " " + virtualWallet.displayWallet() + "\n" + snackInventory.dispensingMessage());
                    Log.logTransactions(snackInventory.getItemName() + " " + snackInventory.getSlotLocation()
                            + " $" + tempWallet + " $" + virtualWallet.getWallet());

                }
                //if wallet doesn't have enough money
                else if (virtualWallet.getWallet() < snackInventory.getPrice()) {
                    System.out.println("You do not have enough money for " + snackInventory.getItemName() + ". You require " +
                            snackInventory.displayPrice() + " and you have $" + virtualWallet.getWallet() +
                            " Please insert more money.");
                    pressAnyKeyToContinue();
                }
                //userinput didn't equal any slotlocation
                else {
                    System.out.println("Nothing is in that slot!\n Press Enter to continue...");
                    pressAnyKeyToContinue();
                }
            }

        }
    }
    //Requires the user to press any key to continue
    private void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }
    /*
        Iterates through inventoryList, and displays list and quantity of each item.
     */
    public void displayInventory(){
        for (Snack invSnack: inventoryList) {
            System.out.println(invSnack.getSlotLocation() + " " + invSnack.getItemName() + " " + invSnack.getPrice()
                    + " " + invSnack.displayQuantity());
        }
    }
    /*
        Opens vendingmachine.csv, reads each line to determine which snack type each snack has. only works if
        each line is typed as following:
        itemSlot| itemName| price | snackType
        sets quantity for each snack at 5 to start.
     */
    public void stockInventory(){
        Path path = Paths.get("vendingmachine.csv");
        String pathLog = path.toAbsolutePath().toString();
        File source = new File(pathLog);
        int count = 0;
        int categoryCount = 0;
        try(Scanner sc = new Scanner(new FileReader(source)))
        {
            while (sc.hasNextLine()){
                /*  Creates snacks of different types and then adds them to the list
                    reads in line of text, then splits it up at |, and inserts it into each array
                    expects to read line as
                    itemSlot| itemName| price | snackType
                 */
                String lineOfText = sc.nextLine();
                String[] splitAtPipe = lineOfText.split("\\|");
                /*
                    indexes are
                    itemSlot| itemName| price | snackType
                    [  0    |     1   |   2   |   3  ]
                    depending on index 3, creates a snack of respective type

                    Checks if index[3] set to Uppercase, is equal to one of the static final variables initialized
                    at the top of the class
                 */
                if(splitAtPipe[3].toUpperCase(Locale.ROOT).contentEquals(CHIPS)){
                    /*  creates a snack that's type Chip(SlotLocation, ItemName, Price)
                        Need to make the price into double, since it's currently string
                     */
                    Snack invSnack = new Chip(splitAtPipe[0], splitAtPipe[1], Double.parseDouble(splitAtPipe[2]));
                    //Add snack to inventoryList
                    inventoryList.add(invSnack);
                }
                /*  creates a snack that's type Candy(SlotLocation, ItemName, Price)
                    Need to make the price into double, since it's currently string
                 */
                else if(splitAtPipe[3].toUpperCase(Locale.ROOT).contentEquals(CANDY)){
                    Snack invSnack = new Candy(splitAtPipe[0], splitAtPipe[1], Double.parseDouble(splitAtPipe[2]));
                    //Add snack to inventoryList
                    inventoryList.add(invSnack);
                }
                /*  creates a snack that's type Gum(SlotLocation, ItemName, Price)
                    Need to make the price into double, since it's currently string
                 */
                else if(splitAtPipe[3].toUpperCase(Locale.ROOT).contentEquals(GUM)){
                    Snack invSnack = new Gum(splitAtPipe[0], splitAtPipe[1], Double.parseDouble(splitAtPipe[2]));
                    //Add snack to inventoryList
                    inventoryList.add(invSnack);
                }
                /*  creates a snack that's type Drink(SlotLocation, ItemName, Price)
                    Need to make the price into double, since it's currently string
                 */
                else if(splitAtPipe[3].toUpperCase(Locale.ROOT).contentEquals(Drink)){
                    Snack invSnack = new Drink(splitAtPipe[0], splitAtPipe[1], Double.parseDouble(splitAtPipe[2]));
                    //Add snack to inventoryList
                    inventoryList.add(invSnack);
                }
            }
        }catch(FileNotFoundException ex){
            ex.getLocalizedMessage();
        }
    }

}
