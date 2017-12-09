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

    private ArrayList<SimObject> obstacleList;
    private ArrayList<SimObject> plantList;
    private ArrayList<SimObject> creatureList;


    public MovementPlanner() {
    }

    /**
     * Initializes the planner with the correct data and triggers the generation of a complete plannable grid. Do not
     * use this function to update the plant and creature lists. Use setPlantList() and setCreatureList() instead!
     * @param obstacles ArrayList containing obstacles. Can't change once the simulation has started!
     * @param plants ArrayList of plants
     * @param creatures ArrayList of creatures
     * @param simulationGrid The simulation Grid used to generate a plannable grid
     * @return true if the generation of the plannable grid was successful, false otherwise.
     */
    public boolean initializePlanner(ArrayList<SimObject> obstacles, ArrayList<SimObject> plants, ArrayList<SimObject> creatures, IGrid simulationGrid){
        this.obstacleList = obstacles;
        this.creatureList = creatures;
        this.plantList = plants;
        this.simulationGrid = simulationGrid;

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
     * Update the plant list
     * @param plantList ArrayList of plants
     */
    public void setPlantList(ArrayList<SimObject> plantList) {
        this.plantList = plantList;
    }

    /**
     * Update the creature list
     * @param creatureList ArrayList of creatures
     */
    public void setCreatureList(ArrayList<SimObject> creatureList) {
        this.creatureList = creatureList;
    }

    /**
     * used to generate the plannable grid. If this fails the motionplanner can't properly function.
     */
    private void generatePlannableGrid(){
        if (simulationGrid != null){
            //create a MotionPoint for each GridPoint
            for (GridPoint gridPoint : simulationGrid.getPointList()){
                plannableGrid.add(new MotionPoint(gridPoint));
            }

            for (MotionPoint currentPoint : plannableGrid){
                getAdjacentPoints(currentPoint);
            }

            //point element number (n) in array can be calculated by formula: (width * (y+1)) + (x - width). Width being the grid width
        }
        else{
            throw new NullPointerException("SimulationGrid was not set!");
        }
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
                //add this point to the adjacentpoints
                currentPoint.addAdjacentPoint(new Point(neighbourX, neighbourY));
            }
        }
    }

    public ArrayList<TargetCoordinate> findPath(int startX, int startY, int targetX, int targetY){
        return null;
    }

    /**
     * Inner class used to create a version of the grid suitable for use in motion planning.
     * These points are "aware" of their neighbouring points.
     */
    private class MotionPoint{

        private GridPoint gridPoint;
        private MotionPoint previousPoint;
        //private ArrayList<MotionPoint> adjacentPoints;
        private ArrayList<Point> adjacentPoints;

        public MotionPoint(GridPoint gridPoint) {
            adjacentPoints = new ArrayList<>();
            this.gridPoint = gridPoint;
        }

        /**
         * Sets the previous point in the used path when generating a path
         * @param motionPoint A point in the plannable grid
         */
        public void setPreviousPoint(MotionPoint motionPoint){
            this.previousPoint = motionPoint;
        }

        /**
         * Gets the previous point in the used path when generating a path
         * @return Returns the previous point in the plannable grid
         */
        public MotionPoint getPreviousPoint(){
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
