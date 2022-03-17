/*
Name: Bradley Schaefer
Student ID: 1126385
A1: CIS2420
To compile 
1) javac eportfolio/*.java  
2) java eportfolio.Window eportfolio/test.txt
*/

/**
 * @author Bradley Schaefer
 * This is the Portfolio Class that will ask the user for input and run a while loop until the user exits the system
 * This adds and removes elements from the ArrayLists and files through them
 * javadoc -d javaDocA3 eportfolio generate JavaDoc
 * tar -czvf bschae01_a3.tgz bschae01_a3 to Zip
 */

package eportfolio;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;  
import java.io.File;  // Import the File clas
import java.lang.Object;
import java.awt.GridBagLayout;
import java.io.Writer;
import java.io.PrintWriter;
import java.util.HashMap;   //Hashmap
import java.lang.*;
import java.awt.ComponentOrientation;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import java.awt.Font;

import java.io.File;  // Import the File class
import java.lang.Object;

public class Window extends JFrame implements ActionListener {  /** Window Class */

    JTextArea openingWindow;

    final JComboBox<String> cb = new JComboBox<String>();
    JTextArea symbolInput;
    JTextArea nameInput;
    JTextArea quantInput;
    JTextArea priceInput;
    JTextArea outputArea;

    private int choiceFromMenu = 0; //Buy = 1, Sell = 2, Update = 3, Gain = 4, Search = 5
    private int choiceForCB = 1;    //Stocks = 1, MF = 2

    static ArrayList<Investment> investments = new ArrayList<Investment>();    //Decleration of ArrayList
    static HashMap <String, ArrayList<Integer> > investmentWordHashMap = new HashMap <String, ArrayList<Integer> > ();  //Decleration of HashMap
    
    static int fileInput = 0;   // i is for loops and n is for input, File input turns =1 if no file was attached at compile
    static int numInvestments = 0;
    int indexOfStock = 0;

    static String fileNameFromInput;

    public class cbListener implements ActionListener { //Used to find what is in CB box
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            String cbString = (String)cb.getSelectedItem();
            if (cbString.equals("Stock")) {
                choiceForCB = 1;
            }
            if (cbString.equals("MutualFund")) {
                choiceForCB = 2;
            }
            //System.out.println("Choice For CB = " + choiceForCB);
        }
    }

    public static void main(String[] args) {
        int i = 0;
        try {   //Command Line input fileName
            File file = new File (args[0]);
            fileNameFromInput = new String(args[0]);
            //System.out.println("\nFile Importing from = " + args[0] + "\nBe sure to add path where file is coming from ex) eportfolio/file.txt\n");
            Scanner scan = new Scanner (file);
            int numberInput = 0;

            String type = null; //Inserting from file
            String symbol = null;
            String name = null;
            double quant = 0;
            double price = 0;
            double bookVal = 0;
            
            while ( scan.hasNextLine() ) {  
                StringBuffer sb = new StringBuffer();   //creating name with multiple words
                String eachLine = null;  //Save each line then use split, deleate data in previous arrayList, the input the data into arrayList
                eachLine = scan.nextLine();
                if (eachLine == "") {
                    numberInput = 0;
                }
                else if (numberInput == 0) { //type
                    String words[] = eachLine.split("[, ]+");
                    String output = words[2].replace("\"", "");
                    type = output;
                    numberInput++;
                }
                else if (numberInput == 1) { //symbol
                    String words[] = eachLine.split("[, ]+");
                    String output = words[2].replace("\"", "");
                    symbol = output;
                    numberInput++;
                }
                else if (numberInput == 2) { //name
                    String words[] = eachLine.split("[, ]+");
                    for (i = 0; i < words.length; i++) {
                        String output = words[i].replace("\"", "");
                        //System.out.print(output + " ");
                        if (i == words.length-1) {
                            sb.append(output);
                        }
                        else if (i > 1) {
                            sb.append(output + " ");
                        }
                    }
                    name = sb.toString();
                    numberInput++;
                }
                else if (numberInput == 3) { //quant
                    String words[] = eachLine.split("[, ]+");
                    String output = words[2].replace("\"", "");
                    quant = Double.parseDouble(output);
                    numberInput++;
                }
                else if (numberInput == 4) { //price
                    String words[] = eachLine.split("[, ]+");
                    String output = words[2].replace("\"", "");
                    price = Double.parseDouble(output);
                    numberInput++;
                }
                else if (numberInput == 5) { //book val, set and reset
                    String words[] = eachLine.split("[, ]+");
                    String output = words[2].replace("\"", "");
                    bookVal = Double.parseDouble(output);

                    if ( type.equals("stock") ) {   //input stock into ArrayList stock
                        Stock newStock = new Stock();
                        newStock.set(type, symbol, name, quant, price, bookVal);
                        investments.add(newStock); 
                        //System.out.println("Stock inserted into list");

                        String []eachWordOfName = name.split("[ ]+");   //Hashmap Input

                        for (i = 0; i < eachWordOfName.length; i++) {
                            String hashInput = eachWordOfName[i].toLowerCase();
                            if (investmentWordHashMap.containsKey(hashInput)) { //update int array
                                ArrayList<Integer> intArrayFromHash = new ArrayList<Integer>();
                                intArrayFromHash = investmentWordHashMap.get(hashInput);
                        
                                intArrayFromHash.add(numInvestments); //Adds stock number to hashMap
                                investmentWordHashMap.remove(hashInput);    //Gets rid of HashInput
                                //System.out.println(hashInput + " " + intArrayFromHash);   //TestCase
                                investmentWordHashMap.put(hashInput, intArrayFromHash);
                            }
                            else {
                                ArrayList<Integer> intArrayForHash = new ArrayList<Integer>();
                                intArrayForHash.add(numInvestments);  //Assuming new word

                                //System.out.println(hashInput + " " + intArrayForHash);   //TestCase
                                
                                investmentWordHashMap.put(hashInput, intArrayForHash);
                            }
                        }
                    }
                    else if ( type.equals("mutualfund") ) { //input mutualFund in ArrayList mutualFund
                        MutualFund newFund = new MutualFund();
                        newFund.set(type, symbol, name, quant, price, bookVal);
                        investments.add(newFund);
                        //System.out.println("Fund inserted into list");

                        String []eachWordOfName = name.split("[ ]+");   //Hashmap Input

                        for (i = 0; i < eachWordOfName.length; i++) {
                            String hashInput = eachWordOfName[i].toLowerCase();
                            if (investmentWordHashMap.containsKey(hashInput)) { //update int array
                                ArrayList<Integer> intArrayFromHash = new ArrayList<Integer>();
                                intArrayFromHash = investmentWordHashMap.get(hashInput);
                        
                                intArrayFromHash.add(numInvestments); //Adds stock number to hashMap
                                //System.out.println(intArrayFromHash);   //TestCase
                                investmentWordHashMap.remove(hashInput);    //Gets rid of HashInput

                                investmentWordHashMap.put(hashInput, intArrayFromHash);
                            }
                            else {
                                ArrayList<Integer> intArrayForHash = new ArrayList<Integer>();
                                intArrayForHash.add(numInvestments);  //Assuming new word

                                //System.out.println(intArrayForHash);   //TestCase
                                
                                investmentWordHashMap.put(hashInput, intArrayForHash);
                            }
                        }
                        
                    }
                    name = null;
                    numInvestments++;
                    numberInput = 0;
                }
            }
        } 
        catch (Exception y) {   //Error checking
            //System.out.println("Could not open file. Or no file inputed. Please Enter File folderName/fileName.txt\n");
            fileInput = 1;
        }
        Window demoGui = new Window( );
        demoGui.setBackground(Color.GREEN);
        demoGui.setVisible(true);
    }

    public Window() {

        addWindowListener(new java.awt.event.WindowAdapter() {  //Allows to output Stock back ontoFile when closed
            public void windowClosing(java.awt.event.WindowEvent r) {
                if (fileInput == 0) {
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter (fileNameFromInput);
                    } catch (Exception s) {
                        //System.out.println("Failed to write.");
                    }
                    for ( Investment investment: investments ) {
                        writer.println(investment.toString());
                    }
                    writer.close();
                }
                //System.out.println("Program Exited and data inputed into file if avaible\n");
                System.exit(0);
            }
        });

        //System.out.println("Num investments = " + numInvestments);
        setSize(705, 530);
        setTitle("ePortfolio");
        setDefaultCloseOperation(Window.DO_NOTHING_ON_CLOSE);

        setLayout(null); //Set Layout

        JMenu choiceMenu = new JMenu("Commands");
        choiceMenu.setFont(new Font("Calibri", Font.PLAIN, 20));

        openingWindow = new JTextArea();
        openingWindow.setEditable(false);
        //openingWindow.setBackground(Color.WHITE);
        openingWindow.setBounds(20, 40, 666, 400);
        openingWindow.setFont(new Font("Calibri", Font.PLAIN, 20));
        openingWindow.setText("Welcome to ePortfolio.\n\n\n\nChoose a command from the “Commands” menu to buy or sell\nan investment, update prices for all investments, get gain for the\nportfolio, search for relevant investments, or quit the program.");
        add(openingWindow);

        ////////////////////////////////////////////////////////////////////////////////////
        JTextArea eleLabel = new JTextArea();
        eleLabel.setEditable(false);
        eleLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        eleLabel.setVisible(false);
        add(eleLabel);
        
        JButton resetBut = new JButton();
        resetBut.setFont(new Font("Calibri", Font.PLAIN, 20));
        resetBut.addActionListener(this);
        resetBut.setVisible(false);
        add(resetBut);

        JButton bottomBut = new JButton();
        bottomBut.setFont(new Font("Calibri", Font.PLAIN, 20));
        bottomBut.addActionListener(this);
        bottomBut.setVisible(false);
        add(bottomBut);

        JButton nextBut = new JButton();
        nextBut.setFont(new Font("Calibri", Font.PLAIN, 20));
        nextBut.addActionListener(this);
        nextBut.setVisible(false);
        add(nextBut);

        JTextArea typeLabel = new JTextArea ();
        typeLabel.setEditable(false);
        typeLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        typeLabel.setVisible(false);
        add(typeLabel);

        String[] stockOrFund = {"Stock", "MutualFund"}; //Combo Box
        JComboBox<String> cb = new JComboBox<String>(stockOrFund);
        cb.setFont(new Font("Calibri", Font.PLAIN, 20));
        cb.addActionListener(new cbListener());
        add(cb);
        cb.setVisible(false);
    
        //System.out.println("Stock or mutualfund = " + choiceForCB);

        JTextArea symbolLabel = new JTextArea();    //Symbol Label
        symbolLabel.setEditable(false);
        symbolLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        symbolLabel.setVisible(false);
        add(symbolLabel);

        symbolInput = new JTextArea();
        symbolInput.setEditable(true);
        symbolInput.setFont(new Font("Calibri", Font.PLAIN, 20));
        symbolInput.setVisible(false);
        add(symbolInput);

        JTextArea nameLabel = new JTextArea();    //Name Label
        nameLabel.setEditable(false);
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        nameLabel.setVisible(false);
        add(nameLabel);

        nameInput = new JTextArea();
        nameInput.setEditable(true);
        nameInput.setFont(new Font("Calibri", Font.PLAIN, 20));
        nameInput.setVisible(false);
        add(nameInput);

        JTextArea quantLabel = new JTextArea();    //Name Label
        quantLabel.setEditable(false);
        quantLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        quantLabel.setVisible(false);
        add(quantLabel);

        quantInput = new JTextArea();
        quantInput.setEditable(true);
        quantInput.setFont(new Font("Calibri", Font.PLAIN, 20));
        quantInput.setVisible(false);
        add(quantInput);

        JTextArea priceLabel = new JTextArea();    //Name Label
        priceLabel.setEditable(false);
        priceLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        priceLabel.setVisible(false);
        add(priceLabel);

        priceInput = new JTextArea();
        priceInput.setEditable(true);
        priceInput.setFont(new Font("Calibri", Font.PLAIN, 20));
        priceInput.setVisible(false);
        add(priceInput);

        JTextArea bottomLabel = new JTextArea();  //Messages Label
        bottomLabel.setEditable(false);
        bottomLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        bottomLabel.setVisible(false);
        add(bottomLabel);

        outputArea = new JTextArea();  //Output Messages Label
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Calibri", Font.PLAIN, 20));
        outputArea.setVisible(false);
        add(outputArea);

        JScrollPane scrolledText = new JScrollPane(outputArea);    //Allowing Scroll Bars visable
        scrolledText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrolledText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrolledText.setVisible(false);
        add(scrolledText);

        JMenuItem buyInvestmentChoice = new JMenuItem("Buy");
        buyInvestmentChoice.setFont(new Font("Calibri", Font.PLAIN, 20));

        buyInvestmentChoice.addActionListener((ActionEvent e) ->
        { 
            choiceFromMenu = 1;
            nextBut.setVisible(false);
            openingWindow.setVisible(false);
            priceInput.setEditable(true);
            outputArea.setText("");
            outputArea.setVisible(true);
            symbolInput.setEditable(true);
            nameInput.setEditable(true);
            priceInput.setEditable(true);

            eleLabel.setText("Buying an investment");
            eleLabel.setBounds(20, 20, 215, 30);
            eleLabel.setVisible(true);
            
            resetBut.setText("Reset");
            resetBut.setBounds(550, 70, 100, 38);
            resetBut.setVisible(true);

            bottomBut.setText("Buy");
            bottomBut.setBounds(550, 175, 100, 38);
            bottomBut.setVisible(true);

            typeLabel.setText("Type");
            typeLabel.setBounds(35, 60, 54, 30);
            typeLabel.setVisible(true);
            
            cb.setBounds(132, 60, 240, 30);
            cb.setVisible(true);

            symbolLabel.setText("Symbol");
            symbolLabel.setBounds(35, 100, 75, 30);
            symbolLabel.setVisible(true);
            
            symbolInput.setText("");
            symbolInput.setBounds(132, 100, 160, 30);
            symbolInput.setVisible(true);

            nameLabel.setText("Name");
            nameLabel.setBounds(35, 140, 63, 30);
            nameLabel.setVisible(true);
            
            nameInput.setText("");
            nameInput.setBounds(132, 140, 320, 30);
            nameInput.setVisible(true);

            quantLabel.setText("Quantity");
            quantLabel.setBounds(35, 180, 82, 30);
            quantLabel.setVisible(true);
            
            quantInput.setText("");
            quantInput.setBounds(132, 180, 320, 30);
            quantInput.setVisible(true);

            priceLabel.setText("Price");
            priceLabel.setBounds(35, 220, 54, 30);
            priceLabel.setVisible(true);
            
            priceInput.setText("");
            priceInput.setBounds(132, 220, 100, 30);
            priceInput.setVisible(true);


            bottomLabel.setText("Messages");
            bottomLabel.setBounds(13, 279, 100, 30);
            bottomLabel.setVisible(true);

            outputArea.setVisible(true);
            scrolledText.setBounds(13, 313, 676, 145);
            scrolledText.setVisible(true);
        });
        choiceMenu.add(buyInvestmentChoice);

        JMenuItem sellInvestmentChoice = new JMenuItem("Sell");
        sellInvestmentChoice.setFont(new Font("Calibri", Font.PLAIN, 20));

        sellInvestmentChoice.addActionListener((ActionEvent e) ->
        { 
            choiceFromMenu = 2;
            nextBut.setVisible(false);
            openingWindow.setVisible(false);
            priceInput.setEditable(true);
            outputArea.setText("");
            outputArea.setVisible(true);
            symbolInput.setEditable(true);
            nameInput.setEditable(true);
            priceInput.setEditable(true);

            eleLabel.setText("Selling an investment");
            eleLabel.setBounds(20, 20, 215, 30);
            eleLabel.setVisible(true);
            
            resetBut.setText("Reset");
            resetBut.setBounds(550, 70, 100, 38);
            resetBut.setVisible(true);

            bottomBut.setText("Sell");
            bottomBut.setBounds(550, 175, 100, 38);
            bottomBut.setVisible(true);

            typeLabel.setVisible(false);
            
            cb.setVisible(false);

            symbolLabel.setText("Symbol");
            symbolLabel.setBounds(35, 100, 75, 30);
            symbolLabel.setVisible(true);
            
            symbolInput.setText("");
            symbolInput.setBounds(132, 100, 160, 30);
            symbolInput.setVisible(true);

            nameLabel.setVisible(false);
    
            nameInput.setVisible(false);

            quantLabel.setText("Quantity");
            quantLabel.setBounds(35, 160, 82, 30);
            quantLabel.setVisible(true);
            
            quantInput.setText("");
            quantInput.setBounds(132, 160, 100, 30);
            quantInput.setVisible(true);

            priceLabel.setText("Price");
            priceLabel.setBounds(35, 220, 54, 30);
            priceLabel.setVisible(true);
            
            priceInput.setText("");
            priceInput.setBounds(132, 220, 100, 30);
            priceInput.setVisible(true);


            bottomLabel.setText("Messages");
            bottomLabel.setBounds(13, 279, 100, 30);
            bottomLabel.setVisible(true);

            outputArea.setVisible(true);
            scrolledText.setBounds(13, 313, 676, 145);
            scrolledText.setVisible(true);
        });
        choiceMenu.add(sellInvestmentChoice);

        JMenuItem updateInvestmentChoice = new JMenuItem("Update");
        updateInvestmentChoice.setFont(new Font("Calibri", Font.PLAIN, 20));

        updateInvestmentChoice.addActionListener((ActionEvent e) ->
        { 
            choiceFromMenu = 3;
            openingWindow.setVisible(false);
            priceInput.setEditable(true);
            symbolInput.setEditable(false);
            nameInput.setEditable(false);
            indexOfStock = 0;
            priceInput.setEditable(true);

            eleLabel.setText("Updating investments");
            eleLabel.setBounds(20, 20, 215, 30);
            eleLabel.setVisible(true);
            
            resetBut.setText("Prev");
            resetBut.setBounds(550, 60, 100, 38);
            resetBut.setVisible(true);

            nextBut.setText("Next");
            nextBut.setBounds(550, 122, 100, 38);
            nextBut.setVisible(true);

            bottomBut.setText("Save");
            bottomBut.setBounds(550, 185, 100, 38);
            bottomBut.setVisible(true);

            typeLabel.setVisible(false);
            
            cb.setVisible(false);

            symbolLabel.setText("Symbol");
            symbolLabel.setBounds(35, 100, 75, 30);
            symbolLabel.setVisible(true);
            
            symbolInput.setText("");
            symbolInput.setBounds(132, 100, 160, 30);
            symbolInput.setVisible(true);

            nameLabel.setText("Name");
            nameLabel.setBounds(35, 160, 63, 30);
            nameLabel.setVisible(true);
            
            nameInput.setText("");
            nameInput.setBounds(132, 160, 320, 30);
            nameInput.setVisible(true);

            quantLabel.setVisible(false);
            
            quantInput.setVisible(false);

            priceLabel.setText("Price");
            priceLabel.setBounds(35, 220, 54, 30);
            priceLabel.setVisible(true);
            
            priceInput.setText("");
            priceInput.setBounds(132, 220, 100, 30);
            priceInput.setVisible(true);


            bottomLabel.setText("Messages");
            bottomLabel.setBounds(13, 279, 100, 30);
            bottomLabel.setVisible(true);

            outputArea.setText("Please Change the Price to update the Investment then press\nthe Save button.");
            outputArea.setVisible(true);
            scrolledText.setBounds(13, 313, 676, 145);
            scrolledText.setVisible(true);

            Investment investment = investments.get(0);
            nameInput.setText(investment.getName());
            symbolInput.setText(investment.getSymbol());
            double outputPrice = investment.getPrice();
            priceInput.setText("" + outputPrice);
        });
        choiceMenu.add(updateInvestmentChoice);

        JMenuItem getGainChoice = new JMenuItem("Get Gain");
        getGainChoice.setFont(new Font("Calibri", Font.PLAIN, 20));

        getGainChoice.addActionListener((ActionEvent e) ->
        { 
            choiceFromMenu = 4;
            openingWindow.setVisible(false);
            nextBut.setVisible(false);
            outputArea.setText("");
            outputArea.setVisible(true);
            symbolInput.setEditable(true);
            nameInput.setEditable(true);

            eleLabel.setText("Getting total gain");
            eleLabel.setBounds(20, 20, 215, 30);
            eleLabel.setVisible(true);

            resetBut.setVisible(false);

            bottomBut.setVisible(false);

            typeLabel.setText("Total gain");
            typeLabel.setBounds(35, 60, 110, 30);
            typeLabel.setVisible(true);
            
            cb.setVisible(false);

            symbolLabel.setVisible(false);
            
            symbolInput.setVisible(false);

            nameLabel.setVisible(false);
            
            nameInput.setVisible(false);

            quantLabel.setVisible(false);

            quantInput.setVisible(false);

            priceLabel.setVisible(false);
            
            priceInput.setText("");
            priceInput.setEditable(false);
            priceInput.setBounds(162, 60, 150, 30);
            priceInput.setVisible(true);

            bottomLabel.setText("Individual gains");
            bottomLabel.setBounds(13, 100, 180, 30);
            bottomLabel.setVisible(true);

            outputArea.setText("");
            scrolledText.setBounds(13, 130, 676, 315);
            scrolledText.setVisible(true);

            double totalGain = 0;
            double IndividualGain = 0;
            double newPrice = 0.0;
            priceInput.setEditable(false);

            StringBuilder stringOutput = new StringBuilder();
            for (Investment investment: investments){    //Gain for Stocks
                if (investment.getType() == "stock") {  //stocks has commision
                    IndividualGain = investment.getPrice() * investment.getQuant() - 10 - investment.getBookVal();
                }
                else {  //mutual fund doesnt have commission
                    IndividualGain = investment.getPrice() * investment.getQuant() - investment.getBookVal();
                }
                double roundedIndGain = Math.round(IndividualGain*100.0)/100.0;
                stringOutput.append(investment.getSymbol() + " total profit/loss = " + roundedIndGain + "\n");
                
                totalGain += IndividualGain;
            }
            
            totalGain = Math.round(totalGain*100.0)/100.0;

            priceInput.setText("" + totalGain);
            //System.out.print("Total Gain from Investments = " + totalGain);
            outputArea.setText(stringOutput.toString());



        });
        choiceMenu.add(getGainChoice);

        JMenuItem searchChoice = new JMenuItem("Search");
        searchChoice.setFont(new Font("Calibri", Font.PLAIN, 20));

        searchChoice.addActionListener((ActionEvent e) ->
        { 
            choiceFromMenu = 5;
            openingWindow.setVisible(false);
            priceInput.setEditable(true);
            nextBut.setVisible(false);
            outputArea.setText("");
            outputArea.setVisible(true);
            symbolInput.setEditable(true);
            nameInput.setEditable(true);
            priceInput.setEditable(true);

            eleLabel.setText("Searching investments");
            eleLabel.setBounds(20, 20, 215, 30);
            eleLabel.setVisible(true);
            
            resetBut.setText("Reset");
            resetBut.setBounds(550, 70, 100, 38);
            resetBut.setVisible(true);

            bottomBut.setText("Search");
            bottomBut.setBounds(550, 175, 100, 38);
            bottomBut.setVisible(true);

            typeLabel.setVisible(false);
            
            cb.setVisible(false);

            symbolLabel.setText("Symbol");
            symbolLabel.setBounds(35, 69, 75, 30);
            symbolLabel.setVisible(true);
            
            symbolInput.setText("");
            symbolInput.setBounds(132, 69, 160, 30);
            symbolInput.setVisible(true);

            nameLabel.setText("Name\nkeywords");
            nameLabel.setBounds(35, 109, 90, 60);
            nameLabel.setVisible(true);
            
            nameInput.setText("");
            nameInput.setBounds(132, 115, 320, 30);
            nameInput.setVisible(true);

            quantLabel.setText("Low price");
            quantLabel.setBounds(35, 180, 100, 30);
            quantLabel.setVisible(true);
            
            quantInput.setText("");
            quantInput.setBounds(140, 180, 100, 30);
            quantInput.setVisible(true);

            priceLabel.setText("High price");
            priceLabel.setBounds(35, 220, 100, 30);
            priceLabel.setVisible(true);
            
            priceInput.setText("");
            priceInput.setBounds(140, 220, 100, 30);
            priceInput.setVisible(true);

            bottomLabel.setText("Search results");
            bottomLabel.setBounds(13, 279, 140, 30);
            bottomLabel.setVisible(true);

            outputArea.setVisible(true);
            scrolledText.setBounds(13, 313, 676, 145);
            scrolledText.setVisible(true);
        });
        choiceMenu.add(searchChoice);

        JMenuItem quitChoice = new JMenuItem("Quit");
        quitChoice.setFont(new Font("Calibri", Font.PLAIN, 20));    //Quits Program and outputs to file if avaible

        quitChoice.addActionListener((ActionEvent e) ->
        { 
            if (fileInput == 0) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter (fileNameFromInput);
                } catch (Exception x) {
                    //System.out.println("Failed to write.");
                }
                for ( Investment investment: investments ) {
                    writer.println(investment.toString());
                }
                writer.close();
            }
            //System.out.println("Program Exited and data inputed into file if avaible\n");
            System.exit(0);
        });
        choiceMenu.add(quitChoice);

        JMenuBar bar = new JMenuBar();
        bar.add(choiceMenu);
        setJMenuBar(bar);
    }

    public void actionPerformed(ActionEvent e)
    {
        //int choiceFromMenu = 0; //Buy = 1, Sell = 2, Update = 3, Gain = 4, Search = 5
        //int choiceForCB = 0;    //Stocks = 1, MF = 2
        //System.out.println(investments);
        
        String junk = null;
        int i = 0;

        String actionCommand = e.getActionCommand();

        //System.out.println("Action Preformed with Action Command = " + actionCommand + "\nChoice From Menu = " + choiceFromMenu + "\nchoiceForCB = " + choiceForCB + "\n");

        if (actionCommand.equals("Reset")) {    //User Activates Reset it Clears TextAreas
            symbolInput.setText("");
            nameInput.setText("");
            quantInput.setText("");
            priceInput.setText("");
            outputArea.setText("");
            //System.out.println("Reset");
        }

        if ((choiceFromMenu == 1) && (actionCommand.equals("Buy"))) {  //Input information for Stock Buy Method
            String type = null;
            String symbol = null;
            String name = null;
            double quant = 0;
            double price = 0;
            double bookVal = 0;
            
            //System.out.println("buy with menu" + numInvestments);
            try {
                if (choiceForCB == 1) {
                    type = "stock";
                }
                else {
                    type = "mutualfund";
                }

                symbol = symbolInput.getText();

                name = nameInput.getText();
                quant = Double.parseDouble(quantInput.getText());
                price = Double.parseDouble(priceInput.getText());

                int b = 0;
                
                if (quant <= 0) {
                    outputArea.setText("Error! quantity is < 0.");
                    throw new Exception();
                }
                if (price <= 0) {
                    outputArea.setText("Error! price is < 0");
                    throw new Exception();
                }
                String other;
                    
                if (type.equals("stock")) {  // stock app apple 5 55 will set other to mutualfund
                    other = "mutualfund";
                }
                else {
                    other = "stock";
                }

                for (Investment investment: investments) {    //Check if there is overlap of naming
                    String sym = investment.getSymbol();
                    String typ = investment.getType();

                    if ( sym.equals(symbol) && typ.equals(other)) {
                        //System.out.println("Duplicates Exist");
                        throw new Exception();
                    }
                }

                for (Investment investment: investments) {    //Update Stocks
                    String sym = investment.getSymbol();
                    if (sym.equals(symbol) && type.equals("stock")){
                        investment.updateBuy( price, quant );
                        outputArea.setText("Stock Updated");
                        b = 1;  //Stop from creating new stock
                        break;
                    }
                    if (sym.equals(symbol) && type.equals("mutualfund")){
                        investment.updateBuy( price, quant );
                        outputArea.setText("MutualFund Updated");
                        b = 1;  //Stop from creating new stock
                        break;
                    }
                }
                if (b != 1) {
                    if ( type.equals("stock") ) {   //input stock into ArrayList stock
                        bookVal = quant * price + 9.99;
                        //GraduateStudent newGradStudent = new GraduateStudent ();
                        Stock newStock = new Stock();
                        newStock.set(type, symbol, name, quant, price, bookVal);
                        investments.add(newStock); 
                        outputArea.setText("Stock inserted into list");

                        String []eachWordOfName = name.split("[ ]+");   //Hashmap Input
                        
                        for (i = 0; i < eachWordOfName.length; i++) {
                            String hashInput = eachWordOfName[i].toLowerCase();
                            if (investmentWordHashMap.containsKey(hashInput)) { //update int array
                                ArrayList<Integer> intArrayFromHash = new ArrayList<Integer>();
                                intArrayFromHash = investmentWordHashMap.get(hashInput);
                        
                                intArrayFromHash.add(numInvestments); //Adds stock number to hashMap
                                //(intArrayFromHash);   //TestCase
                                investmentWordHashMap.remove(hashInput);    //Gets rid of HashInput

                                investmentWordHashMap.put(hashInput, intArrayFromHash);
                            }
                            else {
                                ArrayList<Integer> intArrayForHash = new ArrayList<Integer>();
                                intArrayForHash.add(numInvestments);  //Assuming new word

                                //System.out.println(intArrayForHash);   //TestCase
                                
                                investmentWordHashMap.put(hashInput, intArrayForHash);
                                }
                            }
                        }
                        if ( type.equals("mutualfund") ) { //input mutualFund in ArrayList mutualFund
                            bookVal = quant * price;
                            MutualFund newFund = new MutualFund();
                            newFund.set(type, symbol, name, quant, price, bookVal);
                            investments.add(newFund);
                            outputArea.setText("Fund inserted into list");

                            String []thing = name.split("[ ]+");   //Hashmap Input

                            for (i = 0; i < thing.length; i++) {
                                String hashInput = thing[i].toLowerCase();
                                if (investmentWordHashMap.containsKey(hashInput)) { //update int array
                                    ArrayList<Integer> intArrayFromHash = new ArrayList<Integer>();
                                    intArrayFromHash = investmentWordHashMap.get(hashInput);
                            
                                    intArrayFromHash.add(numInvestments); //Adds stock number to hashMap
                                    //System.out.println(intArrayFromHash);   //TestCase
                                    investmentWordHashMap.remove(hashInput);    //Gets rid of HashInput

                                    investmentWordHashMap.put(hashInput, intArrayFromHash);
                                }
                                else {
                                    ArrayList<Integer> intArrayForHash = new ArrayList<Integer>();
                                    intArrayForHash.add(numInvestments);  //Assuming new word
                                    //System.out.println(intArrayForHash);   //TestCase
                                    investmentWordHashMap.put(hashInput, intArrayForHash);
                                }
                            }
                        }   
                        numInvestments++;   //Adds to the number of investments
                    }
                
            } catch (Exception a) {
                //type, symbol, name, quant, price, bookVal
                outputArea.setText("Error! Please Follow Guidelines.\nAll prices and Quant > 0.\nNo Mutual Funds or Stocks the same symbol and eachother.");
            }
            //System.out.println("Type = " + type + "\nSymbol = " + symbol + "\nname = " + name + "\nquant = " + quant + "\nprice = " + price + "\nbookval = " + bookVal);
        }

        if ((choiceFromMenu == 2) && (actionCommand.equals("Sell"))) {
            //System.out.println("Please enter: symbol, actual Price, quantity value - seperated by spaces");
            try{
                //ArrayList<Investment> investments = new ArrayList<Investment>();    //Decleration of ArrayList
                String symbol = symbolInput.getText();
                double price = Double.parseDouble(priceInput.getText());
                double quant = Double.parseDouble(quantInput.getText());
                
                int numStockOrFund = 0;
                int found = 0;
                int broken = 0;

                for (Investment investment: investments) {    //Check Investments
                    String sym = investment.getSymbol();
                    if ( sym.equals( symbol )) {
                        if ( quant > investment.getQuant() ) {
                            throw new Exception();
                        }
                        if (price < 0) {
                            throw new Exception();
                        }
                        investment.updateSell(price, quant);
                        found++;
                        if ( investment.getQuant() == 0 ) {  //all quant sold
                            //Update HashMap
                            String name = investment.getName();

                            String []eachWordOfName = name.split("[ ]+");
                            for (i = 0; i < eachWordOfName.length; i++) {
                                String hashInput = eachWordOfName[i].toLowerCase();
                                ArrayList<Integer> intArrayFromHash = new ArrayList<Integer>();
                                intArrayFromHash = investmentWordHashMap.get(hashInput);
                                
                                for (int j = 0; j < intArrayFromHash.size(); j++) {
                                    if (intArrayFromHash.get(j) == numStockOrFund) {
                                        intArrayFromHash.remove(j);
                                    }
                                    else if (intArrayFromHash.get(j) > numStockOrFund) {
                                        intArrayFromHash.set(j, intArrayFromHash.get(j) - 1);   //replaces element
                                    }
                                }
                                //System.out.println("New ArrayList = " + intArrayFromHash);  //TestCase
                            } 
                            investments.remove (numStockOrFund);
                            break;
                        }
                        break;
                    }
                    numStockOrFund--;
                }
                double roundedGain = Investment.gain;
                roundedGain = Math.round(roundedGain*100.0)/100.0;
                outputArea.setText("Sold " + quant + " of " + symbol + " at " + price + "\nTotal Gain = " + roundedGain);
            } catch (Exception k) {
                outputArea.setText("Error! Be Sure to have Quant equal or less\nthan what is avaible. Be sure that all numbers are > 0.\nAnd Investment Exsits");
            }
        }

        if ((choiceFromMenu == 3)) {    //indexOfStock is what we are currently on 
            if (numInvestments == 0) {
                outputArea.setText("No Investments Currently");
            }
            else {
                try{
                    if (actionCommand.equals("Prev")) { //changes output
                        if (indexOfStock > 0) {
                            indexOfStock--;
                            outputArea.setText("");
                            Investment investment = investments.get(indexOfStock);
                            nameInput.setText(investment.getName());
                            symbolInput.setText(investment.getSymbol());
                            double outputPrice = investment.getPrice();
                            priceInput.setText("" + outputPrice);
                        }
                        else {
                            outputArea.setText("You have Reached the first stock in list");
                        }
                    }
                    if (actionCommand.equals("Next")) { //changes output
                        if (indexOfStock < numInvestments - 1) {
                            indexOfStock++;
                            outputArea.setText("");
                            Investment investment = investments.get(indexOfStock);
                            nameInput.setText(investment.getName());
                            symbolInput.setText(investment.getSymbol());
                            double outputPrice = investment.getPrice();
                            priceInput.setText("" + outputPrice);
                        }
                        else {
                            outputArea.setText("You have Reached the last stock in list");
                        }
                    }
                    if (actionCommand.equals("Save")) {
                        double theInputedPrice = Double.parseDouble(priceInput.getText());
                        if (theInputedPrice < 0) {
                            outputArea.setText("Error! Price Must be greater than or equal to 0");
                            throw new Exception();
                        }
                        Investment investment = investments.get(indexOfStock);
                        investment.update(theInputedPrice);
                        outputArea.setText("Updated: " + investment.getSymbol() + " to " + theInputedPrice);
                    }
                } catch (Exception t) {
                    outputArea.setText("Error! Please make sure all prices are greater than 0.");
                }
            }
        }

        if ((choiceFromMenu == 5) && (actionCommand.equals("Search"))) {
            try {
                int nameIsEmpty = 0;
                
                ArrayList <Integer> outputSearchName = new ArrayList <Integer> ();
                if (nameInput.getText().equals("")) {   //No Input for Name
                    nameIsEmpty = 1;
                }
                else {
                    String []var = nameInput.getText().split("[ ]+");
                    for (i = 0; i < var.length; i++) {
                        if (investmentWordHashMap.containsKey(var[i])) {
                            if (outputSearchName.size() == 0) {
                                outputSearchName = investmentWordHashMap.get(var[i]);
                            }
                            else {
                                ArrayList <Integer> compare = new ArrayList <Integer> ();
                                compare = investmentWordHashMap.get(var[i]);
                                outputSearchName.retainAll(compare);    //retains like elements
                            }
                        }
                    }
                }
                int symbolEmpty = 0;
                ArrayList <Integer> outputSearchSymbol = new ArrayList <Integer> ();
                String symbolInputG = symbolInput.getText();
                if ( symbolInputG.equals("")) {   //No Input
                    symbolEmpty = 1;
                }
                else {
                    int numThing = 0;
                    for (Investment investment: investments) {
                        if (investment.getSymbol().equals(symbolInputG)) {
                            outputSearchSymbol.add(numThing);
                        }
                        numThing++;
                    }
                }

                int rangePriceEmpty = 0;
                ArrayList <Integer> outputSearchPrice = new ArrayList <Integer> ();

                if ( priceInput.getText().equals("") && quantInput.getText().equals("") ) {   //No Input
                    rangePriceEmpty = 1;
                }
                else {   
                    int lowPriceThere = 0;
                    ArrayList <Integer> lowPriceList = new ArrayList <Integer> ();
                    ArrayList <Integer> highPriceList = new ArrayList <Integer> ();
                    int highPriceThere = 0;
                    if (priceInput.getText().equals("") == false) {  //High Price Input
                        highPriceThere = 1;
                        double highPrice = Double.parseDouble(priceInput.getText());
                        if (highPrice <= 0) {
                            throw new Exception();
                        }
                        
                        int numStock = 0;
                        for (Investment investment: investments) {
                            if (investment.getPrice() <= highPrice) {
                                highPriceList.add(numStock);
                                numStock++;
                            }
                        }
                    }
                    //System.out.println("Started from the bottom now we herer");
                    
                    if (((quantInput.getText()).equals("")) == false) {  //Low Price    KEEP GETTING AN ERROR HERE
                    
                        lowPriceThere = 1;
                        double lowPrice = Double.parseDouble(quantInput.getText());
                        if (lowPrice <= 0) {
                            throw new Exception();
                        }
                        int numStock = 0;
                        for (Investment investment: investments) {
                            //System.out.println("lowPrice = " + lowPrice + "investment Price = " + investment.getPrice());
                            if (investment.getPrice() >= lowPrice) {
                                lowPriceList.add(numStock);
                                numStock++;
                            }
                        }
                    }
                    if (lowPriceThere == 1 && highPriceThere == 1) {  //Both low price and high price exist
                        lowPriceList.retainAll(highPriceList);
                        outputSearchPrice = lowPriceList;
                    }
                    if (lowPriceThere == 1 && highPriceThere == 0) {  //only Low Price
                        outputSearchPrice = lowPriceList;
                    }
                    if (lowPriceThere == 0 && highPriceThere == 1) {  //only High Price
                        outputSearchPrice = highPriceList;
                    }
                    //System.out.println("LowPrice ArrayList = " + lowPriceList + "\nHigh Price List = " + highPriceList + "\noutputSearchPriceList = " + outputSearchPrice);
                }
                //Var to keep track of
                //nameIsEmpty symbolEmpty rangePriceEmpty
                //outputSearchName outputSearchSymbol outputSearchPrice
                //System.out.println("nameIsEmpty = " + nameIsEmpty + " symbolEmpty = " + symbolEmpty + "rangePriceEmpty = " + rangePriceEmpty);    //TestCase
                StringBuilder stringOutputFromBuilder = new StringBuilder();
                if ((nameIsEmpty == 1) && (symbolEmpty == 1) && (rangePriceEmpty == 1)) {
                    //System.out.println("All Investments");
                    for (Investment investment: investments) {
                        stringOutputFromBuilder.append(investment.toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if ((nameIsEmpty == 1) && (symbolEmpty == 1)) {    //prints rangePrices
                    for (i = 0; i < outputSearchPrice.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchPrice.get(i)).toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if ((symbolEmpty == 1) && (rangePriceEmpty == 1)) {    //prints names
                //System.out.println("Got here");
                    for (i = 0; i < outputSearchName.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchName.get(i)).toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if ((nameIsEmpty == 1) && (rangePriceEmpty == 1)) {    //prints symbols
                    for (i = 0; i < outputSearchSymbol.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchSymbol.get(i)).toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if (nameIsEmpty == 1) {    //compares symbol and rangePrice
                    outputSearchSymbol.retainAll(outputSearchPrice);
                    for (i = 0; i < outputSearchSymbol.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchSymbol.get(i)).toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if (symbolEmpty == 1) {    //compares name and rangePrice
                    outputSearchName.retainAll(outputSearchPrice);
                    for (i = 0; i < outputSearchName.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchName.get(i)).toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if (rangePriceEmpty == 1) {    //compares name and symbol
                    outputSearchName.retainAll(outputSearchSymbol);
                    for (i = 0; i < outputSearchName.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchName.get(i)).toString()+ "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else if ((nameIsEmpty == 0) && (rangePriceEmpty == 0) && (symbolEmpty == 0)) {  //compares all 3 attributes
                    outputSearchName.retainAll(outputSearchSymbol);
                    outputSearchName.retainAll(outputSearchPrice);
                    for (i = 0; i < outputSearchName.size(); i++) {
                        stringOutputFromBuilder.append(investments.get(outputSearchName.get(i)).toString() + "\n");
                    }
                    outputArea.setText(stringOutputFromBuilder.toString());
                }
                else {
                    outputArea.setText("No Stocks met crietera :(");
                }
               //if symbol is entered there is only 1 possible option
            } catch (Exception l) {
                outputArea.setText("Error! Please be sure that all prices are > 0\n");
            }

        }
    }
}
