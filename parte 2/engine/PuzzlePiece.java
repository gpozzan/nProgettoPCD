package engine;

class PuzzlePiece{
    private final String id;
    private final String character;
    private final String[] neighbors = new String[4];
    PuzzlePiece(String i, String ch, String n, String e, String s, String w){
	id=i;
	character=ch;
	neighbors[0]=n; neighbors[1]=e; 
	neighbors[2]=s; neighbors[3]=w;
    }
    String[] getNeighbors(){
	return neighbors;
    }
    public String toString(){
	return character;
    }
}
