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
        VuforiaTrackables trackables = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        VuforiaTrackable trackable = trackables.get(0);
        trackable.setName("target");

        trackables.activate();
        VuforiaTrackableDefaultListener listener;
        listener = (VuforiaTrackableDefaultListener) trackable.getListener();
        //OpenGLMatrix lastKnownLocation = null;
        OpenGLMatrix latestLocation = null;





        waitForStart();

        while(opModeIsActive()){
            latestLocation = listener.getCameraLocationOnRobot(robot.camera);
            telemetry.addData("cameraname",formatMatrix(latestLocation));
            telemetry.update();


        }

    }
    private String formatMatrix(OpenGLMatrix matrix)
    {
        return matrix.formatAsTransform();
    }
}
