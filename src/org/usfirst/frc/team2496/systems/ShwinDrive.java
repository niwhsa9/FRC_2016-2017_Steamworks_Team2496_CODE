package org.usfirst.frc.team2496.systems;

import org.usfirst.frc.team2496.robot.Robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

public class ShwinDrive {
	
	SpeedController driveMotors[] = new SpeedController[4]; // array of motors
	boolean flag = false; // flag indicating 4 wheel drive
	double prevTurn = 0;
	double prevStraight;
	double prevEnc0;
	double prevEnc1;
	private final double cp = 0.002d; // 0.0004 value we used at scrimmage
										//.002 worked really well

	
	public ShwinDrive(int bl, int br, int fl, int fr) {
		this.driveMotors[0] = new TalonSRX(bl);
		this.driveMotors[1] = new TalonSRX(br);
		this.driveMotors[2] = new TalonSRX(fl);
		this.driveMotors[3] = new TalonSRX(fr);
		flag = true;

	}

	public ShwinDrive(int l, int r) {
		this.driveMotors[0] = new TalonSRX(l);
		this.driveMotors[1] = new TalonSRX(r);
	}
	
	//test, don't trust me
	/*
	public void turnRight(double degrees, double speed){
		double lEncoder = (double) Robot.lEnc.getRaw() ;//* -1;
		double rEncoder = (double) Robot.rEnc.getRaw();// * -1;
		double ticksToTurn = (degrees / 360) * 40 *400*Math.PI;
		double oneWheelTicks = (ticksToTurn / ( 6));
		this.driveMotors[0].set(speed);
		this.driveMotors[2].set(speed);
		this.driveMotors[1].set(speed);
		this.driveMotors[3].set(speed);
		
		while(lEncoder > -oneWheelTicks&&rEncoder <oneWheelTicks){
			lEncoder = (double) Robot.lEnc.getRaw() ;
			rEncoder = (double) Robot.rEnc.getRaw();
			System.out.println(lEncoder+" "+rEncoder);
		}
	}*/
	
	//400 ticks 
	//Robot is 28 inches across from wheel to wheel 
	//90 degrees = 1/4 of the circumference of the whole robot
	//.25 * 28 * pi = 21.99114857512 inches
	
	public void turn(double degrees, double speed) {
		double lEncoder = (double) Robot.lEnc.getRaw() ;//* -1;
		double rEncoder = (double) Robot.rEnc.getRaw();// * -1;
		double ticksToTurn = (degrees / 360) * 40 *400*Math.PI;
		double oneWheelTicks = (ticksToTurn / ( 6));
		this.driveMotors[0].set(-speed);
		this.driveMotors[2].set(-speed);
		this.driveMotors[1].set(-speed);
		this.driveMotors[3].set(-speed);
		
		while(lEncoder < oneWheelTicks&&rEncoder > -oneWheelTicks){
			lEncoder = (double) Robot.lEnc.getRaw() ;
			rEncoder = (double) Robot.rEnc.getRaw();
			System.out.println(lEncoder+" "+rEncoder);
		}
		/*
		while(lEncoder < 500) {
			this.driveMotors[0].set(-speed);
			this.driveMotors[2].set(-speed);
			lEncoder = (double) Robot.lEnc.getRaw();// * -1;
			System.out.println(lEncoder);
		}
		while(rEncoder > -500) {
			this.driveMotors[1].set(-speed);
			this.driveMotors[3].set(-speed);
			rEncoder = (double) Robot.rEnc.getRaw() ;//* -1;
			
		}*/
		this.driveMotors[0].set(0);
		this.driveMotors[1].set(0);
		this.driveMotors[2].set(0);
		this.driveMotors[3].set(0);
	} 
	public void turnLeft(double degrees, double speed) {
		double lEncoder = (double) Robot.lEnc.getRaw() ;//* -1;
		double rEncoder = (double) Robot.rEnc.getRaw();// * -1;
		double ticksToTurn = (degrees / 360) * 40 *400*Math.PI;
		double oneWheelTicks = (ticksToTurn / ( 6));
		this.driveMotors[0].set(-speed);
		this.driveMotors[2].set(-speed);
		this.driveMotors[1].set(-speed);
		this.driveMotors[3].set(-speed);
		
		while(lEncoder > -oneWheelTicks && rEncoder < oneWheelTicks){
			lEncoder = (double) Robot.lEnc.getRaw() ;
			rEncoder = (double) Robot.rEnc.getRaw();
			System.out.println(lEncoder+" "+rEncoder);
		}
		/*
		while(lEncoder < 500) {
			this.driveMotors[0].set(-speed);
			this.driveMotors[2].set(-speed);
			lEncoder = (double) Robot.lEnc.getRaw();// * -1;
			System.out.println(lEncoder);
		}
		while(rEncoder > -500) {
			this.driveMotors[1].set(-speed);
			this.driveMotors[3].set(-speed);
			rEncoder = (double) Robot.rEnc.getRaw() ;//* -1;
			
		}*/
		this.driveMotors[0].set(0);
		this.driveMotors[1].set(0);
		this.driveMotors[2].set(0);
		this.driveMotors[3].set(0);
	}
	
	public void oneWheelTurn(double degrees, int wheel, double speed) {
		
	}

	public void arcadeDrive(double forward, double turn, boolean fack) { // mother
																			// facker
		turn = 0;// TODO remove

		double error = 0;
		boolean straight = false;
		double l;
		double r;
		double fd = 1;
		double td = 1;
		if (forward <= 0.0)
			fd = -1;
		if (turn <= 0.0)
			td = -1;
		forward *= forward * fd;
		turn *= turn * td;
		l = forward + turn;
		r = forward - turn;

		double lEncoder = (double) Robot.lEnc.getRaw() * -1;
		double rEncoder = (double) Robot.rEnc.getRaw() * -1;

		if (Math.abs(prevTurn) >= 0.2 && Math.abs(turn) <= 0.2) {
			Robot.rEnc.reset();
			Robot.lEnc.reset();
		}

		prevTurn = turn;

		if (Math.abs(turn) <= 0.2 && Math.abs(forward) >= 0.1) { // if going
																	// straight

			
			error = (lEncoder - rEncoder) * cp;
			r += error;
		}
		double m = Math.max(l, r);
		if (Math.abs(m) >= 1.0) {
			double s = 1.0 / m;
			l *= s;
			r *= s;
		}
		// System.out.println("left: " + l + " right: " + r);
	

		r *= -1;
		if (flag == true) {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
			driveMotors[2].set(l);
			driveMotors[3].set(r);
		} else {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
		}

	}

	public void arcadeDrive(double forward, double turn) { // bound (0.0)+-(1.0)
		double l;
		double r;
		double fd = 1;
		double td = 1;
		if (forward <= 0.0)
			fd = -1;
		if (turn <= 0.0)
			td = -1;
		forward *= forward * fd;
		turn *= turn * td;
		l = forward + turn;
		r = forward - turn;

		double m = Math.max(l, r);
		if (Math.abs(m) >= 1.0) {
			double s = 1.0 / m;
			l *= s;
			r *= s;
		}

		r *= -1;

		if (flag == true) {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
			driveMotors[2].set(l);
			driveMotors[3].set(r);
		} else {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
		}

	}

	@Deprecated
	public void arcadeDrive(double forward) { // bound (0.0)+-(1.0)
		double l = forward;
		double r = forward;

		driveMotors[0].set(l);
		driveMotors[1].set(r);

	}

	public void tankDrive(double s1, double s2) {
		s1 *= -1;
		if (flag == true) {
			driveMotors[0].set(s1);
			driveMotors[1].set(s2);
			driveMotors[2].set(s1);
			driveMotors[3].set(s2);
		} else {
			driveMotors[0].set(s1);
			driveMotors[1].set(s2);
		}
	}
	

}
