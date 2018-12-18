package santa;

import java.util.Random;

import static santa.Tour.MAX_SLEIGH_WEIGHT;

public class TwoOpt {


    public static void optimize(Solution solution) {
        Random random = new Random();

        for (int i = 0 ; i < solution.tourCnt(); i++) {

            for (int j = 0; j < 10000; j++) {
              int idx1 = random.nextInt(solution.getTour(i).size());
              int idx2 = random.nextInt(solution.getTour(i).size());
                double prevWeariness = solution.getTour(i).calcWeariness();
                solution.getTour(i).swapGifts(idx1, idx2);

                if (solution.getTour(i).calcWeariness() > prevWeariness ||
                        solution.getTour(i).getSleighWeight() > MAX_SLEIGH_WEIGHT) {
                    // revert changes
                    solution.getTour(i).swapGifts(idx1, idx2);
                    //solution.getTour(i).calcWeariness();
                }


            }


        }
    }
}
