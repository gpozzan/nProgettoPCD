package engine;

import java.nio.file.Path;

public interface Puzzle{
    void initialize(Path inputPath);
    String solve();
}
