import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

// Creating Remote interface for our application 
interface Rmi extends Remote {
   HashMap<String, Integer> getData(int serverId) throws RemoteException;
   HashMap<String,Integer> returnData() throws RemoteException;
   void setData(HashMap<String, Integer> a) throws RemoteException;

   public String getFreeServer() throws RemoteException;

   String printMsg() throws RemoteException;

   void putCars(String car, int flag) throws RemoteException;

   void replicatePut(String car, int value) throws RemoteException;

   void bookingPut(String car, ArrayList list) throws RemoteException;

   void bookCar(String userName, String carName, String time, int flag) throws RemoteException;
}

public class ImplExample implements Rmi {
   private static Integer position = 1;
   private static Integer limit = 0;
   static HashMap<String, Integer> map = new HashMap<>();
   static HashMap<String, ArrayList> bookingmap = new HashMap<>();
   static int i = 0;
   ArrayList<String> arrayOfCurrentCars = new ArrayList<String>();

   // Implementing the interface method
   public String printMsg() {
      return map.toString() + bookingmap.toString();
   }

   public void replicatePut(String car, int value) {
      System.out.println("printing value" + value);
      map.put(car, value);
      this.i += 1;
      System.out.println(map);
   }

   public void bookingPut(String car, ArrayList list) {
      // System.out.println("printing value"+value);
      bookingmap.put(car, list);
      this.i += 1;
      System.out.println(bookingmap);
   }

   public void putCars(String car, int flag) {
      Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

      // flag to store result
      try{
      boolean isKeyPresent = false;
      if(arrayOfCurrentCars.contains(car)) {
         System.out.println("Same car is being added currently. Please come back later.");
      }
      else {
         arrayOfCurrentCars.add(car);
         System.out.println(arrayOfCurrentCars);
      }
      Thread.sleep(6000);  ///Add delay because in real life situation there is a delay sometimes 
      

      // Iterate over the HashMap
      while (iterator.hasNext()) {

         // Get the entry at this iteration
         Map.Entry<String, Integer> entry = iterator.next();

         // Check if this key is the required key
         if (car == entry.getKey()) {

            isKeyPresent = true;
         }
      }
      System.out.println("Checking if present" + isKeyPresent);

      int count = map.containsKey(car) ? map.get(car) : 0;
      map.put(car, count + 1);
      // map.put(car,i);

      // else{
      // map.put(car,1);
      // }
      // map.put(car,i);
      System.out.println("I am here");
      System.out.println(car);
      System.out.println(count);
      this.i += 1;
      System.out.println(map);
      if (flag == 0) {
         try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Rmi stub = (Rmi) registry.lookup("ServerB");
            // stub.putBooks(book,1);
            stub.replicatePut(car, count + 1);
            System.out.println(stub.printMsg());
         } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
         }
         try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Rmi stub = (Rmi) registry.lookup("ServerC");
            // stub.putBooks(book,2);
            stub.replicatePut(car, count + 1);
            System.out.println(stub.printMsg());
         } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
         }
      } else if (flag == 1) {
         try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Rmi stub = (Rmi) registry.lookup("ServerA");
            stub.replicatePut(car, count + 1);
            System.out.println(stub.printMsg());
         } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
         }
         try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Rmi stub = (Rmi) registry.lookup("ServerC");
            stub.replicatePut(car, count);
            System.out.println(stub.printMsg());
         } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
         }
      } else {
         try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Rmi stub = (Rmi) registry.lookup("ServerA");
            stub.replicatePut(car, count + 1);
            System.out.println(stub.printMsg());
         } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
         }
         try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(null);
            // Looking up the registry for the remote object
            Rmi stub = (Rmi) registry.lookup("ServerB");
            stub.replicatePut(car, count + 1);
            System.out.println(stub.printMsg());
         } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
         }
      }
      arrayOfCurrentCars.remove(car);
   }
   catch(InterruptedException e1) {
      Thread.currentThread().interrupt();
      System.out.println( e1.toString());
   }
   catch(Exception e) {
      e.printStackTrace();
      System.out.println(e.toString());
   }

   }

   public void bookCar(String userName, String carName, String time, int flag) {

      int count = map.containsKey(carName) ? map.get(carName) : 0;
      if (count == 0) {
         System.out.println("Sorry car not available");
      } else {
         map.put(carName, count - 1);
         ArrayList<String> list = new ArrayList<String>();
         list.add(userName);
         list.add(time);
         bookingmap.put(carName, list);
         // map.put(car,i);

         // else{
         // map.put(car,1);
         // }
         // map.put(car,i);
         System.out.println("I am here");
         System.out.println(carName);
         System.out.println(count);
         this.i += 1;
         System.out.println(map);
         System.out.println(bookingmap);
         if (flag == 0) {
            try {
               // Getting the registry
               Registry registry = LocateRegistry.getRegistry(null);
               // Looking up the registry for the remote object
               Rmi stub = (Rmi) registry.lookup("ServerB");
               // stub.putBooks(book,1);
               stub.replicatePut(carName, count - 1);
               stub.bookingPut(carName, list);
               System.out.println(stub.printMsg());
            } catch (Exception e) {
               System.out.println("Client exception: " + e.toString());
               e.printStackTrace();
            }
            try {
               // Getting the registry
               Registry registry = LocateRegistry.getRegistry(null);
               // Looking up the registry for the remote object
               Rmi stub = (Rmi) registry.lookup("ServerC");
               // stub.putBooks(book,2);
               stub.replicatePut(carName, count - 1);
               stub.bookingPut(carName, list);
               System.out.println(stub.printMsg());
            } catch (Exception e) {
               System.out.println("Client exception: " + e.toString());
               e.printStackTrace();
            }
         } else if (flag == 1) {
            try {
               // Getting the registry
               Registry registry = LocateRegistry.getRegistry(null);
               // Looking up the registry for the remote object
               Rmi stub = (Rmi) registry.lookup("ServerA");
               stub.replicatePut(carName, count - 1);
               stub.bookingPut(carName, list);
               System.out.println(stub.printMsg());
            } catch (Exception e) {
               System.out.println("Client exception: " + e.toString());
               e.printStackTrace();
            }
            try {
               // Getting the registry
               Registry registry = LocateRegistry.getRegistry(null);
               // Looking up the registry for the remote object
               Rmi stub = (Rmi) registry.lookup("ServerC");
               stub.replicatePut(carName, count - 1);
               stub.bookingPut(carName, list);
               System.out.println(stub.printMsg());
            } catch (Exception e) {
               System.out.println("Client exception: " + e.toString());
               e.printStackTrace();
            }
         } else {
            try {
               // Getting the registry
               Registry registry = LocateRegistry.getRegistry(null);
               // Looking up the registry for the remote object
               Rmi stub = (Rmi) registry.lookup("ServerA");
               stub.replicatePut(carName, count - 1);
               stub.bookingPut(carName, list);
               System.out.println(stub.printMsg());
            } catch (Exception e) {
               System.out.println("Client exception: " + e.toString());
               e.printStackTrace();
            }
            try {
               // Getting the registry
               Registry registry = LocateRegistry.getRegistry(null);
               // Looking up the registry for the remote object
               Rmi stub = (Rmi) registry.lookup("ServerB");
               stub.replicatePut(carName, count - 1);
               stub.bookingPut(carName, list);
               System.out.println(stub.printMsg());
            } catch (Exception e) {
               System.out.println("Client exception: " + e.toString());
               e.printStackTrace();
            }
         }

      }
   }
   public HashMap<String, Integer> getData(int serverId) {
      HashMap<String, Integer> nmap = new HashMap<>();
      if (serverId == 0) {
         try {
            Registry registry = LocateRegistry.getRegistry(null);
            Rmi stub = (Rmi) registry.lookup("ServerB");
            nmap = stub.returnData();

         } catch (Exception e) {
            try {
               Registry registry = LocateRegistry.getRegistry(null);
               Rmi stub = (Rmi) registry.lookup("ServerC");
               nmap = stub.returnData();
            }

            catch (Exception er) {
               System.out.println("No servers active");
            }

         }
      }

      else if (serverId == 1) {
         try {
            Registry registry = LocateRegistry.getRegistry(null);
            Rmi stub = (Rmi) registry.lookup("ServerA");
            nmap = stub.returnData();

         } catch (Exception e) {
            try {
               Registry registry = LocateRegistry.getRegistry(null);
               Rmi stub = (Rmi) registry.lookup("ServerC");
               nmap = stub.returnData();
            }

            catch (Exception er) {
               System.out.println("No servers active");
            }

         }
      } else if (serverId == 2) {
         try {
            Registry registry = LocateRegistry.getRegistry(null);
            Rmi stub = (Rmi) registry.lookup("ServerA");
            nmap = stub.returnData();

         } catch (Exception e) {
            try {
               Registry registry = LocateRegistry.getRegistry(null);
               Rmi stub = (Rmi) registry.lookup("ServerB");
               nmap = stub.returnData();
            }

            catch (Exception er) {
               System.out.println("No servers active");
            }

         }
      }
      return nmap;
   }

   public void setData(HashMap<String, Integer> a) {
      map=a;
   }
   public HashMap<String,Integer> returnData() {
      return map;
   }


   // map.put(car,i);
   // this.i+=1;
   // System.out.println(map);
   // if (flag==0){
   // try {
   // // Getting the registry
   // Registry registry = LocateRegistry.getRegistry(null);
   // // Looking up the registry for the remote object
   // Rmi stub = (Rmi) registry.lookup("ServerB");
   // // stub.putBooks(book,1);
   // stub.replicatePut(car,1);
   // System.out.println(stub.printMsg());
   // } catch (Exception e) {
   // System.out.println("Client exception: " + e.toString());
   // e.printStackTrace();
   // }
   // try {
   // // Getting the registry
   // Registry registry = LocateRegistry.getRegistry(null);
   // // Looking up the registry for the remote object
   // Rmi stub = (Rmi) registry.lookup("ServerC");
   // // stub.putBooks(book,2);
   // stub.replicatePut(car,2);
   // System.out.println(stub.printMsg());
   // } catch (Exception e) {
   // System.out.println("Client exception: " + e.toString());
   // e.printStackTrace();
   // }
   // }
   // else if (flag==1){
   // try {
   // // Getting the registry
   // Registry registry = LocateRegistry.getRegistry(null);
   // // Looking up the registry for the remote object
   // Rmi stub = (Rmi) registry.lookup("ServerA");
   // stub.replicatePut(car,0);
   // System.out.println(stub.printMsg());
   // } catch (Exception e) {
   // System.out.println("Client exception: " + e.toString());
   // e.printStackTrace();
   // }
   // try {
   // // Getting the registry
   // Registry registry = LocateRegistry.getRegistry(null);
   // // Looking up the registry for the remote object
   // Rmi stub = (Rmi) registry.lookup("ServerC");
   // stub.replicatePut(car,2);
   // System.out.println(stub.printMsg());
   // } catch (Exception e) {
   // System.out.println("Client exception: " + e.toString());
   // e.printStackTrace();
   // }
   // }
   // else {
   // try {
   // // Getting the registry
   // Registry registry = LocateRegistry.getRegistry(null);
   // // Looking up the registry for the remote object
   // Rmi stub = (Rmi) registry.lookup("ServerA");
   // stub.replicatePut(car,0);
   // System.out.println(stub.printMsg());
   // } catch (Exception e) {
   // System.out.println("Client exception: " + e.toString());
   // e.printStackTrace();
   // }
   // try {
   // // Getting the registry
   // Registry registry = LocateRegistry.getRegistry(null);
   // // Looking up the registry for the remote object
   // Rmi stub = (Rmi) registry.lookup("ServerB");
   // stub.replicatePut(car,1);
   // System.out.println(stub.printMsg());
   // } catch (Exception e) {
   // System.out.println("Client exception: " + e.toString());
   // e.printStackTrace();
   // }
   // }

   @Override
   public String getFreeServer() throws RemoteException {
      // TODO Auto-generated method stub
      Map hmap = new HashMap();

      hmap.put(1, "1901");
      hmap.put(2, "1902");
      hmap.put(3, "1903");
      hmap.put(4, "1904");

      if (limit >= 5) {
         if (position > 4) {
            System.out.println(position);
            position = 1;
            limit = 0;
         } else {

            position++;
         }
      }
      limit = limit + 1;

      System.out.println(position);
      String target = (String) hmap.get(position);
      return target;
   }
}
