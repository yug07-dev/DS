import java.rmi.RemoteException;
public class BankAccountImpl implements BankAccount{
private double amount=50;
@Override
public void deposit(int amount) throws RemoteException {
// TODO Auto-generated method stub
this.amount+=amount;
}
@Override
public void withdraw(int amount) throws RemoteException {
// TODO Auto-generated method stub
this.amount-=amount;
}
@Override
public double getBalance() throws RemoteException {
// TODO Auto-generated method stub
return amount;
}
}