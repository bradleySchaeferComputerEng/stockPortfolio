/**
* Investment class
* Contains all constructors for Investment and all functions needed for Investment
* This is the object of a Stock used in arraylist from Portfolio
*/

package eportfolio;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;  
import java.io.File;  // Import the File clas
import java.lang.Object;
import java.io.Writer;
import java.io.PrintWriter;

public class Investment {   /** Investment objectClass */
    
    static double gain;

    String type;
    String symbol;
    String name;
    double quantity;
    double price;
    double bookValue;

    /**
    * @param newType Type for stock or mutualfund
    * @param newSymbol symbol for stock
    * @param newName name for stock
    * @param newQuantity quantity of stock
    * @param newPrice price for stock
    * @param newBookValue to set attributes of Investment
    */
    public void set ( String newType, String newSymbol, String newName, double newQuantity, double newPrice, double newBookValue ) {
        type = newType;
        symbol = newSymbol;
        name = newName;
        quantity = newQuantity;
        price = newPrice;
        bookValue = newBookValue;

    }

     /**
    * @return All attributes of Stock in a string
    */
    public String toString () {
        char ch = '"';
        return ("type = " + ch + type + ch + "\nsymbol = " + ch + symbol + ch + "\nname = " + ch + name + ch + "\nquantity = " + ch + quantity + ch + "\nprice = " + ch + price + ch + "\nbookValue = " + ch + bookValue + ch + "\n");
    }

     /**
    * @param otherObject Object to check if attributes the same as a Stock
    * @return The Boolean value true or false
    */
    public boolean equals ( Investment otherObject ) {
        return ( (symbol.equals(otherObject.symbol)) && (name.equals(otherObject.name)) && (quantity == otherObject.quantity) && (price == otherObject.price) && (bookValue == otherObject.bookValue) );
    }

     /**
    * @return typeOfInvestment
    */
    public String getType () {
        return type;
    }

     /**
    * @return nameOfInvestment
    */
    public String getName () {
        return name;
    }

     /**
    * @return symbolInvestment
    */
    public String getSymbol () {
        return symbol;
    }

     /**
    * @return quantityInvestment
    */
    public double getQuant () {
        return quantity;
    }

     /**
    * @return priceInvestment
    */
    public double getPrice () {
        return price;
    }
    
     /**
    * @return bookValOfInvestment
    */
    public double getBookVal () {
        return bookValue;
    }

     /**
    * @param price updated price of Investment
    * @param quant to buy and update values
    */
    public void updateBuy ( double price, double quant ) {

        this.quantity = this.quantity + quant;
        this.price = price;
    }

     /**
    * @param newPrice updated price of Investmenet
    * @param newQuant, to sell investments and have the new price and how much they sell
    */
    public void updateSell (double newPrice, double newQuant ) { //CHECK MORE
        this.price = newPrice;
        this.quantity = newQuant;
    }

     /**
    * @param price to update the price on Object Investment
    */
    public void update ( double price ) {
        this.price = price;
    }
}
