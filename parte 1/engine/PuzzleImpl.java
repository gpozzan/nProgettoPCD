package engine;

public class PuzzleImpl extends AbstractPuzzle{
    
    public String solve(){
	try{
	    PuzzlePiece first = getFirst();
	    if(first == null) throw new Exception();
	    setMatrix(0,0,getFirst());
	    int i = 0;
	    int j = 0;
	    fixNeighbors(i,j);	    
	    if(getNCol() > 1)
		j += 1;	
	    for(; i < getNRow() ; i++){	        
		fixNeighbors(i,j);
		if(i%3 == 1) fixRow(i, j);
	    }
	    i--;
	    if(i%3 == 0) fixRow(i, j);
	}catch(Exception e){
	    System.err.println("Errore nel file di input: manca il match per qualche id");
	    return "";
	}
	return print();
    }
}
