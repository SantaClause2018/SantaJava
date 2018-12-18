package santa;

import com.google.common.base.Stopwatch;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        Path filePath = Paths.get(
            ".." + java.io.File.separator +
            "SantaData"
            //"gifts_reduced.csv"
        );
        Path sourceFilePath  = Paths.get(filePath.toString() +
                java.io.File.separator + "Problem" + java.io.File.separator +
                "gifts.csv");

        Path solutionFilePath= Paths.get(filePath.toString() +
                java.io.File.separator + "Submission" + java.io.File.separator +
                "solution.csv");


        GiftReader reader = new GiftReader(sourceFilePath);
        List<Gift> gifts = reader.load();

        new MapService().run(gifts);
        //DistanceTest.runAccuracyTest(gifts, 1000);
        //DistanceTest.runTimeTest(gifts, gifts.size());


        Solution solution = SlicedRandomTourBuilder.getTours(gifts);
       //Solution solution = RandomTourBuilder.getTours(gifts);
        solution.calculateTotalWeariness();

         if (SolutionValidator.hasCorrectNumberOfGifts(solution,gifts)) {

             System.out.println("random: total weariness: " + solution.getTotalWeariness() +
                     ", total distance: " + solution.getTotalLength());

             SubmissionFileWriter writer = new SubmissionFileWriter(solutionFilePath, gifts);
             writer.writeFile(solution);
         }



        System.out.println("Done");
    }
}
