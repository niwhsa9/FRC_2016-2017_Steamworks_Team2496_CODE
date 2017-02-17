package org.usfirst.frc.team2496.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShwinAutoLib {
	ShwinDrive sw;

	public ShwinAutoLib(ShwinDrive sw) {
		this.sw = sw;
	}

	void vertical(double distance, double speed, double direction) { // 627
		// ticks
		// per
		Robot.rEnc.reset();
		Robot.lEnc.reset();
		double f = ((distance / (6.0 * Math.PI))) * 400;
		SmartDashboard.putString("val", f + "");
		double fDir = (speed * direction);
		int r = 0;

		// SensorValue[gyro] = 0;
		while (Math.abs(Robot.rEnc.getRaw()) < f) {
			r++;
			sw.arcadeDrive(fDir, 0.0);
		}
		sw.arcadeDrive(fDir * -1, 0.0);
		Timer.delay(0.3);
		sw.arcadeDrive(0.0, 0.0);

	}

}
