package engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentPuzzleImpl extends AbstractPuzzle{
    private static ExecutorService exec;
    public String solve(){
	PuzzlePiece first = getFirst();	
	setMatrix(0,0,getFirst());
	int i = 0;
	int j = 0;	
	fixNeighbors(i, j);
	if(getNCol() <= 2){
	    int n_row = getNRow();
	    setMatrix(n_row-1, 0, getBottomLeft());
	    fixNeighbors(n_row-1, 0);
	    int lim1 = n_row / 2;
	    int lim2 = (n_row / 2) + (n_row % 2);        
	    exec = Executors.newFixedThreadPool(2);
	    FixPart fp1 = new FixPart(this, i, j, 1, 0, lim1);
	    FixPart fp2 = new FixPart(this, n_row-1, j, -1, 0, lim2);
	    exec.execute(fp1);
	    exec.execute(fp2);
	}
	else if(getNRow() <= 2){
	    int n_col = getNCol();
	    setMatrix(0, n_col-1, getTopRight());
	    fixNeighbors(0, n_col-1);
	    int lim1 = n_col / 2;
	    int lim2 = (n_col / 2) + (n_col % 2);
	    exec = Executors.newFixedThreadPool(2);
	    FixPart fp1 = new FixPart(this, i, j, 0, 1, lim1);
	    FixPart fp2 = new FixPart(this, i, n_col-1, 0, -1, lim2);
	    exec.execute(fp1);
	    exec.execute(fp2);
	}
	else{
	    exec = Executors.newCachedThreadPool();
	    for(; i < getNRow(); i++){
		fixNeighbors(i,j);
		if(i%3 == 1){
		    FixPart fp = new FixPart(this, i, j, 0, 1, getNCol());
		    exec.execute(fp);
		}
	    }
	    i--;
	    if(i%3 == 0) {
		FixPart fp = new FixPart(this, i, j, 0, 1, getNCol());
		exec.execute(fp);
	    }
	}	    
	exec.shutdown();
	try{	
	    exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	} catch(InterruptedException ie) {}	    
	
	return print();	
    }
}
