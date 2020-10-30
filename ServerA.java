import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;

public class ServerA extends ImplExample {
    public ServerA() {}
    public static void main(String args[]) {
        String objPath = "//localhost:1099/SystemTime";
        try {
            // Instantiating the implementation class
            ImplExample obj = new ImplExample();

            DefaultSystemTime obj1 = new DefaultSystemTime(); 
            // Exporting the object of implementation class  
            SystemTime stub1 = (SystemTime) UnicastRemoteObject.exportObject(obj1, 0);  
            // Binding the remote object (stub) in the registry 
            Naming.bind(objPath, obj1);  
            

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            Rmi stub = (Rmi) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("ServerA", stub);
            map = stub.getData(0);
            stub.setData(map);
            System.out.println("ServerA ready");

            Naming.rebind("ServerA",obj);
            LocateRegistry.createRegistry(1901);
            Naming.rebind("rmi://localhost:1901"+"/pikachu",obj);
            
            LocateRegistry.createRegistry(1902);
            Naming.rebind("rmi://localhost:1902"+"/pikachu",obj);
            
            LocateRegistry.createRegistry(1903);
            Naming.rebind("rmi://localhost:1903"+"/pikachu",obj);
            
            
            LocateRegistry.createRegistry(1904);
            Naming.rebind("rmi://localhost:1904"+"/pikachu",obj);
            

        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}