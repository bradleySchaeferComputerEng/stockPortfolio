/*
Name: Bradley Schaefer
Student ID: 1126385
A1: CIS2420
To compile 
1) javac eportfolio/*.java  
2) java eportfolio.Portfolio file.txt
*/

EXPLANATION
(1): This program allows users to keep track of investments of type Mutual Funds and Stocks. Each Object will contain a symbol, name, quantity, price, book value. These will be updated as the user buys stock, sells stock, updates prices. The stocks and mutual funds will be kept using the same ArrayLists making it easy to add and take away elements. I have added a GUI interface to allow users to do these tasks easily with more visual appeal. It has the same functionality as the code just outputs on outputArea and other JTextAreas. I have implemented the HashMap so that the search method will find investments easily and have updated the information for the Hashmap when investments are inputed and fully sold. I also added the colour Green so that the top tab would slightly be colored green to symbolize money. When the program is exited (either by clicking exit or choosing quit from the drop down menu), if a file was inputed using a commandline argument all the stock will then be re uploaded to that file.

(2): Assumptions are that The user will only input stock or mutualFund. Using an inheritance I was able to get rid of redundent code and simplify my soloution. This allowed for the use of one arrayList and easier access to all the investments. Other assumptions are that the user wont want to upload any other information for a stock or mutual fund. Since the code can't adapt to more things for the user to add they are limited to the functionallity provided. If the user inputs a file the investments will be uploaded to the list automaticlly. If the user does not add a command line argument for the file no investments will be uploaded at compalation time. 

(3): Any user can run this file by using
1) javac eportfolio/*.java
2) java eportfolio.Portfolio foldername/filename.txt   //file is optional

Testing
When Testing this I tested all possible entrys the user could input. When there is a string input such as inputing names or symbols the program will allow any input since all inputs are valid. When there is a number input such as Low Price, High Price, Price, and quantity the program will only accept positive amounts. And, when the user Inputs quantity for selling it will check to make sure the input is less than what is owned already. If the user Inputs anything that is not acceptable such as a string for a number input it will throw an exception and allow the user to retry without giving an error in the terminal. I also tested my code with a file called file.txt inside my eportfolio folder so my command line argument was eportfolio/file.txt.

file.txt 
type = "stock"
symbol = "AAPL"
name = "Apple Inc."
quantity = "500.0"
price = "0.3"
bookValue = "55049.99"

type = "stock"
symbol = "AAPL"
name = "Apple cmp."
quantity = "500.0"
price = "142.23"
bookValue = "55049.99"

type = "mutualfund"
symbol = "SSETX"
name = "BNY Mellon Groth Fund Class I"
quantity = "450.0"
price = "144.0"
bookValue = "23967.0"

(5): Imporvments: I would like to add more visual appeal for the user. I was able to make all corrent functionallity so I am very proud of that but would like to add better colors and even some visuals so that user enjoys the expierence more. My code was also a little messy so I would like to simplify that and make it more "neat". I think this is a result of making this when I was first learning Java. If I were to make this now it would be more consise and make a little more sense of what I was doing in cerin areas. I could alse use exception handeling for certin errors to make the output more specific. However, all this being said I am very proud of the final product. I didnt do very well on the past assignments but was able to fix all of my mistakes from what I can see and was excited to see it all done!