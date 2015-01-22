package server;

import common.*;
import server.engine.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.MalformedURLException;

public class PuzzleSolverServer extends UnicastRemoteObject implements PuzzleSolverServerIntf{
    PuzzleSolverServer() throws RemoteException {}
    
    public Puzzle getPuzzle() throws RemoteException{
	Puzzle p = new ConcurrentPuzzleImpl();
	return p;
    }
    
    public static void main(String[] args) throws Exception{
	if(args.length == 0){
	    System.err.println("Errore: non è stato fornito il nome del server.");
	    return;
	}
	String name = args[0];
	try{
	    LocateRegistry.createRegistry(2020);
	}catch(RemoteException re){
	    System.err.println("Errore: c'è stato un errore nella creazione del registro.");
	    return;
	}
	PuzzleSolverServer server = new PuzzleSolverServer();
	try{
	    Naming.rebind("//localhost:2020/"+ name, server);
	}catch(MalformedURLException mue){
	    System.err.println("Errore: il nome fornito sembra dare problemi.");
	    return;
	}catch(RemoteException re){
	    System.err.println("Errore: c'è stato un errore nel contattare il registro.");
	    return;
	}
    }
}
