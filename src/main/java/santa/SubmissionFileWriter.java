package santa;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SubmissionFileWriter {

    private Map<Integer, Integer> tourAssignments;
    private List<Gift> gifts;
    private  PrintWriter writer;
    private StringBuilder stringBuilder;
    private Path filePath;

    public  SubmissionFileWriter( Path filePath, List<Gift> gifts) {
        this.filePath = filePath;
        this.gifts = gifts;
    }

    public void writeFile(Solution solution) {

        try {

            if (isBetterSolution(solution)) {

                extractTourIds(solution);
                writer = new PrintWriter(new File(filePath.toString()));
                stringBuilder = new StringBuilder();

                stringBuilder.append("GiftId,TripId\n"); // file header

                for (Map.Entry<Integer, Integer> entry : tourAssignments.entrySet()) {
                    stringBuilder.append(entry.getKey() + "," + entry.getValue() + "\n");
                }
                writer.write(stringBuilder.toString());
                writer.close();

            }
            else {
                System.out.println("existing solution is still better. Did not write new solution");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("could not write submission file");
        }
    }

    private void extractTourIds(Solution solution) {
        tourAssignments = new HashMap<>();
        for (int i = 0; i < solution.tourCnt(); i++) {
            for (int j = 0; j < solution.getTour(i).size(); j++) {
                Gift gift = solution.getTour(i).getGift(j);
                tourAssignments.put(gift.getId(), gift.getTour());
            }
        }
    }

    private boolean isBetterSolution(Solution newSolution) {

        try {
            SubmissionFileReader reader = new SubmissionFileReader(filePath);
            Solution oldSolution = Solution.fromSubmission(reader.load(),gifts);
            double oldWariness = oldSolution.calculateTotalWeariness();
            double newWeariness = newSolution.calculateTotalWeariness();
            if (newWeariness < oldWariness ) { return true; }
            return true;
        }
        catch (IOException ex) {
            return true;
        }

    }


}
