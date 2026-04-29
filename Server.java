import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class Server {
public static void main(String[] args) {
try {
BankAccountImpl bankAccount= new BankAccountImpl();
BankAccount
exportObject=(BankAccount)UnicastRemoteObject.exportObject(bankAccount, 0);
Registry registry = LocateRegistry.createRegistry(52369);
String
url="rmi://"+InetAddress.getLocalHost().getHostAddress()+":52369/BankAccount";
Naming.rebind(url, exportObject);
System.out.println("Waiting for the client's call");
} catch (RemoteException | UnknownHostException | MalformedURLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
System.out.println("Error while trying to connect to BankAccount object");
}
}
}
