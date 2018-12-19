package santa;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class SubmissionFileReader {

    private Path filePath;

    private static Function<String, Pair<Integer, Integer>> mapToSubmission = (line) -> {
        String[] s = line.split(",");
        return new Pair<>(
                Integer.parseInt(s[0]),
                Integer.parseInt(s[1])
        );
    };

    public SubmissionFileReader(Path filePath) {
        this.filePath = filePath;
    }

    public List<Pair<Integer,Integer>> load() throws IOException {
        return  Files.readAllLines(filePath).stream().skip(1).map(mapToSubmission).collect(Collectors.toList());
    }
}
