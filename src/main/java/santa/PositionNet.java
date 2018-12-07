package santa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PositionNet {

    private List<Gift> gifts;

    enum Longitude {
        LONGITUDE_NULL,
        LONGITUDE_15_POS,
        LONGITUDE_30_POS,
        LONGITUDE_45_POS,
        LONGITUDE_60_POS,
        LONGITUDE_75_POS,
        LONGITUDE_90_POS,
        LONGITUDE_105_POS,
        LONGITUDE_120_POS,
        LONGITUDE_135_POS,
        LONGITUDE_150_POS,
        LONGITUDE_165_POS,
        LONGITUDE_180_POS,
        LONGITUDE_15_NEG,
        LONGITUDE_30_NEG,
        LONGITUDE_45_NEG,
        LONGITUDE_60_NEG,
        LONGITUDE_75_NEG,
        LONGITUDE_90_NEG,
        LONGITUDE_105_NEG,
        LONGITUDE_120_NEG,
        LONGITUDE_135_NEG,
        LONGITUDE_150_NEG,
        LONGITUDE_165_NEG,
        LONGITUDE_180_NEG,
    };

    /**
     * 0 - 15
     * 15 - 30
     * 30 - 45
     * 45 - 60
     *
     */
    //private Map


    private Longitude getKey(double longitude) {
        /* Pos */
        if (longitude >= 0.0 ) {
            /* >= 90 */
            if (longitude >= 90.0) {
                /* >= 150 */
                if (longitude >= 150.0) {
                    if (longitude < 165) {
                        return Longitude.LONGITUDE_105_POS;
                    }
                } else {

                }
                /* < 90 */
            } else {

            }

            /* Neg */
        } else {

        }

        return Longitude.LONGITUDE_NULL;
    }

    public void add(Gift gift) {
        double longitude = gift.getLongitude();
    }
}
