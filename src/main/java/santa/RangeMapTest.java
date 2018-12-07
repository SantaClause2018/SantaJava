package santa;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.ArrayList;
import java.util.List;

public class RangeMapTest {

    public static void main(String[] args) {
        RangeMap<Double, List<Gift>> map = TreeRangeMap.create();

        map.put(Range.closed(0.0, 15.0), new ArrayList<>());
        map.put(Range.closed(15.0, 30.0), new ArrayList<>());
        map.put(Range.closed(30.0, 45.0), new ArrayList<>());

        List<Gift> a = map.get(14.9);
        List<Gift> b = map.get(15.0);
        List<Gift> c = map.get(15.1);

        /*
        for (int i = -90; i <= (90 - 15); i += 15) {
            System.out.println(i + " - " + (i + 15));
        }

        for (int i = -180; i <= (180 - 15); i += 15) {
            System.out.println(i + " - " + (i + 15));
        }
        */
        new PositionNet();

        System.out.println("Done!");
    }
}
