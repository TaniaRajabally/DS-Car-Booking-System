import java.rmi.Remote; 
import java.rmi.RemoteException;  


// Creating Remote interface for our application 
public interface SystemTime extends Remote {  
   long getSystemTime() throws RemoteException;  
} 