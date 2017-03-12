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
import org.usfirst.frc.team2496.systems.ShwinDrive;

public class Robot extends SampleRobot {

	// Variables
	private final double UNIVERSAL_SPEED = 0.5d;
	private final double deltaTime = 0.005;
	static double time = 0;
	static long startTime = System.currentTimeMillis();
	double lButtonChangeTime = 0;

	// Timer
	int counter = 0;

	// Joysticks
	Joystick stick1 = new Joystick(0);
	Joystick stick2 = new Joystick(1);

	// Sensors
	public static Encoder lEnc = new Encoder(2, 6);
	public static Encoder rEnc = new Encoder(4, 1); // = new Encoder(2, 3);
	// public static ADIS16448_IMU sensor = new ADIS16448_IMU();

	// Unique Systems
	ShwinDrive sw;

	// Subsystems
	ShwinSubsystem feeder;
	ShwinSubsystem lifter;

	public Robot() {

		// sw = new ShwinDrive(4, 1);
		sw = new ShwinDrive(3, 4, 0, 1);
		feeder = new Feeder(0, new TalonSRX[] { new TalonSRX(5), new TalonSRX(6), new TalonSRX(7), new TalonSRX(8) },
				new int[] { 1, -1, 1, -1 });
		feeder.setJoystick(stick1);
		lifter = new Lifter(1, new TalonSRX[] { new TalonSRX(2) }, new int[] { 1 });
		lifter.setJoystick(stick1);

	}

	@Override
	public void robotInit() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
	}

	@Override
	public void autonomous() {
		/*
		 * /*while(isAutonomous() && isEnabled()) { rEnc.reset(); lEnc.reset();
		 * while(Math.abs((double) rEnc.getRaw() / 4.0d) <= 100) {
		 * sw.arcadeDrive(.5, 0.0, true); } while(Math.abs((double)
		 * rEnc.getRaw() / 4.0d) <= 1702) { sw.arcadeDrive(0.6, 0.0, true); }
		 * sw.arcadeDrive(0.0, 0.0);
		 */
		// sw.turn(30.0, .3);
		/*
		 * while(Math.abs((double) rEnc.getRaw() / 4.0d) <= 700) {
		 * sw.arcadeDrive(0.35, 0.0, true); }
		 */

		if (isAutonomous() && isEnabled()) {
			int AutonomousMode = 0;
			//0 = our right side
			//1 = middle
			//2 (not tested) = our left side
			//other = what the heck ashwin
			switch (AutonomousMode) {
			case 0:
				rEnc.reset();
				lEnc.reset();
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 100) {
					sw.arcadeDrive(.5, 0.0, true);
				}
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 1702) {
					sw.arcadeDrive(0.6, 0.0, true);
				}
				sw.arcadeDrive(0.0, 0.0);
				Timer.delay(1.0);
				rEnc.reset();
				lEnc.reset();
				sw.turn(66.0, .5);
				rEnc.reset();
				lEnc.reset();
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 575) {
					sw.arcadeDrive(.5, 0.0, true);
				}
				sw.arcadeDrive(0.0, 0.0);

				rEnc.reset();
				lEnc.reset();
				break;
			case 1:
				rEnc.reset();
				lEnc.reset();
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 100) {
					sw.arcadeDrive(.5, 0.0, true);
				}
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 1702) {
					sw.arcadeDrive(0.6, 0.0, true);
				}
				sw.arcadeDrive(0.0, 0.0);
				lEnc.reset();
				rEnc.reset();
				break;
			case 2:
				rEnc.reset();
				lEnc.reset();
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 100) {
					sw.arcadeDrive(.5, 0.0, true);
				}
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 1702) {
					sw.arcadeDrive(0.6, 0.0, true);
				}
				sw.arcadeDrive(0.0, 0.0);
				Timer.delay(1.0);
				rEnc.reset();
				lEnc.reset();
				sw.turn(294.0, UNIVERSAL_SPEED);
				//can test, don't hate me if not working
				//sw.turnRight(294.0, UNIVERSAL_SPEED);
				rEnc.reset();
				lEnc.reset();
				while (Math.abs((double) rEnc.getRaw() / 4.0d) <= 575) {
					sw.arcadeDrive(.5, 0.0, true);
				}
				sw.arcadeDrive(0.0, 0.0);

				rEnc.reset();
				lEnc.reset();
				break;
				default:
					//what the heck ashwin
					System.out.println("Autonomous should be mode 0~2 only, what the heck ashwin");
					break;
			}

		}
		//can remove, just to make sure
		sw.arcadeDrive(0.0, 0.0);
		rEnc.reset();
		lEnc.reset();
	}

	@Override
	public void operatorControl() {

		while (isOperatorControl() && isEnabled()) {
			// double y = -stick1.getY();// * stick1.getZ();
			// double x = stick1.getX();
			// sw.arcadeDrive(y, x, true);
			double s1 = stick1.getY();
			double s2 = stick2.getY();
			sw.tankDrive(s1, s2);
			feeder.control();
			lifter.control();
			if (stick1.getRawButton(1) == true) {
				rEnc.reset();
				lEnc.reset();
			}
			if (stick1.getRawButton(5)) {
				sw.turn(30.0, UNIVERSAL_SPEED);
			}
			SmartDashboard.putString("l encoder: ", ((double) lEnc.getRaw() / 4.0d + ""));
			SmartDashboard.putString("r encoder: ", ((double) rEnc.getRaw() / 4.0d + ""));
			// SmartDashboard.putString("gyro angle: ", sensor.getAngleZ() +
			// "");
			time += deltaTime;
			Timer.delay(deltaTime); // wait for a motor update time
			if (counter < 500) {
				counter++;
			}
		}

	}

	@Override
	public void test() {

	}

}