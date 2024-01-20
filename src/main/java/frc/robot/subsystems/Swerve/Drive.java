package frc.robot.subsystems.Swerve; 


import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase{
    private final SwerveMod frontLeft = new SwerveMod(1); 
    private final SwerveMod frontRight = new SwerveMod(2); 
    private final SwerveMod backLeft = new SwerveMod(3); 
    private final SwerveMod backRight = new SwerveMod(4);  
    private final Pigeon2 pigeon = new Pigeon2(4);

    public Drive(){ 

    } 

    public double getHeading(){ 
        return Math.IEEEremainder(pigeon.getYaw(), 360);
    } 

    public Rotation2d getRotation2d(){ 
        return Rotation2d.fromDegrees(getHeading()); 
    }  
    
    public void cutMotors(){ 
        frontLeft.stop(); 
        frontRight.stop(); 
        backLeft.stop(); 
        backRight.stop();
    } 

    public void setModuleStates(SwerveModuleState[] states){  
       frontLeft.setDesiredState(states[0]); 
       frontRight.setDesiredState(states[1]); 
       backLeft.setDesiredState(states[2]); 
       backRight.setDesiredState(states[3]);  


    }
    

}
