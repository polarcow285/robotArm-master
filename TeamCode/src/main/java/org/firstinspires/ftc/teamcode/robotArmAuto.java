package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.projects.ProjectArm;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="robotArmAuto", group="Mecanum")
//'tag' that displays the opMode on the phone
public class robotArmAuto extends LinearOpMode{
    public ProjectArm robot = new ProjectArm();
    public static final float ENCODERCOUNTSPERREVOLUTION = 1120f;
    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize with hardwareMap configuration
        robot.init(hardwareMap);


        waitForStart();



        while(opModeIsActive()) {
            robot.armMotor.setPower(0.2f);
            while(robot.armMotor.getCurrentPosition() < 10000){
                calculateSpeed();
                //telemetry.addData("currentPosition", robot.armMotor.getCurrentPosition());
                //telemetry.update();
            }
            break;
            /* calculating average speed
            long startTime = System.currentTimeMillis();
            robot.armMotor.setPower(0.2f);
            while(robot.armMotor.getCurrentPosition() < 1120){
                telemetry.addData("currentPosition", robot.armMotor.getCurrentPosition());
                telemetry.update();
            }
            //sleep(1000);

            long endTime = System.currentTimeMillis();
            robot.armMotor.setPower(0);
            //calculate
            long duration = endTime - startTime;
            telemetry.addData("milliseconds", duration);
            telemetry.update();
            //encoder counts per second
            float distance = 1120;
            float eps = distance/((float)duration / 1000f);
            telemetry.addData("eps", eps);
            telemetry.update();
            float rpm = eps * (60f/ENCODERCOUNTSPERREVOLUTION);
            telemetry.addData("rpm", rpm);
            telemetry.update();
            sleep(100000);
            break;
        */


        }
        robot.armMotor.setPower(0);
    }
    private void calculateSpeed(){
        float initialPosition = robot.armMotor.getCurrentPosition();
        sleep(25);
        float finalPosition = robot.armMotor.getCurrentPosition();
        float distance = finalPosition-initialPosition;
        float sampleTimeMS = 25f;
        float eps = distance/(sampleTimeMS/1000);
        float rpm = eps * (60f/ENCODERCOUNTSPERREVOLUTION);
        telemetry.addData("rpm", rpm);
        telemetry.update();
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
