package org.usfirst.frc.team2496.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2496.systems.Feeder;
import org.usfirst.frc.team2496.systems.Lifter;

public class Robot extends SampleRobot {
	
	//Variables
	private final double deltaTime = 0.005;
	static double time = 0;
	static long startTime = System.currentTimeMillis();
	double lButtonChangeTime = 0;
	
	//Joysticks
	Joystick stick1 = new Joystick(0);

	
	//Sensors
	static Encoder lEnc = new Encoder(0, 1);
	static Encoder rEnc = new Encoder(2, 3);
	ADIS16448_IMU sensor = new ADIS16448_IMU();
	
	//Unique Systems
	ShwinDrive sw;
	
	//Subsystems
	Subsystem feeder;
	Subsystem lifter;


	

	public Robot() {
		sw = new ShwinDrive(4, 1);
		feeder = new Feeder(0, new TalonSRX[] { new TalonSRX(5), new TalonSRX(6), new TalonSRX(7), }, new int[] { 1, 1, 1 });
		lifter = new Lifter(1, new TalonSRX[] { new TalonSRX(2)}, new int[] {1});
	}

	@Override
	public void robotInit() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
	}

	@Override
	public void autonomous() {

	}

	@Override
	public void operatorControl() {

		while (isOperatorControl() && isEnabled()) {
			double y = -stick1.getY();// * stick1.getZ();
			double x = stick1.getX();
			sw.arcadeDrive(y, x, true);
			feeder.control();
			lifter.control();
			if (stick1.getRawButton(1) == true) {
				rEnc.reset();
				lEnc.reset();
			}
			SmartDashboard.putString("l encoder: ", ((double) lEnc.getRaw() / 4.0d + ""));
			SmartDashboard.putString("r encoder: ", ((double) rEnc.getRaw() / 4.0d + ""));
			SmartDashboard.putString("gyro angle: ", sensor.getAngleZ() + "");
			time += deltaTime;
			Timer.delay(deltaTime); // wait for a motor update time
		}

	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}

}