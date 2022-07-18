// Homework 2: Cash Resiger Program
// Course: CIS357
// Due date: 7/13/2022
// Name: Christopher Rucker
// Instructor: Il-Hyung Cho
// Program description: This program allows the user to purchase from a list of items and asks them if they want to update that list of items at the end of the program.
/* Program features: Change the item code type to String: Y
                    Provide the input in CSV format. Ask the user to enter the input file name: P
                    Implement exception handling for
                    File input: P
                    Checking wrong input data type: Y
                    Checking invalid data value: Y
                    Tendered amount less than the total amount: Y
                    Use ArrayList for the items data: Y
                    Adding item data: Y
                    Deleting item data: Y
                    Modifying item data: Y  */


import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HW2_Rucker {

    /**
     * the computePrice method calculates the price of the item List. It accepts the price of each
     * Item object and the sales 6 percent sales tax
     *
     * @return the total in dollars
     */
    public static double computePrice(double price, double tax) {

        double salesTax = price * tax;
        double total = salesTax + price;
        return total;

    }

    public static void main(String[] args) throws Exception {
        try {
            Scanner x = new Scanner(new File("items.txt"));

            while (x.hasNext()) {
                String itemCode = x.next();
                String itemName = x.next();
                String price = x.next();
                System.out.printf("\n ", itemCode, itemName, price);
            }
            /**@throws exception if file doesnt exist.**/

        } catch (Exception e) {
            System.out.println("file not something");
        }


        /**intializing the arrayList that will hold the Item Data*/
        ArrayList<Item> itemList = new ArrayList<>();
        ArrayList<String> storedItems = new ArrayList<>();

        /**Creating the list of items that are being sold.**/

        itemList.add(new Item("A001", "bottled water", 1.50));
        itemList.add(new Item("A002", "candy", 1.00));
        itemList.add(new Item("A003", "chocolate", 2.50));
        itemList.add(new Item("A004", "gum", 1.00));
        itemList.add(new Item("A005", "soda", 2.50));
        itemList.add(new Item("A006", "juice", 3.00));
        itemList.add(new Item("B001", "popcorn", 2.50));
        itemList.add(new Item("B002", "donut", 1.50));
        itemList.add(new Item("B003", "pretzel", 2.00));
        itemList.add(new Item("A007", "caramel", 1.50));

        /** Initializing all of the variables im going to use */
        Scanner input = new Scanner(System.in);
        String answer = "";
        String answer2 = "";
        String productCode = "";
        int quantity = 0;
        double amountPaid = 0;
        double subTotal = 0;
        double totalSale = 0;
        double salesTax = 0.06;
        System.out.println("Welcome to POST system!\n");
        System.out.print("input file: ");
        String inputFile = input.next();
        java.io.File file = new java.io.File(inputFile);

        // Create a file
        java.io.PrintWriter output = new java.io.PrintWriter(file);

        System.out.print("Beginning new sale(Y/N) ");
        answer = input.next();

        System.out.println("----------------");
        /**sets the match variable to false until user inputs a proper product code*/
        boolean match = false;

        while (!answer.equalsIgnoreCase("N")) {
            System.out.print("Enter product code:");
            productCode = input.next();
            /** prompts the user to enter the product codes so they can purchase items that they want */
            while (!productCode.trim().equals("-1")) {
                for (int i = 0; i < itemList.size(); i++) {

                    /**iterates through arraylist to see if entered product code matches the item Code in the array list,
                     * if the user enters a proper product code then the match variable will be switched to false,
                     * only if the item is found in the array list*/

                    if (itemList.get(i).itemCode.equals(productCode.trim())) {
                        match = true;
                        System.out.println("item name: " + itemList.get(i).itemName);
                        /** This try catch block throws an error if the user enters input that is not a valid data type for quantity */
                        try {
                            System.out.print("Enter Quantity: ");
                            quantity = input.nextInt();
                            System.out.println("Item total: $" + itemList.get(i).unitPrice * quantity);
                            subTotal = itemList.get(i).unitPrice * quantity + subTotal;
                            /**adds the bought item to a separate Arraylist that keeps tracks of purchased items**/
                            storedItems.add(" \t" + quantity + " \t" + itemList.get(i).itemName + " $" + itemList.get(i).unitPrice);

                            /** informs the user that they must enter a valid data type for quantity
                             * @throws exception if data type is invalid.**/
                        } catch (Exception e) {
                            System.out.println("!! invalid data type");
                            System.out.println("Enter Quantity: ");
                        }

                    }

                }
                /** This checks if the user wants to view the item list by iterating through the array list that holds the items, and printing the information for them all */
                if (productCode.trim().equals("0000")) {
                    match = true;

                    System.out.printf("item code\t" + "item name\t" + "unit price\n");

                    for (int i = 0; i < itemList.size(); i++) {
                        System.out.println(itemList.get(i).itemCode + "\t" + itemList.get(i).itemName + "\t\t\t$" + itemList.get(i).unitPrice);
                    }
                }
                /** if match is never changed to true, then the user didnt enter a valid product code, and this notifies the user to enter a valid one */
                if (match == false) {
                    System.out.println("! ! invalid product code");

                }

                /** continues to ask use for product code until they enter -1,
                 * also switches the match variable back to false after each iteration of the loop to check for valid input */
                System.out.print("\nEnter product code:");
                productCode = input.next();
                //sets match back to false
                match = false;
            }

            System.out.println("---------------------");
            System.out.println("Item list:");


            for (int i = 0; i < storedItems.size(); i++) {
                System.out.println(storedItems.get(i));
            }
            System.out.println("Subtotal: \t$" + subTotal);
            //computing the total
            double totalAmountWithTax = computePrice(subTotal, salesTax);
            System.out.println("Total with tax (6%) \t\t$" + totalAmountWithTax);

            System.out.print("tendered amount: \t\t$");
            amountPaid = input.nextDouble();
            /** This while loop constantly prompts the user until they enter an amount that is more than the total */
            while (amountPaid < totalAmountWithTax) {
                System.out.println("!! not enough. Enter again:");
                System.out.print("Tendered amount: \t\t$");
                amountPaid = input.nextDouble();
            }

            double change = amountPaid - totalAmountWithTax;
            System.out.println("Change: \t\t$" + Math.round(change * 100.0) / 100.0);
            totalSale = totalSale + totalAmountWithTax;
            System.out.println("-------------------------------------\n");

            System.out.print("Beginning new sale(Y/N): ");
            answer = input.next();
            System.out.println("---------------------------------");

            /** restarts array list of sold items so the user can begin a new sale and resets the subtotal variable back to 0 */
            storedItems.removeAll(storedItems);
            subTotal = 0;

        }
        System.out.println("The total sale for the day is: " + totalSale);
        /** After the sale ends, this prompts the user to see if they want to make any updates to the item List */

        System.out.println("Do you want to update the item data? (A/D/M/Q): ");
        answer2 = input.next();

        /** if the user enters something other than Q, then they'll be able to make as many updates to the Item List that they want */
        while (!answer2.equalsIgnoreCase("Q")) {
            /** this checks if the User enters A, which adds to the item list.  */
            if (answer2.equalsIgnoreCase("A")) {
                /**This prompts them to enter the item code,name, and price of the new item**/
                System.out.println("item code: ");
                String newItemCode = "";
                newItemCode = input.next();
                System.out.print("\nitem name: ");
                String newItemName = "";
                newItemName += input.next();
                System.out.println(newItemName);
                System.out.println("\nitem price: ");
                double newItemPrice = 0.0;
                newItemPrice = input.nextDouble();

                /** adds the new item to the end of the array list**/
                itemList.add(new Item(newItemCode, newItemName, newItemPrice));
                System.out.print("Item add successful!");
                /** this checks if the User enters D, which deletes an item from the list.  */

            } else if (answer2.equalsIgnoreCase("D")) {
                /**This prompts them to enter the item code of the item they want to delete**/

                System.out.println("item code: ");
                String delItem = input.next();
                /**Iterates through the array to see if the item code that they entered exists in the list.**/

                for (int i = 0; i < itemList.size(); i++) {
                    /**if it exists, then delete the item at that position of the array using the i variable in the for loop to keep track of the index**/
                    if (delItem.trim().equals(itemList.get(i).itemCode)) {

                        itemList.remove(i);

                    }

                }
                System.out.println("Item delete sucessful");
                /** this checks if the User enters the letter M, which modifies an item in the list.  */
            } else if (answer2.equalsIgnoreCase("M")) {
                /**This prompts them to enter the item code of the item they want to modify**/
                System.out.println("item code: ");
                String modItemCode = input.next();
                /**Iterates through the array to see if the item code that they entered exists in the list.**/
                for (int i = 0; i < itemList.size(); i++) {
                    if (modItemCode.trim().equals(itemList.get(i).itemCode)) {
                        System.out.println("item name: ");
                        String modItemName = input.next();
                        System.out.println("item price: ");
                        double modItemPrice = input.nextDouble();
                        itemList.get(i).unitPrice = modItemPrice;

                    }
                }
                System.out.println("item modify sucessful!\n");
            }
            /**Asks user if they want to make additional updates to the list.**/
            System.out.println("Do you want to update the item data? (A/D/M/Q): ");
            answer2 = input.next();


        }
        /**prints the item list out at the end of the program**/
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(itemList.get(i).itemCode + "\t" + itemList.get(i).itemName + "\t\t\t$" + itemList.get(i).unitPrice);
        }

    }
}

