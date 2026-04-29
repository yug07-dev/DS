import java.rmi.Remote;
import java.rmi.RemoteException;
public interface BankAccount extends Remote {
void deposit (int amount) throws RemoteException;
void withdraw (int amount) throws RemoteException;
double getBalance() throws RemoteException;
}
