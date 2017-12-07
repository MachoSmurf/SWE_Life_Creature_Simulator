package ModelPackage;

import java.lang.reflect.Array;
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

    private void generatePlannableGrid(){

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
        private ArrayList<MotionPoint> adjacentPoints;

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
         * @param motionPoint a motionpoint instance in the plannable grid
         */
        public void addAdjacentPoint(MotionPoint motionPoint){
            adjacentPoints.add(motionPoint);
        }

        /** Fetch a list with all adjacent points in the plannable grid
         * @return ArrayList containing MotionPoints
         */
        public ArrayList<MotionPoint> getAdjacentPoints(){
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
