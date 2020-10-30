import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.rmi.*;
import java.time.Instant;
import java.time.Clock;
import java.time.Duration;

public class ClientNew{  
   private ClientNew() {}  

    // char[] pro = new char[4];
    static char[] pro = {'A', 'B', 'C'};
    static int sta[] = new int[100];
    static int pri[] = new int[100];
    static int co;
    // pro[0]='A';
    // pro[1]='B';
    // pro[2]='C';
   public static void main(String[] args) {  
    int i;
      try {  
        for(i=0;i<3;i++)
        {
            System.out.println("For process "+pro[i]+":");
            System.out.println("Status:");
            sta[i]=1;
            System.out.println(sta[i]);
            System.out.println("Priority");
            pri[i] = i+1;
            System.out.println(pri[i]);
        }
         
        System.out.println("Process A will initiliaze Election");
        int ele = 1;
         
        elect(ele);
        System.out.println("Final coordinator is "+pro[co-1]);
    
     
    
        // Runnable receiver = new BullyAlgo("receiver");
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 

         // Looking up the registry for the remote object 
         
         Rmi stub1 = (Rmi) registry.lookup("ServerA");
         String position = stub1.getFreeServer();
         String url = "rmi://localhost:"+position;
            System.out.println("Server at port "+url);
            Rmi stub = (Rmi) Naming.lookup(url+"/pikachu");
            Clock clientClock = Clock.systemUTC();
            // Looking up the registry for the remote object 
                  SystemTime stubTime = (SystemTime) registry.lookup("SystemTime");
            
                  // Get current time before calling the server to calculate RTT
                  long start = Instant.now().toEpochMilli();
            
                  // Calling the remote method using the obtained object 
                  long serverTime = stubTime.getSystemTime();
                  System.out.println("Server time "+ serverTime);
            
                  long end = Instant.now().toEpochMilli(); 
            
                  // Calulate RTT
                  long rtt = (end-start)/2;
                  System.out.println("RTT "+ rtt);
            
                  // Calcuate updatedTime to set the client clock with RTT delay
                  long updatedTime = serverTime+rtt;
                  
                  // Calculate offset
                  Duration diff = Duration.ofMillis(updatedTime - clientClock.instant().toEpochMilli());
                  
                  // Set Client clock based on offset to server time
                  clientClock = clientClock.offset(clientClock, diff);
                  System.out.println("\nNew Client Time "+ clientClock.instant().toEpochMilli());
                  // -->
         Scanner myObj = new Scanner(System.in); 
         int choice =0;
            while(choice!=-1){

                String answer;
                System.out.println("Enter "+"\n"+"1:Add Cars"+"\n"+"2:View Car Details"+"\n"+"3:Book Car"+"\n"+"4:See Bookings");
                choice = myObj.nextInt();
                if (choice==1){
                  System.out.println("Enter Car Name");
                  // Calling the remote method using the obtained object 
                  String carName = myObj.next();
                  stub.putCars(carName,0);
                  System.out.println(stub.printMsg()); 
              }
              else if(choice ==2){
                  System.out.println("The Cars available are:");
                  System.out.println(stub.printMsg()); 
              }
              else if (choice==3){
               System.out.println("Enter the User's name");
               String userName = myObj.next();
                  System.out.println("Enter the car name to book");
                  String carName = myObj.next();
                  System.out.println("Enter the date + time");
                  String time = myObj.next();
                  stub.bookCar(userName,carName,time,0);
                  // System.out.println(answer);
              }
              else if (choice==4){
               System.out.println(stub.printMsg()); 
              }
              else if (choice==-1){
                  break;
              }
              else{
                  System.out.println("Wrong Input");
              }

          }
        
         
      } catch (Exception e) {
         System.out.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   }
   static void elect(int ele)
    {
        ele = ele-1;
        co = ele+1;
        for(int i=0;i<3;i++)
        {
            if(pri[ele]<pri[i])
            {
                System.out.println("Election message is sent from "+pro[ele]+" to "+pro[i]);
                if(sta[i]==1)
                    elect(i+1);
            }
        }
    } 
}