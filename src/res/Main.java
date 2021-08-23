package res;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.*;

public class Main extends Application {

    private GraphicsContext g;

    private double t = 0.0;
    private double oldX = 400, oldY = 300;

    private Parent createContent(){
        Pane root = new Pane();
        Button startButton = new Button("Start");
        root.setPrefSize(800, 600);

        Canvas canvas = new Canvas(800, 600);
        g = canvas.getGraphicsContext2D();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                t += 0.017;
                draw();
            }
        };

        startButton.setOnAction(e -> timer.start());

        root.getChildren().addAll(canvas, startButton);
        return root;
    }

    private void draw() {
        Point2D p = curveFunction();

        g.setStroke(Color.RED);

        double newX = 400 + p.getX();
        double newY = 300 + p.getY();

        if(oldX != 400 && oldY != 300)
            g.strokeLine(oldX, oldY, newX, newY);

        oldX = newX;
        oldY = newY;
    }

    private Point2D curveFunction() {
        double x = sin(t) * (pow(E, cos(t)) - 2 * cos(4*t) - pow(sin(t/12), 5));
        double y = cos(t) * (pow(E, cos(t)) - 2 * cos(4*t) - pow(sin(t/12), 5));

        return new Point2D(x, -y).multiply(50);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("JMCurves");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
