package santa;

import com.google.common.base.Stopwatch;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        Stopwatch stopwatch = Stopwatch.createStarted();

        Path filePath = Paths.get(
            ".." + java.io.File.separator +
            "SantaData" + java.io.File.separator +
            "Problem" + java.io.File.separator +
            "gifts.csv"
            //"gifts_reduced.csv"
        );

        int i;
        int k;
        GiftReader reader = new GiftReader(filePath);
        List<Gift> gifts = reader.load();
        PositionNet net = new PositionNet();

        /* distance to all other neighbours */
        if (false) {
            Gift first;
            Gift second;
            List<GiftNeighbour> neighbours;
            for (i = 0; i < gifts.size(); i++) {
                first = gifts.get(i);
                neighbours = first.getNeighbours();
                net.add(first);
                for (k = i + 1; k < gifts.size(); k++) {
                    second = gifts.get(k);
                    if (!first.equals(second)) {
                        neighbours.add(new GiftNeighbour(first, second));
                    }
                }

                System.out.println(String.format("%6d", i) + " elapsed=" + stopwatch.elapsed(TimeUnit.SECONDS));
            }
        }

        /* grid */
        if (false) {
            for (Gift gift : gifts) {
                net.add(gift);
            }
            net.printMap();
        }

        //DistanceTest.runAccuracyTest(gifts, 1000);
        //DistanceTest.runTimeTest(gifts, gifts.size());

        stopwatch.stop();
        System.out.println("stop=" + stopwatch.elapsed(TimeUnit.SECONDS));
        System.out.println("Done");

        RandomTourBuilder.getTours(gifts);
    }
}
