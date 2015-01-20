package server;

import common.*;
import server.engine.*;
import java.rmi.*;
import java.rmi.server.*;

public class PuzzleSolverServer extends UnicastRemoteObject implements PuzzleSolverServerIntf{
    PuzzleSolverServer() throws RemoteException {}
    
    public Puzzle getPuzzle() throws RemoteException{
	Puzzle p = new ConcurrentPuzzleImpl();
	return p;
    }
    
    public static void main(String[] args){
	if(args.length == 0){
	    System.err.println("Errore: non è stato fornito il nome del server.");
	    return;
	}
	
    }
}
