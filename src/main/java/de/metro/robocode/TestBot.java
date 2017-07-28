package de.metro.robocode;

import robocode.*;

public class TestBot extends AdvancedRobot {

    @Override
    public void run() {

        double radius = 100.0;
        double angle = 90.0;

        while (true) {
            ahead(radius);
            //turnLeft(angle);
            turnGunLeft(angle);
            fireBullet(getEnergy());
            for(HitWallEvent e : getHitWallEvents()){
                System.out.println(e.toString());
                clearAllEvents();
            }
        }
        
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }
    
    public void onHitByBullet(HitByBulletEvent e) {
        turnLeft(90 - e.getBearing());
    }

}
