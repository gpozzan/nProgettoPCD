package client;

import common.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.rmi.*;
import java.net.MalformedURLException;

public class PuzzleSolverClient{
    private static final Charset charset = StandardCharsets.UTF_8;
    static String createInput(Path inputPath) throws NoSuchFileException{
	String result = "";
	try (BufferedReader reader = Files.newBufferedReader(inputPath, charset)){
		String line = null;
		while((line = reader.readLine()) != null){
		    result += line + "\n";
		}
	    } catch(IOException e){
	    System.err.println(e);
	    return "";
	}
	return result;
    }
    static void printOutput(String output, Path outputPath){
	try(BufferedWriter writer = Files.newBufferedWriter(outputPath, charset)){
		writer.write(output);
	    } catch(IOException e){
	    System.err.println(e);
	}
    }
    public static void main(String[] args) throws Exception{
	if(args.length < 3){
	    System.out.println("Errore: manca qualche input.");
	    return;
	}
	String inputFile = args[0];
	String outputFile = args[1];
	String name = args[2];
	Path inputPath = Paths.get(inputFile);
	Path outputPath = Paths.get(outputFile);
	String input = createInput(inputPath);
	if(input.equals("")) return;	
	PuzzleSolverServerIntf server;
	try{
	    server = (PuzzleSolverServerIntf)Naming.lookup("//localhost:2020/" + name);
	}catch(NotBoundException nbe){
	    System.err.println("Errore: il nome cercato non è stato caricato nel registro.");
	    return;
	}catch(MalformedURLException mue){  
	    return;
	}catch(RemoteException re){
	    System.err.println("Errore: c'è stato un errore nel lookup del riferimento remoto.");
	    return;
	}
	Puzzle puzzle;
	try{
	    puzzle = server.getPuzzle();
	}catch(RemoteException re){
	    System.err.println("Errore: non è stato possibile ottenere un riferimento a un puzzle remoto.");
	    System.err.println(re);
	    return;
	}	
	String correctInput = puzzle.initialize(input);
	
	if(!correctInput.equals("")){
	    System.err.println("Errore: il file di input contiene qualche errore, una spiegazione più dettagliata si potrà trovare nel file di output");
	    printOutput(correctInput, outputPath);
	    return;
	}
	String output;
	try{
	    output = puzzle.solve();
	}catch(RemoteException re){
	    System.err.println("Errore: c'è stato qualche errore di connessione nella risoluzione remota del puzzle.");
	    return;
	}
	printOutput(output, outputPath);
    }
}
