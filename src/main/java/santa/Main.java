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

        /*
        Solution slicedRandomSolution = SlicedRandomTourBuilder.getTours(gifts);
        slicedRandomSolution.calculateTotalWeariness();
        System.out.println("random slice: total weariness: " + slicedRandomSolution.getTotalWeariness() +
                ", total distance: " + slicedRandomSolution.getTotalLength());

        */

        Solution randomSolution = RandomTourBuilder.getTours(gifts);
        randomSolution.calculateTotalWeariness();

         if (SolutionValidator.isValid(randomSolution, gifts.size())) {
             System.out.println("random: total weariness: " + randomSolution.getTotalWeariness() +
                     ", total distance: " + randomSolution.getTotalLength());

             SubmissionFileWriter writer = new SubmissionFileWriter(solutionFilePath, gifts);
             writer.writeFile(randomSolution);
         }



        System.out.println("Done");
    }
}
