package engine;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract class AbstractPuzzle implements Puzzle{
    private final Charset charset = StandardCharsets.UTF_8;
    private HashMap<String, PuzzlePiece > pieceIndex = new HashMap<String, PuzzlePiece>();
    private PuzzlePiece[][] matrix;
    private PuzzlePiece first;
    private int n_col = 0;
    private int n_row = 0;
    public void initialize(Path inputPath){
	try (BufferedReader reader = Files.newBufferedReader(inputPath, charset)){
		String line = null;
		while((line = reader.readLine()) != null){
		    String[] pt = line.split("\t");
		    if(pt[1].length() > 1){
			//err: stringa al posto di carattere
		    }
		    PuzzlePiece pp = new PuzzlePiece(pt[0], pt[1], pt[2], pt[3], pt[4], pt[5]);
		    if(pt[2].equals("VUOTO") && pt[5].equals("VUOTO")) first = pp;
		    if(pt[2].equals("VUOTO")) n_col++;
		    if(pt[5].equals("VUOTO")) n_row++;
		    pieceIndex.put(pt[0], pp);		    
		}
		matrix = new PuzzlePiece[n_row][n_col];		
	    } catch(IOException e){
	    System.err.println(e);
	}		
    }
    PuzzlePiece getFirst(){
	return first;
    }
    PuzzlePiece getPiece(String id){
	return pieceIndex.get(id);
    }
    int getNRow(){
	return n_row;
    }
    int getNCol(){
	return n_col;
    }
    void setMatrix(int i, int j, PuzzlePiece pp){
	matrix[i][j] = pp;
    }  
    void fixNeighbors(int i, int j){
	String[] neighbors = matrix[i][j].getNeighbors();	
	if(!neighbors[0].equals("VUOTO") && matrix[i-1][j] == null) matrix[i-1][j] = getPiece(neighbors[0]);
	if(!neighbors[1].equals("VUOTO") && matrix[i][j+1] == null) matrix[i][j+1] = getPiece(neighbors[1]);
	if(!neighbors[2].equals("VUOTO") && matrix[i+1][j] == null) matrix[i+1][j] = getPiece(neighbors[2]);
	if(!neighbors[3].equals("VUOTO") && matrix[i][j-1] == null) matrix[i][j-1] = getPiece(neighbors[3]);
    }
    void fixRow(int i, int j){
	for(; j < n_col; j++){
	    fixNeighbors(i, j);
	}
    }
    String print(){
	String resTab = "";
	String resPhr = "";
	for(int i = 0; i < n_row; i++){
	    String temp = "";
	    for(int j = 0; j < n_col; j++){
		temp += matrix[i][j];		
	    }
	    resTab += temp + "\n";
	    resPhr += temp;	    
	}
	return resPhr + "\n" + "\n" + resTab + "\n" + n_row + " " + n_col;
    }
    abstract public String solve();
    
}
