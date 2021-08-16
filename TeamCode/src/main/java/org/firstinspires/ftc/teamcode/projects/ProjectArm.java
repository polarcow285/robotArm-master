package org.firstinspires.ftc.teamcode.projects;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class ProjectArm extends Project {
    //setting up motors
    public DcMotor armMotor = null;
    public WebcamName camera = null;

    @Override
    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;

        //Define and Initialize Motors
        armMotor = hwMap.dcMotor.get("armMotor");


        //Setup Motor directions and Encoder settings
        armMotor.setDirection(DcMotor.Direction.FORWARD);

        //rightMotor .setDirection(DcMotor.Direction.FORWARD);

        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        Stop();
    }
    public void Stop(){
        armMotor.setPower(0);

    }

}

