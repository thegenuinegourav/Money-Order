![Money Order Logo](/app/src/main/res/mipmap-hdpi/ic_launcher.png)  
#Money Order  
  
####__*An Android App that is capable of doing OFFLINE PAYMENT transactions.*__  
  
  
###Motivation  
Though at Jio it is ensured that all Indians are connected to data at all times but there would be scenarios where the user could not be connected to the Internet owing to many reasons like Basement Parking, Basement Stores, while in a flight, etc.  
  
As an individual we cannot avoid such scenarios and when we need to make payment, only cash transaction comes to our rescue.After demonetization, the whole focus is to how to eradicate cash transaction from the system and hence the need of Offline Payments becomes really important.  
  
  
  
###Description:ledger:    
Money Order is an android application designed as a centralised server, aiming to solve the problem of Offline Payments. It uses the GSM networks to carry out the transactions. Several clients (while offline) can make transactions via this centralised server android app.  
  
  
###How it works:question:  
Suppose Client A wants to send 50 rupees to Client B provided both are offline.  
Step 1: Client A sends the SMS to a number (associated with the device in which Money Order is installed). 
Step 2: SMS Format should be as follows:
        MO<space><Reciever Phone Number><space><Amount to transfer>
Step 3: Money Order will recieve this SMS, complete corresponding transaction and sends back successfull/failure SMS regarding the transaction to both sender and reciever.     
  
  
###Future
Money Order is a software which is especially designed for organisations (not for an individual). An organisation can install this app with any helpline number (lets say, 5255). Clients then can send SMS to 5255 with proper above format to make offline transactions.  
  
  
  
### Development  
  
Want to contribute? **:pencil:**  
  
To fix a bug or enhance an existing module, follow these steps:  
  
1. Fork the repo
2. Create a new branch (`git checkout -b exciting-stuff`)
3. Make the appropriate changes in the files
4. Add changes to reflect the changes made
5. Commit your changes (`git commit -am 'exciting-stuff!!'`)
6. Push to the branch (`git push origin exciting-stuff`)
7. Create a Pull Request  
  
  
### Interested?  
  
If you find a bug (the website couldn't handle the query and / or gave irrelevant results), kindly open an issue [here](https://github.com/thegenuinegourav/Money-Order/issues/new) by including your search query and the expected result.  
  
If you'd like to request a new functionality, feel free to do so by opening an issue [here](https://github.com/thegenuinegourav/Money-Order/issues/new) including some sample queries and their corresponding results.  
  
