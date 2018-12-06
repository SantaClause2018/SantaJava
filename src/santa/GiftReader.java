package santa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GiftReader {

    private Path filePath;

    private static Function<String, Gift> mapToGift = (line) -> {
        String[] s = line.split(",");
        Gift gift  = new Gift();
        gift.setId(Integer.parseInt(s[0]));
        gift.setLatitude(Double.parseDouble(s[1]));
        gift.setLongitude(Double.parseDouble(s[2]));
        gift.setWeight(Double.parseDouble(s[3]));
        return gift;
    };

    public GiftReader(Path filePath) {
        this.filePath = filePath;
    }

    public List<Gift> load() throws IOException {
        return  Files.readAllLines(filePath).stream().skip(1).map(mapToGift).collect(Collectors.toList());
    }
}
