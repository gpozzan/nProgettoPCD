package common;

import java.rmi.*;

public interface PuzzleServer extends Remote{
    Puzzle getPuzzle() throws RemoteException;
}
