package org.usfirst.frc.team166.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team166.robot.commands.FarLeftAuto;
import org.usfirst.frc.team166.robot.commands.FarRightAuto;
import org.usfirst.frc.team166.robot.commands.MidAuto;
import org.usfirst.frc.team166.robot.commands.MidLeftAuto;
import org.usfirst.frc.team166.robot.commands.MidRightAuto;
import org.usfirst.frc.team166.robot.commands.PaperWeightAuto;
import org.usfirst.frc.team166.robot.commands.UberAuto;
import org.usfirst.frc.team166.robot.commands.drive.LowGear;
import org.usfirst.frc.team166.robot.subsystems.AManipulators;
import org.usfirst.frc.team166.robot.subsystems.AimShooter;
import org.usfirst.frc.team166.robot.subsystems.Drive;
import org.usfirst.frc.team166.robot.subsystems.Intake;
import org.usfirst.frc.team166.robot.subsystems.IntakeRoller;
import org.usfirst.frc.team166.robot.subsystems.Shooter;
import org.usfirst.frc.team166.robot.subsystems.Vision;
import org.usfirst.frc.team166.robot.triggers.CopilotLeftTrigger;
import org.usfirst.frc.team166.robot.triggers.CopilotRightTrigger;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {

	// subsystems
	public static Drive drive;
	public static Intake intake;
	public static Shooter shooter;
	public static AimShooter aimShooter;
	public static Vision vision;
	public static IntakeRoller intakeRoller;
	public static AManipulators aManipulators;
	private static SendableChooser autoChooser;

	// triggers
	public static CopilotLeftTrigger copilotLeftTrigger;
	public static CopilotRightTrigger copilotRightTrigger;
	public static OI oi;

	Command autonomousCommand;
	Command lowGearCommand;

	/**
	 * This function is run when the robot is first started up and should be used for any initialization code.
	 */
	@Override
	public void robotInit() {
		drive = new Drive();
		intake = new Intake();
		shooter = new Shooter();
		aimShooter = new AimShooter();
		vision = new Vision();
		intakeRoller = new IntakeRoller();
		aManipulators = new AManipulators();

		// autochooser
		autoChooser = new SendableChooser();

		// triggers
		copilotRightTrigger = new CopilotRightTrigger();
		copilotLeftTrigger = new CopilotLeftTrigger();

		oi = new OI();
		// instantiate the command used for the autonomous period
		lowGearCommand = new LowGear();

		// auto chooser commands
		autoChooser.addDefault("FarLeftAuto", new FarLeftAuto());
		autoChooser.addObject("MidLeftAuto", new MidLeftAuto());
		autoChooser.addObject("MidAuto", new MidAuto());
		autoChooser.addObject("MidRightAuto", new MidRightAuto());
		autoChooser.addObject("FarRightAuto", new FarRightAuto());
		autoChooser.addObject("Uber Auto", new UberAuto());
		autoChooser.addObject("Paper Weight", new PaperWeightAuto());

		SmartDashboard.putData("Autonomous", autoChooser);

		autonomousCommand = (Command) autoChooser.getSelected();
		// autonomousCommand = new FarLeftAuto();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			lowGearCommand.start();
		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			lowGearCommand.start();
		autonomousCommand.cancel();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
