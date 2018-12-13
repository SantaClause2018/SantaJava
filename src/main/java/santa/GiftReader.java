package santa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static santa.Gift.NOT_ASSIGNED_TO_TOUR;

public class GiftReader {

    private Path filePath;

    private static Function<String, Gift> mapToGift = (line) -> {
        String[] s = line.split(",");
        return new Gift(
                Integer.parseInt(s[0]),
                Math.toRadians(Double.parseDouble(s[1])),
                Math.toRadians(Double.parseDouble(s[2])),
                Double.parseDouble(s[3]),
                NOT_ASSIGNED_TO_TOUR
        );
    };

    public GiftReader(Path filePath) {
        this.filePath = filePath;
    }

    public List<Gift> load() throws IOException {
        return  Files.readAllLines(filePath).stream().skip(1).map(mapToGift).collect(Collectors.toList());
    }
}
