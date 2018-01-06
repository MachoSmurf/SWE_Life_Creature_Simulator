package ViewPackage;

import ModelPackage.*;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLSimulatorController extends UIController implements Initializable, ILifeResult {

    public Canvas canvSimulation1;
    public Button btnTest;

    public Label lblZoomValue1;
    public Slider sldZoom1;

    private int zoom = 20;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void updateSimulationResults(StepResult simStatus) {
        //drawGrid(simStatus.getCurrentGrid());
    }

    public void onZoomSlider1Finished(){
        zoom = (int)sldZoom1.getValue();
        lblZoomValue1.setText(Integer.toString(zoom));
    }

    public void onTestClick(){
        ///!!!DEVELOPMENT ONLY!!!

        Grid g = new Grid(100,100);
        g.setPointType(new Point(10,10), GridPointType.Ground);

        drawGrid(g);
    }

    private void drawGrid(IGrid g){
        GraphicsContext gc = canvSimulation1.getGraphicsContext2D();
        gc.clearRect(0,0, canvSimulation1.getWidth(), canvSimulation1.getHeight());
        //gc.setFill(javafx.scene.paint.Color.BLACK);

        gc.setStroke(convertToJavaFXColor(Color.BLACK));
        gc.setFill(null);
        gc.setLineWidth(1);


        for (GridPoint gp : g.getPointList()){
            gc.setFill(convertToJavaFXColor(gp.getColor()));
            gc.fillRect(gp.getX() + (zoom * gp.getX()), gp.getY() + (zoom * gp.getY()), zoom, zoom);
            if (zoom > 5) {
                gc.strokeRect(gp.getX() + (zoom * gp.getX()), gp.getY() + (zoom * gp.getY()), zoom, zoom);
            }
        }

    }

    private javafx.scene.paint.Color convertToJavaFXColor(Color c){
        return javafx.scene.paint.Color.rgb(c.getRed(), c.getGreen(), c.getBlue());
    }
}
