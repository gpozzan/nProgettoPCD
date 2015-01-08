JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ./engine/Puzzle.java \
	./engine/AbstractPuzzle.java \
        ./engine/PuzzleImpl.java \
        ./engine/PuzzlePiece.java \
	./PuzzleSolver.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) engine/*.class
	$(RM) *.txt
	$(RM) *~
	$(RM) engine/*~
