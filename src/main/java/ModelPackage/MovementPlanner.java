package ModelPackage;

import javax.imageio.ImageIO;
import javax.lang.model.type.ArrayType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Uses a simplified version of Dijkstra's pathfinding algorithm to find the shortest path to a certain position.
 * Also provides information on where to navigate to. Assumes the following Grid setup:
 *
 * 6 7 8
 * 3 4 5
 * 0 1 2
 */
public class MovementPlanner {

    private IGrid simulationGrid;
    private ArrayList<MotionPoint> plannableGrid;
    private ArrayList<Obstacle> obstacleList;
    private ArrayList<Point> pathFound;

    private ArrayList<ArrayList<MotionPoint>> subGrids;

    public MovementPlanner() {
    }

    /**
     * Initializes the planner with the correct data and triggers the generation of a complete plannable grid.
     * @param obstacles ArrayList containing obstacles. Can't change once the simulation has started!
     * @param simulationGrid The simulation Grid used to generate a plannable grid
     * @return true if the generation of the plannable grid was successful, false otherwise.
     */
    public boolean initializePlanner(ArrayList<Obstacle> obstacles, IGrid simulationGrid){
        this.obstacleList = obstacles;
        this.simulationGrid = simulationGrid;
        this.plannableGrid = new ArrayList<>();
        this.pathFound = new ArrayList<>();

        try{
            generatePlannableGrid();
        }
        catch(Exception e){
            System.out.println ("Error while generating plannable grid.");
            return false;
        }

        try{
            generateSubgrids();
        }
        catch (Exception e){
            System.out.println("Error generating subgrids");
            return false;
        }
        return true;
    }

    /**
     * Generates subgrids to optimize motionplanning by substracting the water from the islands
     */
    private void generateSubgrids() {
        subGrids = new ArrayList<>();

        ArrayList<MotionPoint> waterList = new ArrayList<>();
        //waterlist is always element 0 in the subgrid list
        subGrids.add(waterList);
        for (MotionPoint motionPoint : plannableGrid){
            if (motionPoint.getWater()){
                waterList.add(motionPoint);
            }
            else
            {
                //we found a living area, check if we found this one already
                boolean found = false;
                for (ArrayList<MotionPoint> area : subGrids){
                    if (area.contains(motionPoint)){
                        found = true;
                    }
                }
                if (!found){
                    //new living area, find adjacent points
                    ArrayList<MotionPoint> livingArea = new ArrayList<>();
                    livingArea.add(motionPoint);
                    ArrayList<Point> openPoints = new ArrayList<>();
                    for (Point adjacent : motionPoint.getAdjacentPoints()){
                        openPoints.add(adjacent);
                    }
                    ArrayList<Point> pointBuffer = new ArrayList<>();
                    while (openPoints.size() > 0){
                        for (Point p : openPoints){
                            MotionPoint currentPoint = getMotionPointByCoordinates((int)p.getX(), (int)p.getY());
                            if ((!livingArea.contains(currentPoint)) && (!currentPoint.getWater())){
                                livingArea.add(currentPoint);
                                for (Point nextPoint : currentPoint.getAdjacentPoints()){
                                    if (!pointBuffer.contains(nextPoint)){
                                        pointBuffer.add(nextPoint);
                                    }
                                }
                            }
                        }

                        openPoints = new ArrayList<>(pointBuffer);
                        pointBuffer.clear();
                    }
                    subGrids.add(livingArea);
                }
            }
        }


        System.out.println("Points in water list: " + waterList.size());
        System.out.println("Subgrids Found: " + subGrids.size());
    }

    /**
     * Used to generate the plannable grid, consisting of MotionPoints and the points adjacent to them. If this fails the motionplanner can't properly function.
     */
    private void generatePlannableGrid(){
        if (simulationGrid != null){
            int pointCount = simulationGrid.getWidth() * simulationGrid.getHeight();
            for(int i=0; i<pointCount; i++){
                plannableGrid.add(null);
            }
            //create a MotionPoint for each GridPoint
            for (GridPoint gridPoint : simulationGrid.getPointList()){
                int gridPointNumber = getPointNumber(new Point(gridPoint.getX(), gridPoint.getY()));
                plannableGrid.set(gridPointNumber, new MotionPoint(gridPoint));
            }

            for (MotionPoint currentPoint : plannableGrid){
                for (Obstacle o : obstacleList){
                    if ((o.getX() == currentPoint.getX()) && (o.getY() == currentPoint.getY())) {
                        currentPoint.setObstacle(true);
                    }
                }
            }

            for (MotionPoint currentPoint : plannableGrid){
                getAdjacentPoints(currentPoint);
            }
        }
        else{
            throw new NullPointerException("SimulationGrid was not set!");
        }
    }

    /**
     * Gets the total count of MotionPoints in the plannableGrid.
     * @return int containing the MotionPoint count
     */
    public int getTotalMotionPoints(){
        return plannableGrid.size();
    }

    /**
     * Gets the total adjacent points found in the plannable grid
     * @return int containing the adjacent point count
     */
    public int getTotalAdjacentCount(){
        int counter = 0;
        for (MotionPoint mp : plannableGrid){
            counter += mp.adjacentPoints.size();
        }
        return counter;
    }

    /**
     * Find the points adjacent to the current point
     * @param currentPoint point to find the adjacent points of and store them in.
     */
    private void getAdjacentPoints(MotionPoint currentPoint) {

        int x = currentPoint.getX();
        int y = currentPoint.getY();

        //get point list
        for(int xx = 1; xx>=-1; xx--){
            for (int yy = 1; yy>=-1; yy--){
                int neighbourX = x - xx;
                int neighbourY = y - yy;
                if (neighbourX < 0){
                    //x falls left of grid
                    neighbourX = simulationGrid.getWidth()-1;
                }
                if (neighbourX > simulationGrid.getWidth()-1) {
                    //x falls of right of grid
                    neighbourX = 0;
                }
                if (neighbourY < 0){
                    //y falls of below grid
                    neighbourY = simulationGrid.getHeight()-1;
                }
                if (neighbourY > simulationGrid.getHeight()-1){
                    neighbourY = 0;
                }
                //add this point to the adjacentpoints, only if the point is not itself (x+0 && y+0)
                if (!((neighbourX == x) && (neighbourY == y))){
                    if (!getMotionPointByCoordinates(neighbourX, neighbourY).getObstacle()){
                        //currentPoint.addAdjacentPoint(new Point(neighbourY, neighbourX));
                        currentPoint.addAdjacentPoint(new Point(neighbourX, neighbourY));
                    }
                }
            }
        }
    }


    /**
     * Generate a path towards the endpoint
     * @param startX X-coordinate of startpoint
     * @param startY Y-coordinate of startpoint
     * @param targetX X-coordinate of endpoint
     * @param targetY Y-coordinate of endpoint
     * @return ArrayList of points, in the right order that lead to the endpoint. Returns null if no path was found or
     * the startpoint was the endpoint
     */
    public ArrayList<Point> findPath(int startX, int startY, int targetX, int targetY) throws Exception {
        //reset the grid, clearing all previous points
        resetPlannableGrid();

        if ((startX == targetX) && (startY == targetY)){
            return null;
        }

        long startTime = System.nanoTime();

        ArrayList<Point> openPoints = new ArrayList<>();
        ArrayList<Point> pointBuffer = new ArrayList<>();
        ArrayList<Point> closedPoints = new ArrayList<>();
        //fetch the first set of adjacent points to the startpoint
        Point startPoint = new Point(startX, startY);
        closedPoints.add(startPoint);
        for (Point adjacentPoint : getMotionPointByCoordinates(startX, startY).getAdjacentPoints()){
            getMotionPointByCoordinates((int)adjacentPoint.getX(), (int)adjacentPoint.getY()).setPreviousPoint(startPoint);
            openPoints.add(adjacentPoint);
        }
        //reset previouspoint for startpoint (DEBUG)
        getMotionPointByCoordinates(startX, startY).setPreviousPoint(null);

        int distanceCounter = 0;
        debugGrid(distanceCounter, startPoint, new Point(targetX, targetY), openPoints, closedPoints);
        MotionPoint endPoint = null;
        while (endPoint == null){
            //start counting loops for debug
            if (openPoints.size() == 0){
                return null;
            }

            //check the open points for the endpoint
            for (Point currentPoint : openPoints){
                if ((currentPoint.getX() == targetX) && (currentPoint.getY() == targetY)){
                    endPoint = getMotionPointByCoordinates((int)currentPoint.getX(), (int)currentPoint.getY());
                    System.out.println("Found target. Steps required: " + distanceCounter);
                    debugGrid(distanceCounter, startPoint, new Point(targetX, targetY), openPoints, closedPoints);
                    break;
                }
            }

            if (endPoint == null){
                //point not found, move current points into closed points and fill buffer with new points
                for (Point currentPoint : openPoints){
                    MotionPoint motionPoint = getMotionPointByCoordinates((int)currentPoint.getX(), (int)currentPoint.getY());
                    for(Point freshOpenPoint : motionPoint.getAdjacentPoints()){

                        if ((!closedPoints.contains(freshOpenPoint)) && (!openPoints.contains(freshOpenPoint)) && (!pointBuffer.contains(freshOpenPoint))){
                            MotionPoint freshMP = getMotionPointByCoordinates((int) freshOpenPoint.getX(), (int)freshOpenPoint.getY());
                            if ((freshMP.getPreviousPoint() == null)) {
                                freshMP.setPreviousPoint(currentPoint);
                                pointBuffer.add(freshOpenPoint);
                            }
                        }
                    }
                }
            }


            //target not found, move all open points to closed points and move the buffer into the open points
            for(Point openPoint : openPoints){
                if (!closedPoints.contains(openPoint)){
                    closedPoints.add(openPoint);
                }
            }
            openPoints = new ArrayList<>(pointBuffer);
            pointBuffer.clear();


            distanceCounter++;
            //debugGrid(distanceCounter, startPoint, new Point(targetX, targetY), openPoints, closedPoints);
        }

        long endTime = System.nanoTime();
        System.out.println("Pathfinding completed in " + ((endTime - startTime) / 1000000) + "ms");

        MotionPoint parentPoint = endPoint;
        int infiniteProtection = 0;
        while((parentPoint != null) && (infiniteProtection < 100)){
            infiniteProtection++;

            pathFound.add(new Point(parentPoint.getX(), parentPoint.getY()));
            if (parentPoint.getPreviousPoint() != null){
                MotionPoint newParent = getMotionPointByCoordinates((int)parentPoint.getPreviousPoint().getX(), (int)parentPoint.getPreviousPoint().getY());
                parentPoint = newParent;
            }
            else
            {
                break;
            }
        }


        /*
        for (Point step : pathFound){
            System.out.println("(" + step.getX() + "," + step.getY() + ")");
        }
        */

        return pathFound;
    }

    /**
     * Resets the pathFound and previousPoint references so the plannable grid can be reused, reducing the time needed
     * for pathfinding
     */
    private void resetPlannableGrid() {
        pathFound.clear();
        for (MotionPoint mp : plannableGrid){
            mp.setPreviousPoint(null);
        }
    }

    /**
     * Gets the element number corresponding to this points coordinates.
     * @param p Point to be used
     * @return int element number in the pointList
     */
    private int getPointNumber(Point p){
        int gridWidth = simulationGrid.getWidth();
        int x = (int)p.getX();
        int y = (int)p.getY();
        int number = (gridWidth * (y+1)) + (x-gridWidth);
        //System.out.println(number);
        return number;
    }

    /**
     * Gets the MotionPoint corresponding to a coordinate
     * @param x coordinate of point
     * @param y coordinate of point
     * @return MotionPoint corresponding to the point
     */
    private MotionPoint getMotionPointByCoordinates(int x, int y){
       if (plannableGrid!=null){
            //point element number (n) in array can be calculated by formula: (width * (y+1)) + (x - width). Width being the grid width
            return plannableGrid.get(((simulationGrid.getWidth() * (y+1)) + (x - simulationGrid.getWidth())));
        }
        else
        {
            throw new NullPointerException("No Plannable Grid to fetch points from!");
        }
    }

    /**
     * Debugging method for generating a visual image of the plannableGrid.
     * @param stepNumber int representing the number of steps in the motionplanning so far
     * @param startPoint Point representing the start position of the motionplanning
     * @param endPoint Point representing the end position (not to be confused with the endpoint used internally in the findPath method!)
     * @param openPoints ArrayList of points containing the open Points
     * @param closedPoints ArrayList of points containing the closed Points
     */
    private void debugGrid(int stepNumber, Point startPoint, Point endPoint, ArrayList<Point> openPoints, ArrayList<Point> closedPoints){
        int factor = 20;
        int size = 5;

        int canvasWidth = simulationGrid.getWidth() * factor;
        int canvasHeight = simulationGrid.getHeight() * factor;

        BufferedImage img = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();


        //draw grid
        int gridWidth = simulationGrid.getWidth();
        int gridHeight = simulationGrid.getHeight();
        for (int i = 0; i<gridWidth; i++){
            for (int j = 0; j<gridHeight; j++){
                //MotionPoint mp = getMotionPointByCoordinates(i, j);


                if (getMotionPointByCoordinates(i, j).getObstacle()){
                    g2.setColor(Color.MAGENTA);
                    g2.fillOval(i*factor, j*factor, size, size);
                }
                else{
                    g2.setColor(Color.WHITE);
                    g2.drawOval(i*factor, j*factor, size, size);
                }
            }
        }


        //draw open points
        for (Point openPoint : openPoints){
            g2.setColor(Color.GREEN);
            g2.drawOval((int)openPoint.getX()*factor, (int)openPoint.getY()*factor, size, size);
        }

        //draw closed points
        for (Point closedPoint : closedPoints){
            g2.setColor(Color.ORANGE);
            g2.drawOval((int)closedPoint.getX()*factor, (int)closedPoint.getY()*factor, size, size);
            g2.setColor(Color.BLUE);
            //if ((closedPoint.getX() != (int)startPoint.getX()) && (closedPoint.getY() != (int)startPoint.getY())){
            try{
                Point previousPoint = getMotionPointByCoordinates((int)closedPoint.getX(), (int)closedPoint.getY()).getPreviousPoint();
                g2.drawLine((int)closedPoint.getX()*factor, (int)closedPoint.getY()*factor, (int)previousPoint.getX() * factor, (int)previousPoint.getY()*factor);
            }
            catch(NullPointerException ne){

            }
        }
        //draw endpoint
        g2.setColor(Color.RED);
        g2.drawOval((int)endPoint.getX()*factor, (int)endPoint.getY()*factor, size, size);


        try{
            //ImageIO.write(img, "PNG", new File("/out/buffer_output"+stepNumber+".png"));
            ImageIO.write(img, "PNG", new File(System.getProperty("user.dir") + File.separator + "out" + File.separator + "buffer_output"+stepNumber+".png"));
            //System.out.println("Wrote img to disk");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Inner class used to create a version of the grid suitable for use in motion planning.
     * These points are "aware" of their neighbouring points.
     */
    private class MotionPoint{

        private boolean isObstacle;

        private GridPoint gridPoint;
        private Point previousPoint;
        //private MotionPoint previousPoint;
        //private ArrayList<MotionPoint> adjacentPoints;
        private ArrayList<Point> adjacentPoints;

        public MotionPoint(GridPoint gridPoint) {
            previousPoint = null;
            adjacentPoints = new ArrayList<>();
            isObstacle = false;
            this.gridPoint = gridPoint;
        }

        /**
         * Sets whether or not the point is an obstacle
         * @param isObstacle boolean true or false
         */
        public void setObstacle(boolean isObstacle){
            this.isObstacle = isObstacle;
        }

        /**
         * returns whether or not the point is an obstacle
         * @return boolean whether or not this is an obstacle
         */
        public boolean getObstacle(){
            return this.isObstacle;
        }

        /**
         * Sets the previous point in the used path when generating a path
         * @param point A point in the plannable grid
         */
        public void setPreviousPoint(Point point){
            this.previousPoint = point;
        }

        /**
         * Gets the previous point in the used path when generating a path
         * @return Returns the previous point in the plannable grid
         */
        public Point getPreviousPoint(){
            return previousPoint;
        }

        /** Adds an adjacent point in the plannable grid
         * @param point a motionpoint instance in the plannable grid
         */
        public void addAdjacentPoint(Point point){
            adjacentPoints.add(point);
        }

        /** Fetch a list with all adjacent points in the plannable grid
         * @return ArrayList containing MotionPoints
         */
        public ArrayList<Point> getAdjacentPoints(){
            return adjacentPoints;
        }

        /** Gets the GridPoint representing this MotionPoint in the regular simulation grid
         * @return
         */
        public GridPoint getGridPoint(){
            return gridPoint;
        }

        /** Gets the X coordinate, based on the regular grid system
         * @return X coordinate
         */
        public int getX(){
            return gridPoint.getX();
        }

        /** Gets the Y coordinate, based on the regular grid system
         * @return Y coordinate
         */
        public int getY(){
            return gridPoint.getY();
        }

        /**
         * Gets the state of this point (Water or not)
         * @return
         */
        public boolean getWater(){
            return gridPoint.getWater();
        }
    }
}
