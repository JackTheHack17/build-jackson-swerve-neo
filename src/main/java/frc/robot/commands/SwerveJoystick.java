package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.SwerveConstants;
import frc.robot.subsystems.Swerve.Drive;


public class SwerveJoystick extends CommandBase { 
    private Drive swerveDrive; 
    private DoubleSupplier latSpeed; 
    private DoubleSupplier lonSpeed; 
    private DoubleSupplier turnSpeed; 
    private boolean isFieldOriented = true;  
    private final double DEADBAND = 0.0001; 

    public SwerveJoystick(DoubleSupplier x,  
    DoubleSupplier y, DoubleSupplier turn, Drive swerve){ 
       latSpeed = x; 
       lonSpeed = y; 
       turnSpeed = turn;  
       swerveDrive = swerve; 
       addRequirements(swerve);
    }  

    @Override 
    public void execute(){ 
       double xSpeed = latSpeed.getAsDouble(); 
       double ySpeed = lonSpeed.getAsDouble(); 
       double turningSpeed = turnSpeed.getAsDouble(); 
       
       xSpeed = (Math.abs(xSpeed) > DEADBAND) ? xSpeed : 0; 
       ySpeed = (Math.abs(ySpeed) > DEADBAND) ? ySpeed : 0;   
       turningSpeed = (Math.abs(turningSpeed) > DEADBAND) ? turningSpeed : 0; 

       xSpeed = new SlewRateLimiter(SwerveConstants.maxLinearAccel).calculate(xSpeed) * SwerveConstants.maximumVelocity;
       ySpeed = new SlewRateLimiter(SwerveConstants.maxLinearAccel).calculate(xSpeed) * SwerveConstants.maximumVelocity;
       turningSpeed = new SlewRateLimiter(SwerveConstants.maxAngleAccel).calculate(turningSpeed) * SwerveConstants.maximumTorque;
       ChassisSpeeds chassSpeeds;
       if (isFieldOriented){ 
          chassSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, 
          turningSpeed, swerveDrive.getRotation2d()); 
       } else { 
          chassSpeeds = new ChassisSpeeds(xSpeed, ySpeed, turningSpeed); 
       } 

       SwerveModuleState[] states = SwerveConstants.swerveKin.toSwerveModuleStates(chassSpeeds);
       swerveDrive.setModuleStates(states);
    }
}
