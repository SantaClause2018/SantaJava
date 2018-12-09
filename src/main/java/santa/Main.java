package santa;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get(
            ".." + java.io.File.separator +
            "SantaData" + java.io.File.separator +
            "Problem" + java.io.File.separator +
            "gifts.csv"
        );
        GiftReader reader = new GiftReader(filePath);
        List<Gift> gifts = reader.load();
        PositionNet net = new PositionNet();
        for (Gift gift : gifts) {
            net.add(gift);
        }

        net.printMap();

        DistanceTest.runAccuracyTest(gifts, 1000);
        DistanceTest.runTimeTest(gifts, gifts.size());
        System.out.println("Done");
    }       
}
