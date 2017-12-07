package ModelPackage;

import java.util.ArrayList;

/**
 * Uses a simplified version of Dijkstra's pathfinding algorithm to find the shortest path to a certain position.
 * Also provides information on where to navigate to.
 */
public class MovementPlanner {

    public MovementPlanner() {
    }

    public ArrayList<TargetCoordinate> findPath(int startX, int startY, int targetX, int targetY){
        return null;
    }

    /**
     * Inner class used to create a version of the grid suitable for use in motion planning.
     * These points are "aware" of their neighbouring points.
     */
    private class MotionPoint{

        public MotionPoint() {
        }
    }
}
