package common;

import java.rmi.*;

public interface PuzzleSolverServerIntf extends Remote{
    Puzzle getPuzzle() throws RemoteException;
}
