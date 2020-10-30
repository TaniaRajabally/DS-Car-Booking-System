import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerB extends ImplExample {
    public ServerB() {}
    public static void main(String args[]) {
        try {

            // Clock syncronisation using Cristian's Algo
            DefaultSystemTime obj1 = new DefaultSystemTime(); 
            // Exporting the object of implementation class  
            SystemTime stub1 = (SystemTime) UnicastRemoteObject.exportObject(obj1, 0);  
            // Binding the remote object (stub) in the registry 
            //Naming.bind(objPath, obj1);  

            // Instantiating the implementation class
            ImplExample obj = new ImplExample();

            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            Rmi stub = (Rmi) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();

            registry.rebind("ServerB", stub);
            map = stub.getData(1);
            stub.setData(map);
            System.out.println("ServerB ready");

        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}