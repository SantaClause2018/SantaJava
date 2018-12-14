package santa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * https://stackoverflow.com/questions/14329691/convert-latitude-longitude-point-to-a-pixels-x-y-on-mercator-projection
 *
 */
public class MapService {
    // CHANGE THIS: the output path of the image to be created
    private static final String IMAGE_FILE_PATH = "map.png";

    // CHANGE THIS: image width in pixel
    private static final int IMAGE_WIDTH_IN_PX = 1600;

    // CHANGE THIS: image height in pixel
    private static final int IMAGE_HEIGHT_IN_PX = 1200;

    // CHANGE THIS: minimum padding in pixel
    private static final int MINIMUM_IMAGE_PADDING_IN_PX = 10;

    // formula for quarter PI
    private final static double QUARTERPI = Math.PI / 4.0;

    private Point2D.Double dup(Point2D.Double xy, double dx, double dy) {
        Point2D.Double xy2 = new Point2D.Double();
        xy2.x = xy.x + dx;
        xy2.y = xy.y + dy;
        return xy2;
    }

    public void run(List<Gift> gifts) throws Exception {
        // configuring the buffered image and graphics to draw the map
        BufferedImage bufferedImage = new BufferedImage(
            IMAGE_WIDTH_IN_PX,
            IMAGE_HEIGHT_IN_PX,
            BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = bufferedImage.createGraphics();
        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints renderHints = new RenderingHints(map);
        g.setRenderingHints(renderHints);

        // min and max coordinates, used in the computation below
        Point2D.Double minXY = new Point2D.Double(-1, -1);
        Point2D.Double maxXY = new Point2D.Double(-1, -1);

        // a list of counties where each county contains a list of coordinates that form the county boundary
        Collection<Collection<Point2D.Double>> countyBoundaries = new ArrayList<Collection<Point2D.Double>>();

        // for every county, convert the longitude/latitude to X/Y using Mercator projection formula
        for (int i = 0; i < gifts.size(); i++) {
            /** TODO: nur jedes 25 Päckli auf der Karte anzeigen */
            if (i % 25 == 0) {
                Gift gift = gifts.get(i);
                Collection<Point2D.Double> lonLat = new ArrayList<Point2D.Double>();

                // convert to radian
                double longitude = gift.getLongitude() * Math.PI / 180;
                double latitude = gift.getLatitude() * Math.PI / 180;

                Point2D.Double xy = new Point2D.Double();
                xy.x = longitude;
                xy.y = Math.log(Math.tan(QUARTERPI + 0.5 * latitude));

                // The reason we need to determine the min X and Y values is because in order to draw the map,
                // we need to offset the position so that there will be no negative X and Y values
                minXY.x = (minXY.x == -1) ? xy.x : Math.min(minXY.x, xy.x);
                minXY.y = (minXY.y == -1) ? xy.y : Math.min(minXY.y, xy.y);

                lonLat.add(xy);
                lonLat.add(dup(xy, 0.00, 0.01));
                lonLat.add(dup(xy, 0.01, 0.01));
                lonLat.add(dup(xy, 0.01, 0.00));

                countyBoundaries.add(lonLat);
            }
        }

        // readjust coordinate to ensure there are no negative values
        for (Collection<Point2D.Double> points : countyBoundaries) {
            for (Point2D.Double point : points) {
                point.x = point.x - minXY.x;
                point.y = point.y - minXY.y;

                // now, we need to keep track the max X and Y values
                maxXY.x = (maxXY.x == -1) ? point.x : Math.max(maxXY.x, point.x);
                maxXY.y = (maxXY.y == -1) ? point.y : Math.max(maxXY.y, point.y);
            }
        }

        int paddingBothSides = MINIMUM_IMAGE_PADDING_IN_PX * 2;

        // the actual drawing space for the map on the image
        int mapWidth = IMAGE_WIDTH_IN_PX - paddingBothSides;
        int mapHeight = IMAGE_HEIGHT_IN_PX - paddingBothSides;

        // determine the width and height ratio because we need to magnify the map to fit into the given image dimension
        double mapWidthRatio = mapWidth / maxXY.x;
        double mapHeightRatio = mapHeight / maxXY.y;

        // using different ratios for width and height will cause the map to be stretched. So, we have to determine
        // the global ratio that will perfectly fit into the given image dimension
        double globalRatio = Math.min(mapWidthRatio, mapHeightRatio);

        // now we need to readjust the padding to ensure the map is always drawn on the center of the given image dimension
        double heightPadding = (IMAGE_HEIGHT_IN_PX - (globalRatio * maxXY.y)) / 2;
        double widthPadding = (IMAGE_WIDTH_IN_PX - (globalRatio * maxXY.x)) / 2;

        // for each country, draw the boundary using polygon
        for (Collection<Point2D.Double> points : countyBoundaries) {
            Polygon polygon = new Polygon();

            for (Point2D.Double point : points) {
                int adjustedX = (int) (widthPadding + (point.getX() * globalRatio));

                // need to invert the Y since 0,0 starts at top left
                int adjustedY = (int) (IMAGE_HEIGHT_IN_PX - heightPadding - (point.getY() * globalRatio));
                polygon.addPoint(adjustedX, adjustedY);

                //System.out.println(point.getX() + "/" + point.getY() + " => " + adjustedX + "/" + adjustedY);
            }

            g.drawPolygon(polygon);
        }

        // create the image file
        ImageIO.write(bufferedImage, "PNG", new File(IMAGE_FILE_PATH));
    }
}
