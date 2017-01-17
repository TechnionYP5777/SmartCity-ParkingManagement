package gui.manager.OrLearning;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
 
@SuppressWarnings("deprecation")
public class Curbstone3D extends Application{
 
    @Override public void start(Stage s) throws Exception {
        s.setTitle("Curbstone 3D");
        Curbstone c = new Curbstone(50,Color.web("#DC143C"),1);
        c.setTranslateX(0);
        c.rx.setAngle(25);
        c.ry.setAngle(45);
 
        Timeline animation = new Timeline();
        animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(c.ry.angleProperty(), 0d)
                ),
                new KeyFrame(Duration.valueOf("20000ms"),
                        new KeyValue(c.ry.angleProperty(), 360d)
                ));
        animation.setCycleCount(Animation.INDEFINITE);
        // create root group
        Parent root = c;
        // translate and rotate group so that origin is center and +Y is up
        root.setTranslateX(200);
        root.setTranslateY(75);
        Line line = new Line(200, 200, 200, 200);
        Rotate rotation = new Rotate(1,Rotate.Y_AXIS);
        rotation.pivotXProperty().bind(line.startXProperty());
        rotation.pivotYProperty().bind(line.startYProperty());
        root.getTransforms().add(rotation);
        // create scene
        Scene scene = new Scene(root, 400,150);
        scene.setCamera(new PerspectiveCamera());
        s.setScene(scene);
        s.show();
        // start spining animation
        animation.play();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
 
    public class Curbstone extends Group {
        final Rotate rx = new Rotate(0,Rotate.X_AXIS);
        final Rotate ry = new Rotate(0,Rotate.Y_AXIS);
        final Rotate rz = new Rotate(0,Rotate.Z_AXIS);
        public Curbstone(double size, Color color, double shade) {
            getTransforms().addAll(rz, ry, rx);
            getChildren().addAll(
                RectangleBuilder.create() // back face
                    .width(2 * size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.5*shade), 1.0))
                    .translateX(-0.5*size)
                    .translateY(-0.5*size)
                    .translateZ(0.5*size)
                    .build(),
               RectangleBuilder.create() // bottom face
                    .width(2 * size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.4*shade), 1.0))
                    .translateX(-0.5*size)
                    .translateY(0)
                    .rotationAxis(Rotate.X_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // right face
                    .width(size).height(size)
                    .fill(Color.GRAY.deriveColor(0.0, 1.0, (1 - 0.3*shade), 1.0))
                    .translateX(-1*size)
                    .translateY(-0.5*size)
                    .rotationAxis(Rotate.Y_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // left face
                    .width(size).height(size)
                    .fill(Color.GRAY.deriveColor(0.0, 1.0, (1 - 0.2*shade), 1.0))
                    .translateX(size)
                    .translateY(-0.5*size)
                    .rotationAxis(Rotate.Y_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // top face
                    .width(2 * size).height(size)
                    .fill(color.deriveColor(0.0, 1.0, (1 - 0.1*shade), 1.0))
                    .translateX(-0.5*size)
                    .translateY(-1*size)
                    .rotationAxis(Rotate.X_AXIS)
                    .rotate(90)
                    .build(),
                RectangleBuilder.create() // top face
                    .width(2 * size).height(size)
                    .fill(color)
                    .translateX(-0.5*size)
                    .translateY(-0.5*size)
                    .translateZ(-0.5*size)
                    .build()
            );
        }
    }
}
