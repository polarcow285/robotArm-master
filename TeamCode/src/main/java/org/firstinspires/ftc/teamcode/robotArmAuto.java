package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.projects.ProjectArm;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="robotArmAuto", group="Mecanum")
//'tag' that displays the opMode on the phone
public class robotArmAuto extends LinearOpMode{
    public ProjectArm robot = new ProjectArm();

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize with hardwareMap configuration
        robot.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            robot.armMotor.setPower(0.5f);
            sleep(1000);
            robot.armMotor.setPower(0);
            sleep(1000);

        }
        robot.armMotor.setPower(0);
    }
}
