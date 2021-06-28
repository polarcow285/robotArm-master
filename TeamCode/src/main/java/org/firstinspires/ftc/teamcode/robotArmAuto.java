package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.projects.ProjectArm;
import com.qualcomm.robotcore.hardware.DcMotor;
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
            robot.armMotor.setPower(0.6f);
            telemetry.addData("currentPosition", robot.armMotor.getCurrentPosition());
            telemetry.update();
            //sleep(1000);
            if(robot.armMotor.getCurrentPosition() >= 500){
                robot.armMotor.setPower(0);
                break;
            }




        }
        robot.armMotor.setPower(0);
    }
    private void WaitTillTargetReached(int tolerance){
        int difference = Math.abs(robot.armMotor.getTargetPosition() - robot.armMotor.getCurrentPosition());
        while(difference > tolerance)
        //while(robot.leftMotor.isBusy() || robot.rightMotor.isBusy())
        {
            difference = Math.abs(robot.armMotor.getTargetPosition() - robot.armMotor.getCurrentPosition());
            telemetry.addData("position", robot.armMotor.getCurrentPosition());
            telemetry.addData("difference", difference);
            telemetry.update();

            sleep(1);
        }
        telemetry.addData("DONE", "done");
        telemetry.update();
    }
}
