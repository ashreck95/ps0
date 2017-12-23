/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

	public static final double TOLERANCE = 1e-5;
	
    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	for(int i = 0; i < 4; i++) {
	    	turtle.forward(sideLength);
	    	turtle.turn(90);
    	}
	}

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return 180 * (1 - (2.0 / sides));
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	return (int)Math.ceil((2 / (1 - angle / 180)));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	double angle = calculateRegularPolygonAngle(sides);
    	for(int i = 0; i < sides; i--) {
	    	turtle.forward(sideLength);
	    	turtle.turn(angle);
    	}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	
/* **************************OLD CODE (IT WORKS BUT ISN'T GREAT)*********************************************/    	
    	
//    	/*
//    	 * Creates the unit vector coordinates of @param currentHeading. 
//    	 * Because the heading angle's origin is north (up) instead of east (right) like the unit circle
//    	 * 	AND the heading angle rotates clockwise as it's magnitude increases, we have to swap the sine
//    	 * 	and the cosine from their typical affiliation with xCoordinates-->cosine and yCoordinates-->sine.
//    	 */
//    	double currentHeadingUnitVectorX = Math.sin(Math.toRadians(currentHeading)),    			
//				currentHeadingUnitVectorY = Math.cos(Math.toRadians(currentHeading));
//    	
//    	/* Uses the equation for finding the angle alpha between two Vectors:
//    	 * 						 vectorA (dotProduct) vectorB
//    	 * 			cos(alpha) = ----------------------------
//    	 * 						 	 |vectorA|  *  |vectorB|
//    	 * 
//    	 * The dot product of two 2D vectors:
//    	 * 			dotProduct = vectorA.xCoord * vectorB.xCoord +
//    	 * 						 vectorA.yCoord * vectorB.yCoord 
//    	 */
//    	
//    	int deltaX = targetX - currentX,
//    			deltaY = targetY - currentY;
//		double dotProduct = (currentHeadingUnitVectorX * deltaX + currentHeadingUnitVectorY * deltaY),
//    			
//    			targetHeadingMagnitude = Math.hypot(deltaX , deltaY),
//    			
//				angleBetweenTargetAndHeading = Math.toDegrees(Math.acos(
//						/* The currentHeading Vector is a unit vector (magnitude of 1) */
//						dotProduct / targetHeadingMagnitude /* * currentHeading vector magnitude */)); 
//						   	
//    	
//    	/* if the target heading is behind (counterclockwise) the current heading, the angle needs to 
//    	 * be modified. First we need to find the target heading.
//    	 */		
//		//Only true value of Target Heading for (+,+) quadrant
//    	double targetHeading = Math.toDegrees(Math.atan2(deltaX, deltaY));
//				
//    	if(deltaX >= 0 && deltaY < 0)
//    		targetHeading = 90 - targetHeading; /*Only true for (+,-) quadrant*/
//    	else if(deltaX < 0)
//    		targetHeading = 270 - targetHeading; /*Only true for (-,+/-) quadrants*/
//    	
//    	if(currentHeading - targetHeading < 180 && currentHeading - targetHeading > 0)
//    		angleBetweenTargetAndHeading = 360 - angleBetweenTargetAndHeading;
//    	
//    	/* The trig functions are not completely accurate and produce values near integer values.
//    	 * The following code checks if the angle is near an integer then rounds them up to their
//    	 *  actual accurate integer value if true.
//    	 */    	
//    	int roundedAngle = (int)Math.round(angleBetweenTargetAndHeading);
//    	
//    	if(Math.abs(roundedAngle - angleBetweenTargetAndHeading) <= TOLERANCE)
//    		return roundedAngle;
//    	else
//    		return angleBetweenTargetAndHeading;
    	
    	
    	
/* **************************NEW CODE*********************************************/ 
    	// Finds the target heading in relation to the current point. 
    	double targetHeading = Math.toDegrees(Math.atan2( targetX - currentX, targetY - currentY));
    	if(targetX - currentX < 0) targetHeading += 360;
    	
    	// Finds the amount the heading needs to be adjusted.
    	double adjustAngle = targetHeading - currentHeading; 	// CW is +, CCW is -   	
    	if(adjustAngle < 0 ) adjustAngle = 360 + adjustAngle;	// converts any CCW angle to its CW angle
    	
    	return adjustAngle;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle, 4, 40);
        // draw the window
        //turtle.draw();
        
//        //testing calculateHeadingToPoint
//        for(int i = 0; i < 360; i += 10) {
//        	double result = calculateHeadingToPoint(0, 0, 0, -1, -1);
//        	System.out.println("angleToPoint[" + i + "] = " + result);
//	        if(result != 0 && result - result != 0) {
//	        	System.out.println("ERROR");
//	        	break;
//	        }
//        }
        System.out.println("+X = " + calculateHeadingToPoint(0, 0, 0, 1, 0));	//+X axis
        System.out.println("Q1 = " + calculateHeadingToPoint(0, 0, 0, 1, 1));	//Quadrant I
        System.out.println("+Y = " + calculateHeadingToPoint(0, 0, 0, 0, 1));	//+Y axis
        System.out.println("Q2 = " + calculateHeadingToPoint(15, 0, 0, -1, 1));	//Quadrant II
        System.out.println("-X = " + calculateHeadingToPoint(0, 0, 0, -1, 0));	//-X axis
        System.out.println("Q3 = " + calculateHeadingToPoint(0, 0, 0, -1, -1));	//Quadrant III
        System.out.println("-Y = " + calculateHeadingToPoint(0, 0, 0, 0, -1));	//-Y axis
        System.out.println("Q4 = " + calculateHeadingToPoint(0, 0, 0, 1, -1));	//Quadrant IV
        System.out.println("(1.0, 4, 5, 4, 6) = " + calculateHeadingToPoint(1.0, 4, 5, 4, 6));
        System.out.println("FINISHED");
    }

}
