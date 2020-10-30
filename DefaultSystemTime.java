import java.time.Instant;

// Implementing the remote interface 
public class DefaultSystemTime implements SystemTime {  
   // Implementing the interface method 
   public long getSystemTime() {  
      // Calculating Epoch time on server
      long time = Instant.now().toEpochMilli();
      System.out.println("This is the current time on the server, "+ time);
      return time;  
   }  
}