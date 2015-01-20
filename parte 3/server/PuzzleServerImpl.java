package server;

import common.*;
import server.engine.*;
import java.rmi.server.*;
import java.rmi.*;

class PuzzleServerImpl extends UnicastRemoteObject implements PuzzleServer{
    PuzzleServerImpl() throws RemoteException {}
    public Puzzle getPuzzle() throws RemoteException{
	Puzzle p = new ConcurrentPuzzleImpl();
	return p;
    }
}
