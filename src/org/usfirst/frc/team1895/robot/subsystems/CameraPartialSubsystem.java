package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.enums.F310Buttons;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.VisionException;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.image.NIVisionException;

public class CameraPartialSubsystem {
	
	private final int cam0;
	private final int cam1;
	private final int cam2;
	private final int cam3;
	
	private int maxCam;
	private int curCamNum;
	private int curCam;
	
	private Image frame;
	
	private CameraServer server;
	public boolean ifFirstTime = true;
	
	/***/
	public CameraPartialSubsystem() {
		cam0 = NIVision.IMAQdxOpenCamera(RobotMap.cam0, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		cam1 = NIVision.IMAQdxOpenCamera(RobotMap.cam1, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		cam2 = NIVision.IMAQdxOpenCamera(RobotMap.cam2, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		cam3 = NIVision.IMAQdxOpenCamera(RobotMap.cam3, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		
		curCam = cam0;
		curCamNum = 0;
		
		maxCam = RobotMap.num_cams - 1;
		
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		server = CameraServer.getInstance();
        server.setQuality(5);
	}
	
	/***/
	public void init() throws NIVisionException, VisionException  {
		NIVision.IMAQdxConfigureGrab(cam0);
		NIVision.IMAQdxStartAcquisition(cam0);
	}

	/***/
	public void run() throws NIVisionException, VisionException {
		boolean forward  = Robot.oi.joy.getRawButton(11);
		boolean backward = Robot.oi.joy.getRawButton(10);
		if(forward && !backward) {
			if(curCamNum < maxCam) {
				changeCam(curCamNum + 1);
				curCamNum++;
			} else {
				changeCam(0);
				curCamNum = 0;
			}
		} else if(backward && !forward) {
			if(curCamNum > 0) {
				changeCam(curCamNum - 1);
				curCamNum--;
			} else {
				changeCam(maxCam);
				curCamNum = maxCam;
			}
		}
		updateCam();
	}
	
	/***/
	public void changeCam(int newId) throws NIVisionException, VisionException  {
		int change;
		switch(newId) {
			case 0:
				change = cam0;
				break;
			case 1:
				change = cam1;
				break;
			case 2:
				change = cam2;
				break;
			case 3:
				change = cam3;
				break;
			default:
				change = cam0;
				break;
		}
		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxConfigureGrab(change);
		NIVision.IMAQdxStartAcquisition(change);
		curCam = change;
	}
	
	/***/
	public void updateCam() throws NIVisionException, VisionException {
		NIVision.IMAQdxGrab(curCam, frame, 1);
		server.setImage(frame);
	}
	
	/***/
	public void endStream() throws NIVisionException, VisionException {
		NIVision.IMAQdxStopAcquisition(curCam);
	}
	
}