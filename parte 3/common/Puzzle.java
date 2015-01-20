package common;

import java.rmi.*;

public interface Puzzle extends Remote{
    String initialize(String input) throws RemoteException;
    String solve() throws RemoteException;
}
