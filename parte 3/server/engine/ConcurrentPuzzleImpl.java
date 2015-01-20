package server.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.rmi.*;

public class ConcurrentPuzzleImpl extends AbstractPuzzle{
    private static ExecutorService exec;
    private boolean errorFlag = false;
    public ConcurrentPuzzleImpl() throws RemoteException {}
    public void setFlag(boolean f){
	/*Qualora qualcosa andasse storto nella risoluzione del puzzle
	  verrà impostata a true questa flag.*/
	errorFlag = f;
    }
    public String solve() throws RemoteException{
	try{
	    PuzzlePiece first = getFirst();	
	    setMatrix(0,0,getFirst());
	    int i = 0;
	    int j = 0;	
	    fixNeighbors(i, j);
	    if(getNCol() <= 2){
		/*Caso limite: il puzzle viene risolto da due thread che
		 partono dall'angolo in alto a sinistra e in basso a
		 sinistra del puzzle.*/
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
		/*Caso limite: il puzzle viene risolto da due thread che
		  partono dall'angolo in alto a sinistra e in alto a
		  destra del puzzle.*/
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
		/*Se ho più di due colonne posso spostare il "cursore"
		  degli elementi del puzzle a destra di una casella,
		  questo mi permette di sfruttare appieno le informazioni
		  sui vicini dei PuzzlePiece e riempire tre colonne con
		  una sola passata.*/
		j += 1;
		/*Imposto il numero di iterazioni per i FixPart al numero
		  di colonne meno 2 poiché partiranno dalla terza colonna*/
		int lim_col = getNCol() - 2;
		exec = Executors.newCachedThreadPool();
		for(; i < getNRow(); i++){
		    /*Scendendo verticalmente risolvo le tre colonne più a
		      sinistra del puzzle.*/
		    fixNeighbors(i,j);
		    if(i%3 == 1){
			/*Ogni tre righe, a partire dalla seconda, faccio
			  partire un thread che sistema il gruppo di righe.*/
			FixPart fp = new FixPart(this, i, j+1, 0, 1, lim_col);
			exec.execute(fp);
		    }
		}
		/*In certi casi l'ultima riga potrebbe rimanere non risolta,
		  sistemo tornando indietro di un passo e controllando se sia
		  il caso di far partire un ultimo thread.*/
		i--;
		if(i%3 == 0) {
		    FixPart fp = new FixPart(this, i, j+1, 0, 1, lim_col);
		    exec.execute(fp);
		}
	    }
	}catch(Exception e){
	    System.err.println("Errore nel file di input: non è stato trovato match per qualche id");
	    setFlag(true);
	}
	finally{
	    exec.shutdown();
	    try{	
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	    } catch(InterruptedException ie) {}	    
	}
	if(errorFlag == true) return "";
	return print();	
    }
}
