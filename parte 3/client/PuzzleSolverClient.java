package client;

import common.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PuzzleSolverClient{
    private static final Charset charset = StandardCharsets.UTF_8;
    static String createInput(Path inputPath){
	String result = "";
	try (BufferedReader reader = Files.newBufferedReader(inputPath, charset)){
		String line = null;
		while((line = reader.readLine()) != null){
		    result += line;
		}
	    } catch(IOException e){
	    System.err.println(e);
	}
	return result;
    }
    public static void main(String[] args){	
    }
}
