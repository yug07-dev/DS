import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
public class Client {
public final static int amount=10;
public static void main(String[] args) {
try {
String url= "rmi://" +InetAddress.getLocalHost().getHostAddress() +
":52369/BankAccount";
BankAccount bankAccount =(BankAccount) Naming.lookup(url);
System.out.println("Current amount in the bankaccount"+bankAccount.getBalance());
System.out.println("Deposit-----> "+ amount);
bankAccount.deposit(amount);
System.out.println("Amount after the deposit"+ bankAccount.getBalance());
System.out.println("Withdraw ---- > "+amount);
bankAccount.withdraw(amount);
System.out.println("Amount after the withdraw "+ bankAccount.getBalance());
}
catch (UnknownHostException | MalformedURLException | RemoteException |
NotBoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}