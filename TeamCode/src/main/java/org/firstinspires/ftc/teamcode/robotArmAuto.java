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
            goToEncoderPositionPID(2240, 175, 0);

            sleep(2000);

            /*for(int x = 0; x < 20; x++){
                goToEncoderPositionINC(56, 10);
                sleep(100);
            }
            goToEncoderPositionINC(-1120, 10);
            sleep(3000);
            /*setSpeed(35, 5000);
            while(robot.armMotor.getCurrentPosition() < (ENCODERCOUNTSPERREVOLUTION*3)){
                calculateSpeed();
            }

            setSpeed(70, 5000);
            while(robot.armMotor.getCurrentPosition() < (ENCODERCOUNTSPERREVOLUTION*6)){
                calculateSpeed();
            }
            setSpeed(140, 5000);
            while(robot.armMotor.getCurrentPosition() < (ENCODERCOUNTSPERREVOLUTION*9)){
                calculateSpeed();
            }
            break;


            //calculating average speed
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
    private void stopMotor(){
        robot.armMotor.setPower(0);
        //sleep(delayMS);
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
    private void setSpeed(float rpm, int delayMS){
        float power = rpm/175f;
        robot.armMotor.setPower(power);
        //sleep(delayMS);
    }
    private boolean errorCheck(int targetPosition, int tolerance){
        //correction speed rpm of 10 or greater causes motor to overshoot
        boolean noError;
        if(Math.abs(robot.armMotor.getCurrentPosition() - targetPosition) <= tolerance){
            noError = true;
        }
        else{
            noError = false;
            telemetry.addData("correcting", "kjasdnf");
            telemetry.update();
            sleep(1000);
            goToEncoderPositionABS(targetPosition, 5);
        }
        telemetry.addData("current position", robot.armMotor.getCurrentPosition());
        telemetry.update();
        return noError;
        //sleep(delayMS);
    }
    private void goToEncoderPositionINC(int targetEncoderIncrement, float rpm){
        float initialPosition = robot.armMotor.getCurrentPosition();
        if(targetEncoderIncrement > 0){
            setSpeed(rpm, 0);
            while(robot.armMotor.getCurrentPosition() <  initialPosition + targetEncoderIncrement){
                //calculateSpeed();
            }
        }
        else{
            setSpeed(-rpm, 0);
            while(robot.armMotor.getCurrentPosition() >  initialPosition + targetEncoderIncrement){
                //calculateSpeed();
            }
        }
        stopMotor();


        //sleep(delayMS);
    }
    //does all corrections, does not return until all corrections are completed
    private void goToEncoderPositionPID(int targetEncoderPosition, float rpm, int tolerance){
        int delta = Math.abs(robot.armMotor.getCurrentPosition() - targetEncoderPosition);
        telemetry.addData("delta", delta);
        telemetry.update();

        if (delta <= tolerance) {
            stopMotor();
        }
        else{
            goToEncoderPositionABS(targetEncoderPosition, rpm);
            while(errorCheck(targetEncoderPosition, tolerance) == false) {

            }
        }

        /*
        else if (targetEncoderPosition > initialPosition){
            setSpeed(rpm, 0);
            while(robot.armMotor.getCurrentPosition() <  targetEncoderPosition){
                //calculateSpeed();
            }
        }
        else {
            setSpeed(-rpm, 0);
            while (robot.armMotor.getCurrentPosition() > targetEncoderPosition) {
                //calculateSpeed();
            }
        }*/
    }
    //no correction
    private void goToEncoderPositionABS(int targetEncoderPosition, float rpm){
        float initialPosition = robot.armMotor.getCurrentPosition();
        if (targetEncoderPosition > initialPosition){
            setSpeed(rpm, 0);
            while(robot.armMotor.getCurrentPosition() <  targetEncoderPosition){
                //calculateSpeed();
            }
        }
        else {
            setSpeed(-rpm, 0);
            while (robot.armMotor.getCurrentPosition() > targetEncoderPosition) {
                //calculateSpeed();
            }
        }
        stopMotor();
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
