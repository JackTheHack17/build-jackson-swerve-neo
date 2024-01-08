package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
//import com.revrobotics.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d; 

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

    driveEncoder.setPosition(0); 

    turningEncoder.setPosition(0);  
    turningEncoder.setInverted(isTurnInverted); 

    driveController = driveMotor.getPIDController(); 
    turnController = turningMotor.getPIDController();  

    driveController.setP(1.0); 
    driveController.setI(0.0); 
    driveController.setD(0.0);   

    driveController.setFeedbackDevice(driveEncoder); 
    
    turnController.setP(1.0); 
    turnController.setI(0.0); 
    turnController.setD(0.0);  

    turnController.setFeedbackDevice(turningEncoder);  

    turnController.setPositionPIDWrappingEnabled(true); 
    turnController.setPositionPIDWrappingMaxInput(360); 
    turnController.setPositionPIDWrappingMinInput(0); 


    
  }  

  public void setDriveVolts(double volts){ 
    driveMotor.setVoltage(volts);
  } 

  public void setTurnVolts(double volts){ 
    turningMotor.setVoltage(volts);
  } 

  public void setDriveVelocity(double metersPerSecond){ 
    driveController.setReference(metersPerSecond, com.revrobotics.CANSparkMax.ControlType.kVelocity);
  }
 
  public void setDrivePosition(double position){ 
    driveController.setReference(position, com.revrobotics.CANSparkMax.ControlType.kPosition);
  }
      

}
