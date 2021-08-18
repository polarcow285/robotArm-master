package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.projects.ProjectArm;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;




@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="vuforiaAuto", group="Mecanum")
public class vuforiaTest extends LinearOpMode{
    public ProjectArm robot = new ProjectArm();



    public void runOpMode() throws InterruptedException {
        robot.camera = hardwareMap.get(WebcamName.class, "webcam");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "Ab0RVYH/////AAABmWfPx0qmjUTXm/a0Dyd78JVdtOj1ckVOocGBApfqKoV+3aKFVeVoYRP2FVGddUMCPdLrSBvqKrj87XILaYbCHf+Bh401o2vz1+7nXk/oG7l0lvWvAdxCjoZ1OqsQkVT7/ZnuQKcUGGVJ+VzvEcI6KVSG6FyT9cXZLAMRIlboUai1sMAs6h7emEbzWWWbI5ushNuYWiK7x8mgfR4s36wERwFieLHba1HIiYuVF6E4+BEdeX60lH7Us9ASKJrnj+dmAOt4MGqJWViDr0BeKosZrRi4WIUX76vWJ2b8ByfSTJYD+jSbkjRq7amwgeC5kDJMbjcYVVZnCC0dHs8k56qQiReKb7RBio3RQjBLtegRMbHw";
        parameters.cameraName = robot.camera;

        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("VuforiaTest");

        //gets first trackable (image)
        VuforiaTrackable redpandaTrackable = trackables.get(0);
        VuforiaTrackable giraffeTrackable = trackables.get(1);
        redpandaTrackable.setName("redpanda");
        giraffeTrackable.setName("giraffe");

        trackables.activate();

        //create listener based on trackable
        VuforiaTrackableDefaultListener giraffeListener;
        giraffeListener = (VuforiaTrackableDefaultListener) giraffeTrackable.getListener();

        VuforiaTrackableDefaultListener redpandaListener;
        redpandaListener = (VuforiaTrackableDefaultListener) redpandaTrackable.getListener();

        OpenGLMatrix redpandaLocation = null;
        OpenGLMatrix giraffeLocation = null;
        OpenGLMatrix latestLocation = null;





        waitForStart();
        float z;

        while(opModeIsActive()){
            giraffeLocation = giraffeListener.getUpdatedRobotLocation();
            redpandaLocation = redpandaListener.getUpdatedRobotLocation();
            if(redpandaLocation !=null){
                z = redpandaLocation.getTranslation().get(2);
                if(z>250){
                    //far
                    telemetry.clear();
                    telemetry.addData("redpanda", "far!");
                }
                else{
                    telemetry.clear();
                    telemetry.addData("redpanda", "close!");

                }
            }
            else if(giraffeLocation !=null){
                //giraffeLocation = giraffeListener.getUpdatedRobotLocation();
                telemetry.clear();
                telemetry.addData("giraffe", formatMatrix(giraffeLocation));
            }
            /*else{
                telemetry.clear();
                telemetry.addData("no animal found", ":(");
                //telemetry.update();
            }*/
            telemetry.update();
                /*for(int x = 0; x < 2; x++){
                    latestLocation = ((VuforiaTrackableDefaultListener) trackables.get(x).getListener()).getUpdatedRobotLocation();
                }*/







            //latestLocation = listener.getCameraLocationOnRobot(robot.camera);




        }
        stop();

    }
    private String formatMatrix(OpenGLMatrix matrix)
    {
        if(matrix != null){
            return matrix.formatAsTransform();
        }
        else{
            return "not found";
        }

    }
}
