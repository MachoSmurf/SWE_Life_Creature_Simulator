package ModelPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
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
            int pointCount = simulationGrid.getWidth() * simulationGrid.getHeight();
            for(int i=0; i<pointCount; i++){
                plannableGrid.add(null);
            }
            //create a MotionPoint for each GridPoint
            for (GridPoint gridPoint : simulationGrid.getPointList()){
                int gridPointNumber = getPointNumber(new Point(gridPoint.getX(), gridPoint.getY()));
                plannableGrid.set(gridPointNumber, new MotionPoint(gridPoint));

                /*
                boolean newMotionPoint = true;
                //TODO: Implement obstacles correctly, maintaining the point location formula
                /*for (Obstacle o : obstacleList){
                    //check if this gridpoint is an obstacle. If so this won't be a motionpoint
                    if (((o.getX() == gridPoint.getX()) && (o.getY() == gridPoint.getY()))){
                        newMotionPoint = false;
                    }
                }*//*
                if (newMotionPoint){
                    plannableGrid.add(new MotionPoint(gridPoint));
                }*/
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
                                /*if (((freshMP.getX() == 5) && (freshMP.getY() == 7) && (freshMP.getPreviousPoint() == null)))
                                {
                                    System.out.println("5,7 is null");
                                }*/
                                freshMP.setPreviousPoint(currentPoint);
                                /*try{
                                    plannableGrid.set(getPointNumber(freshOpenPoint), freshMP);
                                }
                                catch(Exception e){
                                    System.out.println("Exception getting point index");
                                }*/
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
            debugGrid(distanceCounter, startPoint, new Point(targetX, targetY), openPoints, closedPoints);

        }

        long endTime = System.nanoTime();
        System.out.println("Pathfinding completed in " + ((endTime - startTime) / 100000) + "ms");

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

    private void resetPlannableGrid() {
        pathFound.clear();
        for (MotionPoint mp : plannableGrid){
            mp.setPreviousPoint(null);
        }
    }

    private int getPointNumber(Point p){
        int gridWidth = simulationGrid.getWidth();
        int x = (int)p.getX();
        int y = (int)p.getY();
        int number = (gridWidth * (y+1)) + (x-gridWidth);
        //System.out.println(number);
        return number;
    }

    private MotionPoint getMotionPointByCoordinates(int x, int y){
        if ((x== 5) && (y==7)){
            System.out.println("");
        }

        if (plannableGrid!=null){
            //point element number (n) in array can be calculated by formula: (width * (y+1)) + (x - width). Width being the grid width
            return plannableGrid.get(((simulationGrid.getWidth() * (y+1)) + (x - simulationGrid.getWidth())));
        }
        else
        {
            throw new NullPointerException("No Plannable Grid to fetch points from!");
        }
    }

    private void debugGrid(int stepNumber,Point startPoint, Point endPoint, ArrayList<Point> openPoints, ArrayList<Point> closedPoints){
        int factor = 40;
        int size = 5;

        int canvasWidth = simulationGrid.getWidth() * factor;
        int canvasHeight = simulationGrid.getHeight() * factor;

        BufferedImage img = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();


        //draw grid

        for (int i = 0; i<10; i++){
            for (int j = 0; j<10; j++){
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
            if ((closedPoint.getX() != (int)startPoint.getX() ) && (closedPoint.getY() != (int)startPoint.getY())){
                Point previousPoint = getMotionPointByCoordinates((int)closedPoint.getX(), (int)closedPoint.getY()).getPreviousPoint();
                g2.drawLine((int)closedPoint.getX()*factor, (int)closedPoint.getY()*factor, (int)previousPoint.getX() * factor, (int)previousPoint.getY()*factor);
            }
        }
        //draw endpoint
        g2.setColor(Color.RED);
        g2.drawOval((int)endPoint.getX()*factor, (int)endPoint.getY()*factor, size, size);


        try{
            ImageIO.write(img, "PNG", new File("buffer_output"+stepNumber+".png"));
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
    }
}
