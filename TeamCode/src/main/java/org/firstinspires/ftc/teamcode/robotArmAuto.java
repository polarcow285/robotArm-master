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
    int numberOfCorrections = 0;
    public static final int STOPINTERVALMS = 50;

    boolean isFirstTime = true;

    long currentTime;
    long previousTime;
    long elapsedTime;
    float targetPosition;
    float error;
    float cumulativeError;
    float rateError;
    float lastError;
    float correction;

    //PID constants
    float kp = 1;
    float ki = 1;
    float kd = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize with hardwareMap configuration
        robot.init(hardwareMap);



        waitForStart();



        while(opModeIsActive()) {
            //telemetry.setAutoClear(false);
            targetPosition = 2240;
            if (isFirstTime){
                previousTime = System.currentTimeMillis();
                sleep(1);
                lastError = 0;
                isFirstTime = false;
            }
            while(true){
                float finalError = computePID();
                if(finalError == 0){
                    break;
                }
                goToEncoderPositionINC(finalError, 40);
                //telemetry.addData("current position",robot.armMotor.getCurrentPosition());
                //telemetry.update();
            }
            robot.armMotor.setPower(0);
            break;
            /*targetPosition = 2240;
            if (isFirstTime){
                previousTime = System.currentTimeMillis();
                lastError = 0;
                isFirstTime = false;
            }
            goToEncoderPositionINC(computePID(robot.armMotor.getCurrentPosition()), 80);
            sleep(100);
             */

            //goToEncoderPositionPID(2240, 175, 0);
            /*goToEncoderPositionPID(2240, 175, 0);
            long elapsedTime = System.currentTimeMillis() - begTime;
            telemetry.addData("elapsedTime", elapsedTime);
            telemetry.update();

            goToEncoderPositionPID(0, 175, 0);
            telemetry.addData("done2", "done2");
            telemetry.update();*/
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
    private float computePID(){
        currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - previousTime;

        error = targetPosition - robot.armMotor.getCurrentPosition();
        cumulativeError += error * elapsedTime; //integral - step function approximation
        rateError = (error - lastError)/elapsedTime; //derivative

        if(error == 0){
            ki = 0;
        }
        correction = (kp * error) + (ki * cumulativeError) + (kd * rateError);


        lastError = error;
        previousTime = currentTime;

        telemetry.addData("correction", correction);
        telemetry.update();
        return correction;

    }
    private void stopMotor(){
        robot.armMotor.setPower(0);
        //routine that waits for motor to physically stop after power has been shut down
        waitForStop();
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
    private boolean errorCheck(int targetPosition, int tolerance, int delta){
        //correction speed rpm of 10 or greater causes motor to overshoot
        boolean noError;
        telemetry.addData("current position", robot.armMotor.getCurrentPosition());
        telemetry.update();
        if(Math.abs(delta) <= tolerance){
            noError = true;
        }
        else{
            noError = false;
            telemetry.addData("correcting", "kjasdnf");
            telemetry.update();
            sleep(1000);
            goToEncoderPositionINC(delta, 5);
        }

        return noError;
        //sleep(delayMS);
    }
    private boolean isStopped(){
        int initialPosition = robot.armMotor.getCurrentPosition();
        sleep(STOPINTERVALMS);
        int finalPosition = robot.armMotor.getCurrentPosition();
        telemetry.addData("isStopped", initialPosition == finalPosition);
        telemetry.update();
        return initialPosition == finalPosition;
    }
    private void goToEncoderPositionINC(float targetEncoderIncrement, float rpm){
        float initialPosition = robot.armMotor.getCurrentPosition();
        if(targetEncoderIncrement > 0){
            setSpeed(rpm, 0);
            //robot.armMotor.setPower(0.2);
            while(robot.armMotor.getCurrentPosition() <  initialPosition + targetEncoderIncrement){
                sleep(1);
                break;
                //calculateSpeed();
                //telemetry.addData("heyyyyyyyy", robot.armMotor.getCurrentPosition());
                //telemetry.update();
            }
        }
        else{
            setSpeed(-rpm, 0);
            while(robot.armMotor.getCurrentPosition() >  initialPosition + targetEncoderIncrement){
                sleep(1);
                break;
                //calculateSpeed();
                //telemetry.addData("im stuck", "stuck");
                //telemetry.update();
            }
        }
        //stopMotor();


        //sleep(delayMS);
    }
    //does all corrections, does not return until all corrections are completed
    private void goToEncoderPositionPID(int targetEncoderPosition, float rpm, int tolerance){
        numberOfCorrections = 0;
        int delta = robot.armMotor.getCurrentPosition() - targetEncoderPosition;
        if (Math.abs(delta) <= tolerance) {
            stopMotor();
        }
        else{
            //try to go to the target position (1st try)
            goToEncoderPositionABS(targetEncoderPosition, rpm);
            //sleep(200);
            //update delta
            int currentPosition = robot.armMotor.getCurrentPosition();
            delta = currentPosition - targetEncoderPosition;
            while(Math.abs(delta)>tolerance){
                numberOfCorrections++;
                telemetry.addData("correcting", numberOfCorrections);
                telemetry.addData("delta", delta);
                telemetry.addData("current position", currentPosition);
                telemetry.update();

                //goToEncoderPositionABS(targetEncoderPosition, 5);
                goToEncoderPositionINC(-delta, 10);
                //sleep(250);
                //goToEncoderPositionINC(-delta, 5);
                currentPosition = robot.armMotor.getCurrentPosition();
                delta = currentPosition - targetEncoderPosition;

            }
            //after first attempt, corrects continuously as needed
           /* while(errorCheck(targetEncoderPosition, tolerance, delta) == false) {
                delta = robot.armMotor.getCurrentPosition() - targetEncoderPosition;
            }*/
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
    private void waitForStop(){
        while(isStopped() == false){

        }
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
