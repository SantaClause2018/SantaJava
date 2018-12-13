package santa;

import com.google.common.base.Stopwatch;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get(
            ".." + java.io.File.separator +
            "SantaData" + java.io.File.separator +
            "Problem" + java.io.File.separator +
            "gifts.csv"
            //"gifts_reduced.csv"
        );

        GiftReader reader = new GiftReader(filePath);
        List<Gift> gifts = reader.load();

        //DistanceTest.runAccuracyTest(gifts, 1000);
        //DistanceTest.runTimeTest(gifts, gifts.size());

        List<Tour> tours = SlicedRandomTourBuilder.getTours(gifts);

        System.out.println("Done");

        //RandomTourBuilder.getTours(gifts);
    }
}
