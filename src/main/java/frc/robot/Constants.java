// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  } 

  public static class SwerveConstants { 
    public static final double wheelRad = 3;  
    public static final double driveRatio = (50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0); 
    public static final double turnRatio = 150 / 70; 
    public static final double maximumVelocity = 40;
    public static final double maxLinearAccel = 30;
    public static final double maxAngleAccel = 15; 
    public static final double maximumTorque = 0; 
    
    public static final SwerveDriveKinematics swerveKin = new SwerveDriveKinematics( 
      new Translation2d(Math.PI, -Math.PI), 
      new Translation2d(Math.PI, Math.PI), 
      new Translation2d(-Math.PI, -Math.PI), 
      new Translation2d(-Math.PI, Math.PI)
    );
    
  } 
}
