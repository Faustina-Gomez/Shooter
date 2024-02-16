// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public CANSparkMax backSpark = new CANSparkMax(31,MotorType.kBrushless);
  public CANSparkMax frontSpark = new CANSparkMax(32,MotorType.kBrushless);
  public CANSparkMax intakeSpark = new CANSparkMax(30,MotorType.kBrushless);
  public XboxController opJoy = new XboxController(0);
  public TimeOfFlight flightSensor = new TimeOfFlight(40);
   

  // Replace with CommandPS4Controller or CommandJoystick if needed

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {



  }

  public void intakeShoot(){
    if(opJoy.getXButton()){
      intakeSpark.set(-0.80);
    }
  }

  public void teleopPeriodic(){
    //   double speedRate = SmartDashboard.getNumber("SpeedRate", 0.3)* MAX_RATE;
    // double turnRate = SmartDashboard.getNumber("TurnRate", 1)* MAX_RATE/R;
    double intakeSpeed = SmartDashboard.getNumber("intake speed", 0);
    double frontSpeed = SmartDashboard.getNumber("front speed", 0);
    double backSpeed = SmartDashboard.getNumber("back speed", 0);

    flightSensor.setRangingMode(RangingMode.Short,24);
  
    flightSensor.getRange();

    intakeSpark.setIdleMode(IdleMode.kBrake);
    frontSpark.setIdleMode(IdleMode.kBrake);
 

    

   if(opJoy.getBButtonPressed()){
      intakeSpark.set(-1);
    }

    if(flightSensor.getRange()<=275){
      intakeSpark.set(0); 
      frontSpark.set(1);
      backSpark.set(1);
      intakeShoot(); 
    }

    // //opJoy.getBButtonPressed().onTrue(new InstantCommand()->frontSpark.set(0.80));
    if (opJoy.getAButtonPressed()){
      frontSpark.set(1);
      backSpark.set(1);
    } 

    if(opJoy.getYButtonPressed()){
      frontSpark.set(0);
      backSpark.set(0);
      intakeSpark.set(0);
    }


    

    SmartDashboard.putNumber("ToF Range", flightSensor.getRange());

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
