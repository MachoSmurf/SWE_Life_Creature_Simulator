package ModelPackage;

import java.awt.*;
import java.util.ArrayList;

/**
 * Uses a simplified version of Dijkstra's pathfinding algorithm to find the shortest path to a certain position.
 * Also provides information on where to navigate to.
 */
public class MovementPlanner {

    private IGrid simulationGrid;
    private ArrayList<MotionPoint> plannableGrid;
    private ArrayList<Obstacle> obstacleList;
    private ArrayList<Point> pathFound;

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
        return true;
    }

    /**
     * used to generate the plannable grid. If this fails the motionplanner can't properly function.
     */
    private void generatePlannableGrid(){
        if (simulationGrid != null){
            //create a MotionPoint for each GridPoint
            for (GridPoint gridPoint : simulationGrid.getPointList()){
                boolean newMotionPoint = true;
                //TODO: Implement obstacles correctly, maintaining the point location formula
                /*for (Obstacle o : obstacleList){
                    //check if this gridpoint is an obstacle. If so this won't be a motionpoint
                    if (((o.getX() == gridPoint.getX()) && (o.getY() == gridPoint.getY()))){
                        newMotionPoint = false;
                    }
                }*/
                if (newMotionPoint){
                    plannableGrid.add(new MotionPoint(gridPoint));
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

    public int getTotalMotionPoints(){
        return plannableGrid.size();
    }

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
        for(int xx = -1; xx<2; xx++){
            for (int yy = -1; yy<2; yy++){
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
                    currentPoint.addAdjacentPoint(new Point(neighbourX, neighbourY));
                }
            }
        }
        /*System.out.println("Adjacent points for " + currentPoint.getX() + ", " + currentPoint.getY() + ": ");
        for (Point p : currentPoint.getAdjacentPoints()){
            System.out.println("\t " + p.getX() + ", " + p.getY());
        }*/
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
            openPoints.add(adjacentPoint);
            getMotionPointByCoordinates((int)adjacentPoint.getX(), (int)adjacentPoint.getY()).setPreviousPoint(startPoint);
        }
        //reset previouspoint for startpoint (DEBUG)
        getMotionPointByCoordinates(startX, startY).setPreviousPoint(null);

        int distanceCounter =1;
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
                    break;
                }
            }

            //point not found, move current points into closed points and fill buffer with new points
            for (Point currentPoint : openPoints){
                MotionPoint motionPoint = getMotionPointByCoordinates((int)currentPoint.getX(), (int)currentPoint.getY());
                for(Point freshOpenPoint : motionPoint.getAdjacentPoints()){

                    if ((!closedPoints.contains(freshOpenPoint)) && (!openPoints.contains(freshOpenPoint))){
                        MotionPoint freshMP = getMotionPointByCoordinates((int) freshOpenPoint.getX(), (int)freshOpenPoint.getY());
                        if ((freshMP.getPreviousPoint() == null)) {
                            /*if ((freshMP.getX() == 5) && (freshMP.getY() == 7))
                            {
                                System.out.println("Hier stoppen...");
                            }*/
                            freshMP.setPreviousPoint(new Point(motionPoint.getX(), motionPoint.getY()));
                            try{
                                plannableGrid.set(getPointNumber(freshOpenPoint), freshMP);
                            }
                            catch(Exception e){
                                System.out.println("Hier stoppen...");
                            }
                            pointBuffer.add(freshOpenPoint);
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
        }

        long endTime = System.nanoTime();
        System.out.println("Pathfinding completed in " + ((endTime - startTime) / 100000) + "ms");

        //Get the path backwards
        /*{ OLD CODE

            MotionPoint parentPoint = endPoint;
            MotionPoint previousPoint;
            int failsafeCounter = 0;

            while (true) {

                Point step = new Point(parentPoint.getX(), parentPoint.getY());
                pathFound.add(step);
                if ((parentPoint.previousPoint.getY() == startY) && (parentPoint.previousPoint.getX() == startX)) {
                    //last point
                    Point lastStep = new Point((int) parentPoint.previousPoint.getX(), (int) parentPoint.previousPoint.getY());
                    pathFound.add(lastStep);
                    break;
                } else {
                    parentPoint = getMotionPointByCoordinates((int) parentPoint.previousPoint.getX(), (int) parentPoint.previousPoint.getY());
                }
                //previousPoint = getMotionPointByCoordinates((int)parentPoint.getPreviousPoint().getX(), (int)parentPoint.getPreviousPoint().getY());

                if (failsafeCounter >= simulationGrid.getPointList().size()) {
                    return null;
                }
                failsafeCounter++;
            }
        }


        /*for (int i = 0; i<distanceCounter; i++){
            pathFound.add(new Point(parentPoint.getX(), parentPoint.getY()));
            System.out.println(parentPoint.getX() + "," + parentPoint.getY());
            parentPoint = getMotionPointByCoordinates((int)parentPoint.previousPoint.getX(), (int)parentPoint.previousPoint.getY());
        }*/

        MotionPoint parentPoint = endPoint;
        int infiniteProtection = 0;
        while((parentPoint != null) && (infiniteProtection < 100)){
            infiniteProtection++;

            pathFound.add(new Point(parentPoint.getX(), parentPoint.getY()));
            MotionPoint newParent = getMotionPointByCoordinates((int)parentPoint.getPreviousPoint().getX(), (int)parentPoint.getPreviousPoint().getY());
            parentPoint = newParent;
        }



        return pathFound;
    }

    private int getPointNumber(Point p){
        return ((int)(p.getY()+1)) + (int)(p.getX() - simulationGrid.getWidth());
    }

    private MotionPoint getMotionPointByCoordinates(int x, int y){
        if (plannableGrid!=null){
            //point element number (n) in array can be calculated by formula: (width * (y+1)) + (x - width). Width being the grid width
            return plannableGrid.get((simulationGrid.getWidth() * (y+1)) + (x - simulationGrid.getWidth()));
        }
        else
        {
            throw new NullPointerException("No Plannable Grid to fetch points from!");
        }
    }

    /**
     * Inner class used to create a version of the grid suitable for use in motion planning.
     * These points are "aware" of their neighbouring points.
     */
    private class MotionPoint{

        private GridPoint gridPoint;
        private Point previousPoint;
        //private MotionPoint previousPoint;
        //private ArrayList<MotionPoint> adjacentPoints;
        private ArrayList<Point> adjacentPoints;

        public MotionPoint(GridPoint gridPoint) {
            previousPoint = null;
            adjacentPoints = new ArrayList<>();
            this.gridPoint = gridPoint;
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
    }
}
