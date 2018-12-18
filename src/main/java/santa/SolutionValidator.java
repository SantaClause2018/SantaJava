package santa;

import java.util.*;

public class SolutionValidator {

    public static boolean hasAllGiftIds(Solution solution, List<Gift> gifts) {

        boolean isValid = true;
        List<Integer> giftIdsInSolution = new ArrayList<>();
        List<Integer> giftIds = new ArrayList<>();
        List<Integer> missingIds = new ArrayList<>();

        for (Gift gift: gifts) {
            giftIds.add(gift.getId());
        }
        Collections.sort(giftIds);

        for (int i = 0; i < solution.tourCnt(); i++) {
            for (int j = 0; j < solution.getTour(i).size(); j++) {
                int giftId = solution.getTour(i).getGift(j).getId();
                giftIdsInSolution.add(giftId);
            }
        }
        Collections.sort(giftIdsInSolution);

        for (int i = 0; i < Math.min(giftIds.size(), giftIdsInSolution.size()); i++) {
            if (!giftIds.get(i).equals(giftIdsInSolution.get(i))) {
                isValid = false;
                missingIds.add(giftIds.get(i));
                System.out.println("Gift with id " + giftIds.get(i) + " is missing in the computed solution" );
            }

        }

        return isValid;
    }

    public static boolean hasCorrectNumberOfGifts(Solution solution, List<Gift> gifts) {

        boolean isValid = true;
        int giftCnt = 0;

        for (int i = 0; i < solution.tourCnt(); i++) {
            giftCnt += solution.getTour(i).size();
        }
        if (giftCnt != gifts.size()) {
            isValid = false;
            System.out.println((gifts.size()-giftCnt) + " gifts are missing in the solution" );
        }
        else {
            System.out.println("the solution contains all gifts");
        }


        return isValid;
    }

    public static Set<Integer> findDuplicates(List<Integer> listContainingDuplicates)
    {
        final Set<Integer> setToReturn = new HashSet();
        final Set<Integer> set1 = new HashSet();

        for (Integer yourInt : listContainingDuplicates)
        {
            if (!set1.add(yourInt))
            {
                setToReturn.add(yourInt);
            }
        }
        return setToReturn;
    }
}
