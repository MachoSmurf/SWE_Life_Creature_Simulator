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
                for (Obstacle o : obstacleList){
                    //check if this gridpoint is an obstacle. If so this won't be a motionpoint
                    if (((o.getX() == gridPoint.getX()) && (o.getY() == gridPoint.getY()))){
                        newMotionPoint = false;
                    }
                }
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
    public ArrayList<Point> findPath(int startX, int startY, int targetX, int targetY){
        if ((startX == targetX) && (startY == targetY)){
            return null;
        }

        long startTime = System.nanoTime();

        ArrayList<MotionPoint> openPoints = new ArrayList<>();
        //move the starting point into the open points list
        openPoints.add(getMotionPointByCoordinates(startX, startY));

        MotionPoint endPoint = null;
        while (endPoint == null){

            //no more points to use in planning. Route can't be found
            if (openPoints.size() == 0){
                break;
            }

            ArrayList<Point> pointsToCheck = null;
            //get points to check this step
            for (MotionPoint motionPoint : openPoints){

            }
        }

        long endTime = System.nanoTime();
        System.out.println("Pathfinding completed in " + ((endTime - startTime) * 100000) + "ms");

        return null;
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
