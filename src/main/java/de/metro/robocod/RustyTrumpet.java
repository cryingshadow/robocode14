package de.metro.robocod;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;
import robocode.*;
import robocode.util.Utils;

public class RustyTrumpet extends AdvancedRobot {

    Random rand = new Random();
    static final int SCAN = 0, SEEK = 1, SURROUND = 2;
    int count = 0; // Keeps track of how long we've
    int state = SCAN;
    double gunTurnAmt; // How much to turn our gun when searching
    String trackName; // Name of the robot we're currently tracking
    boolean movingForward = true;
    HashMap<String, Double> dujmanii;

    /**
     * run: Rusty's main run function
     */
    @Override
    public void run() {
        dujmanii = new HashMap<String, Double>(super.getOthers());
        setBodyColor(new Color(218, 165, 32));
        setGunColor(Color.YELLOW);
        setRadarColor(Color.ORANGE);
        setScanColor(Color.ORANGE);
        setBulletColor(Color.WHITE);
        setAdjustRadarForGunTurn(false);
        setAdjustGunForRobotTurn(true);

        while (true) {
            switch (state) {
                case SCAN:
                    if (dujmanii.size() == getOthers()) {
                        //setStop(true);
                        //state = SEEK;
                    } else {
                        setTurnGunLeft(360);
                    }

                    happyFeet();
                    break;
            }

            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        /*switch (state) {
            case SCAN:
                dujmanii.put(e.getName(), e.getDistance());
                return;

        }*/
        double radarTurn
                = // Absolute bearing to target
                getHeadingRadians() + e.getBearingRadians()
                // Subtract current radar heading to get turn required
                - getRadarHeadingRadians();

        setTurnGunRightRadians(Utils.normalRelativeAngle(radarTurn));
        if(e.getDistance() < 400)
        setFire(0.5);

        // ...
    }

    /**
     * onHitRobot: Set him as our new target
     */
    /*public void onHitRobot(HitRobotEvent e) {
        // Only print if he's not already our target.
        if (trackName != null && !trackName.equals(e.getName())) {
            out.println("Tracking " + e.getName() + " due to collision");
        }
        // Set the target
        trackName = e.getName();
        // Back up a bit.
        // Note:  We won't get scan events while we're doing this!
        // An AdvancedRobot might use setBack(); execute();
        gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
        turnGunRight(gunTurnAmt);
        fire(3);
        back(50);
    }*/
    /**
     * onWin: Do a victory dance
     *
     * @param e
     */
    @Override
    public void onWin(WinEvent e) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }

    private void happyFeet() {
        double x = getX(),
                y = getY(),
                maxX = getBattleFieldWidth(),
                maxY = getBattleFieldHeight();

        setAhead(Double.MAX_VALUE);
        // Tell the game we will want to turn right 90
        boolean turnDir = rand.nextBoolean();

        if (getTurnRemaining() == 0) {
            if (turnDir) {
                setTurnRight(rand.nextInt(80) + 40);
            } else {
                setTurnLeft(rand.nextInt(80) + 40);
            }
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        // Bounce off!
        setTurnRight(90);
    }


}
