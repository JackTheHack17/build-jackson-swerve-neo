package frc.robot.subsystems.Swerve;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
//import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Constants.SwerveConstants; 

public class SwerveMod { 

    private final CANSparkMax driveMotor; 
    private final CANSparkMax turningMotor;  

    private final RelativeEncoder driveEncoder; 
    private final RelativeEncoder turningEncoder;  

    private final SparkMaxPIDController driveController;  
    private final SparkMaxPIDController turnController; 

    private final CANCoder absoluteAngleEncoder;   
    private final Rotation2d encoderOffset;  
    private final boolean isTurnInverted = true; 
    private final boolean absEncInverted = false; 

    public SwerveMod(int index){ 
        switch (index) { 
            case 1:  
              driveMotor = new CANSparkMax(1,MotorType.kBrushless); 
              turningMotor = new CANSparkMax(2, MotorType.kBrushless); 
              absoluteAngleEncoder = new CANCoder(0); 
              encoderOffset = new Rotation2d(0);   
              break;
            case 2:  
              driveMotor = new CANSparkMax(3,MotorType.kBrushless); 
              turningMotor = new CANSparkMax(4, MotorType.kBrushless); 
              absoluteAngleEncoder = new CANCoder(1); 
              encoderOffset = new Rotation2d(0);  
              break; 
            case 3:  
              driveMotor = new CANSparkMax(5,MotorType.kBrushless); 
              turningMotor = new CANSparkMax(6, MotorType.kBrushless); 
              absoluteAngleEncoder = new CANCoder(1); 
              encoderOffset = new Rotation2d(0);   
              break;
            case 4: 
              driveMotor = new CANSparkMax(7,MotorType.kBrushless); 
              turningMotor = new CANSparkMax(8, MotorType.kBrushless); 
              absoluteAngleEncoder = new CANCoder(0); 
              encoderOffset = new Rotation2d(0);   
              break;
            default: 
              throw new RuntimeException("Module doesn't exist");      
        }
    
    driveMotor.restoreFactoryDefaults(); 
    turningMotor.restoreFactoryDefaults(); 

    driveEncoder = driveMotor.getEncoder();  
    turningEncoder = turningMotor.getEncoder();  

    turningEncoder.setInverted(isTurnInverted);  

    resetEncoders(); 

    turningEncoder.setInverted(isTurnInverted); 

    driveController = driveMotor.getPIDController(); 
    turnController = turningMotor.getPIDController();   

    double[] drivePID = {1,0,0};  

    double[] turnPID = {1,0,0}; 

    driveEncoder.setPositionConversionFactor((Math.PI * Units.inchesToMeters((SwerveConstants.wheelRad * 2)) / SwerveConstants.driveRatio)); 
    
    turningEncoder.setPositionConversionFactor(1 / SwerveConstants.turnRatio); 

    configControl(driveController,false,drivePID, driveEncoder); 

    configControl(turnController, true, turnPID, turningEncoder);  

    
 
    driveMotor.burnFlash(); 
    turningMotor.burnFlash();
    
  }  


  public void configControl(SparkMaxPIDController controller, boolean wrapping, double[] pid, RelativeEncoder feedback){ 
    controller.setP(pid[0]); 
    controller.setI(pid[1]); 
    controller.setD(pid[2]);   
    controller.setFeedbackDevice(feedback); 
    if (wrapping){ 
      controller.setPositionPIDWrappingEnabled(true); 
      controller.setPositionPIDWrappingMaxInput(360); 
      controller.setPositionPIDWrappingMinInput(0);
    }

  }
  public void setDrive(double speed){ 
    driveMotor.set(speed); 
    
  }   
   
  public double getDriveMotPos(){ 
    return driveEncoder.getPosition();
  }  

  public double getDriveVeloc(){ 
    return driveEncoder.getVelocity();
  }

  public void setTurn(double speed){ 
    turningMotor.set(speed);
  } 
  
  public double getTurnMotPos(){ 
    return turningEncoder.getPosition();
  } 

  private double absoluteAnglePos(){ 
    double angle = absoluteAngleEncoder.getPosition();
    angle *= 2.0 * Math.PI;
    angle -= encoderOffset.getRadians();  
    return angle * (absEncInverted ? -1.0 : 1.0);
  }  

  
  /* 
  public void setDriveVelocity(double metersPerSecond){ 
    driveController.setReference(metersPerSecond, com.revrobotics.CANSparkMax.ControlType.kVelocity);
  }
  */
  public void setDrivePosition(double position){ 
    driveController.setReference(position, com.revrobotics.CANSparkMax.ControlType.kPosition);
  }  

  public void setTurnPosition(double position){ 
    turnController.setReference(position, com.revrobotics.CANSparkMax.ControlType.kPosition);
  }   

  private void resetEncoders(){ 
    driveEncoder.setPosition(0); 
    turningEncoder.setPosition(absoluteAnglePos());
  } 

  private SwerveModuleState getState(){ 
    return new SwerveModuleState(getDriveVeloc(), new Rotation2d(getTurnMotPos())); 
  }
  
  public void setDesiredState(SwerveModuleState state){  
    if (Math.abs(state.speedMetersPerSecond) < 0.0001){ 
       stop(); 
       return;
    }
    state = SwerveModuleState.optimize(state, getState().angle);   
    setDrive(state.speedMetersPerSecond/SwerveConstants.maximumVelocity); 
    setTurnPosition(state.angle.getRadians());

  } 

  public void stop() { 
    driveMotor.set(0); 
    turningMotor.set(0); 
  }
  
      

}
