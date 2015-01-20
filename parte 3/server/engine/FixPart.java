package server.engine;

class FixPart implements Runnable{
    private ConcurrentPuzzleImpl puzzle;
    private int i;
    private int j;
    private int step_i;
    private int step_j;
    private int limit;
    FixPart(ConcurrentPuzzleImpl ap, int i, int j, int k, int q, int l){
	puzzle = ap;
	this.i = i;
	this.j = j;
	step_i = k;
	step_j = q;
        limit = l;	
    }
    public void run(){
	for(int k = 0; k < limit; k++){
	    try{
		puzzle.fixNeighbors(i, j);
	    }catch(Exception e){
		puzzle.addError("Errore nel file di input: non è stato trovato match per qualche id");		
	    }
	    i += step_i;
	    j += step_j;	    
	}
    }
}
