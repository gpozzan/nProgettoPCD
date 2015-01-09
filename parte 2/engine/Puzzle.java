package engine;

import java.nio.file.Path;

public interface Puzzle{
    boolean initialize(Path inputPath);
    String solve();
}
