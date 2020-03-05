package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

 
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  int practFL = 1;
  int practFR = 2;
  int practBL = 3;
  int practBR = 4;
  
  int nineteenFL = 2;
  int nineteenFR = 3;
  int nineteenBL = 0;
  int nineteenBR = 1;

  int nineteenP1 = 6;
  int nineteenP2 = 7;

  WPI_TalonSRX FL = new WPI_TalonSRX(nineteenFL);
  WPI_TalonSRX FR = new WPI_TalonSRX(nineteenFR);
  WPI_TalonSRX BL = new WPI_TalonSRX(nineteenBL);
  WPI_TalonSRX BR = new WPI_TalonSRX(nineteenBR);
  SpeedControllerGroup left = new SpeedControllerGroup(FL, BL);
  SpeedControllerGroup right = new SpeedControllerGroup(FR, BR);

  MecanumDrive drive = new MecanumDrive(FL, BL, FR, BR);
  DifferentialDrive tank = new DifferentialDrive(left, right);

  DoubleSolenoid shift = new DoubleSolenoid(nineteenP1,nineteenP2);

  XboxController control = new XboxController(0);

  private boolean mode = false;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    shift.set(Value.kReverse);
    mode = false;
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }


  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }


  @Override
  public void teleopPeriodic() {

    if(control.getBumperPressed(Hand.kRight) == true){
      if(mode == false){
        shift.set(Value.kForward);
        mode = true;
      }else{
        shift.set(Value.kReverse);
        mode = false;
      }
    }

    if(mode == false){
      tank.tankDrive(control.getY(Hand.kLeft), control.getY(Hand.kRight));
    }else{
      drive.driveCartesian(control.getY(Hand.kLeft), control.getX(Hand.kLeft), control.getX(Hand.kRight));
    }

  }


  @Override
  public void testPeriodic() {
  }
}
