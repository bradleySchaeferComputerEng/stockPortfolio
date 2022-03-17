/**
* Stock class
* Contains all children for Investment and all functions needed for stock that are not in Investment
* This is the object of a Stock used in arraylist of investments from Portfolio
*/

package eportfolio;

public class Stock extends Investment { /** Stock objectClass */

     /**
    * @param price updated price of Investmenet
    * @param quant, to sell investments and have the new price and how much they sell
    */
    public void updateBuy ( double price, double quant ) {

        super.updateBuy(price, quant);
        
        this.bookValue = this.bookValue + (price * quant) + 9.99;   //Stocks
    }

     /**
    * @param newPrice updated price of Investmenet
    * @param quant, to sell investments and have the new price and how much they sell
    */
    public void updateSell (double newPrice, double quant ) { //CHECK MORE
        this.price = newPrice;
        double remainingQuant = this.quantity - quant;
        double totalQuant = this.quantity;

        double payment = quant * newPrice - 9.99;
    
        if (this.quantity == quant) { //Selling everything    //NOTDONE THIS
            
            gain = payment - this.bookValue;
            System.out.print("gain = ");    //printing the toal gain with 2 decimals
            System.out.printf("%.2f", gain);
            System.out.println("\nSold all the stock of " + this.symbol);
        }
        else {
            gain = payment - (this.bookValue * (quant/totalQuant));
            System.out.print("gain = ");    //printing the toal gain with 2 decimals
            System.out.printf("%.2f", gain);
            System.out.println();

            this.bookValue = this.bookValue * (remainingQuant/totalQuant);

            this.quantity = this.quantity - quant;
        }
        
        super.updateSell(price, remainingQuant);
    }

}
