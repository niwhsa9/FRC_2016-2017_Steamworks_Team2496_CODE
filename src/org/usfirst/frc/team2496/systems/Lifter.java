package org.usfirst.frc.team2496.systems;

import org.usfirst.frc.team2496.robot.Subsystem;
import org.usfirst.frc.team2496.systems.*;

import edu.wpi.first.wpilibj.SpeedController;

public class Lifter extends Subsystem {

	public Lifter(int id, SpeedController[] motors, int[] revField) {
		super(id, motors, revField);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void consoleLog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dashboardLog() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void control() {
		if (js.getRawButton(2) == true) {

			setAll(1.0);

		} else if ((js.getRawButton(4) == true)) {
			setAll(-1.0);
		}

		else {
			setAll(0.0);

		}
		
	}

}
