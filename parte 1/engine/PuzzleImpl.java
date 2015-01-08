package engine;

public class PuzzleImpl extends AbstractPuzzle{
    
    public String solve(){        
        setMatrix(0,0,getFirst());
	int i = 0;
	int j = 0;
	fixNeighbors(i,j);
	if(getNCol() > 1)
	    j += 1;	
	if(getNRow() > 1)
	    i += 1;
	for(; i < getNRow() ; i++){	        
	    fixNeighbors(i,j);
	    if(i%3 == 1) fixRow(i, j);
	}
	i--;
	if(i%3 == 0) fixRow(i, j);
		
	return print();
    }
}
