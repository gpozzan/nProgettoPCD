JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        ./client/PuzzleSolverClient.java \
	./common/Puzzle.java \
	./common/PuzzleServer.java \
	./server/PuzzleServerImpl.java \
	./server/PuzzleSolverServer.java \
	./server/engine/AbstractPuzzle.java \
	./server/engine/ConcurrentPuzzleImpl.java \
	./server/engine/FixPart.java \
	./server/engine/PuzzlePiece.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) server/*.class
	$(RM) server/engine/*.class
	$(RM) common/*.class
	$(RM) client/*.class
	$(RM) *.txt
	$(RM) *~
	$(RM) server/*~
	$(RM) server/engine/*~
	$(RM) common/*~
	$(RM) client/*~
