package org.usfirst.frc.team2496.systems;

import org.usfirst.frc.team2496.robot.*;

import edu.wpi.first.wpilibj.SpeedController;


public class Feeder extends ShwinSubsystem {

	public Feeder(int id, SpeedController[] motors, int[] revField) {
		super(id, motors, revField);
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
		if(js.getRawButton(7) == true || js.getRawButton(3) == true ) {
			setAll(-0.50);
		}
		else {
			setAll(0.0);
		}
		
	}

}
