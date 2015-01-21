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
import java.rmi.*;

public class PuzzleSolverClient{
    private static final Charset charset = StandardCharsets.UTF_8;
    static String createInput(Path inputPath){
	String result = "";
	try (BufferedReader reader = Files.newBufferedReader(inputPath, charset)){
		String line = null;
		while((line = reader.readLine()) != null){
		    result += line + "\n";
		}
	    } catch(IOException e){
	    System.err.println(e);
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
	PuzzleSolverServerIntf server = (PuzzleSolverServerIntf)Naming.lookup("//localhost:2020/" + name);
	Puzzle puzzle = server.getPuzzle();
	String correctInput = puzzle.initialize(input);
	if(!correctInput.equals("")){
	    printOutput(correctInput, outputPath);
	    return;
	}
        String output = puzzle.solve();
	printOutput(output, outputPath);
    }
}
