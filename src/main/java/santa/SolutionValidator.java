package santa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolutionValidator {

    public static boolean isValid(Solution solution, int numberOfGifts) {

        boolean isValid = true;
        List<Integer> giftIds = new ArrayList<>();
        List<Integer> missingIds = new ArrayList<>();


        for (int i = 0; i < solution.tourCnt(); i++) {
            for (int j = 0; j < solution.getTour(i).size(); j++) {
                int giftId = solution.getTour(i).getGift(j).getId();
                giftIds.add(giftId);
            }
        }

        Collections.sort(giftIds);

        for (int i = 1; i <= numberOfGifts; i++) {
            if (giftIds.contains(i)) {
                isValid = false;
                missingIds.add(i);
                System.out.println("Gift with id " + i + " is missing in the computed solution" );
            }

        }
        return isValid;
    }
}
