package meta;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Main extends Application {

    public static String filePathName = "A-n32-k5.txt";

    public static List<Point> pointList;
    public static List<Integer> resultList;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<String> fileNames = List.of(
//                "A-n32-k5.txt",
//                "A-n80-k10.txt",
                "B-n31-k5.txt",
                "B-n78-k10.txt"
//                "P-n16-k8.txt",
//                "P-n76-k5.txt");
        );

        List<Integer> alphaList = List.of(1); //List.of(1, 2);
        List<Integer> betaList = List.of(1); //List.of(1,3);
        List<Double> evaporationList = List.of(0.1); //List.of(0.1, 0.5);
        List<Integer> populationList = List.of(10);// List.of(10, 30, 50);

        fileNames.forEach(
                fileName -> alphaList.forEach(
                        alpha -> betaList.forEach(
                                beta -> evaporationList.forEach(
                                        evaporation -> populationList.forEach(
                                                population -> {
                                                    filePathName = fileName;
                                                    ACO.alpha = alpha;
                                                    ACO.beta = beta;
                                                    ACO.evaporation = evaporation;
                                                    ACO.colonySize = population;

                                                    pointList = FileReader.read(filePathName);
                                                    System.out.print("Start " + Calendar.getInstance().getTime() + " ");
                                                    resultList = ACO.run(filePathName);

                                                    System.out.println(" End " + Calendar.getInstance().getTime());
                                                    try {
                                                        generateWindow(stage);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                        )
                                )
                        )
                )
        );
    }

    public static void generateWindow(Stage stage) throws IOException {
        stage.setTitle(filePathName + " colony_" + ACO.colonySize + " a_" + ACO.alpha + " b_" + ACO.beta + " evaporation_" + ACO.evaporation);
        Pane root = new Pane();

        int minX = pointList.get(0).getX();
        int maxX = pointList.get(0).getY();
        int minY = pointList.get(0).getX();
        int maxY = pointList.get(0).getY();

        Text text = new Text(ACO.resultString + "\n" + ACO.resultString2);
        text.setTranslateX(2);
        text.setTranslateY(1000);
        text.autosize();
        root.getChildren().add(text);

        for (Point point : pointList) {
            minX = Math.min(minX, point.getX());
            maxX = Math.max(maxX, point.getX());
            minY = Math.min(minY, point.getY());
            maxY = Math.max(maxY, point.getY());
        }

        for (Point point : pointList) {
            Circle circle = new Circle();
            circle.setTranslateX(((double) point.getX() * 900) / maxX);
            circle.setTranslateY(((double) point.getY() * 900) / maxY);
            circle.setRadius(5);
            root.getChildren().add(circle);
        }

        for(int i=1; i<resultList.size(); i++) {
            int finalI = i;
            Point from = pointList.stream().filter(point -> resultList.get(finalI - 1) == point.getNumber()).findFirst().get();
            Point to = pointList.stream().filter(point -> resultList.get(finalI) == point.getNumber()).findFirst().get();
            Line line = new Line();
            line.setStartX(((double) from.getX() * 900) / maxX);
            line.setStartY(((double) from.getY() * 900) / maxY);
            line.setEndX(((double) to.getX() * 900) / maxX);
            line.setEndY(((double) to.getY() * 900) / maxY);
            root.getChildren().add(line);
        }

        stage.setScene(new Scene(root, 1000, 1050));
       // stage.show();
        WritableImage snapshot = stage.getScene().snapshot(null);
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File(stage.getTitle() + ".png"));
    }
}
