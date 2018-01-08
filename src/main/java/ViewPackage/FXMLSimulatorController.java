package ViewPackage;

import LifePackage.Simulation;
import ModelPackage.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLSimulatorController extends UIController implements Initializable, ILifeResult {

    public Canvas canvSimulation1;
    public Canvas canvSimulation2;
    public Canvas canvSimulation3;
    public Canvas canvSimulation4;

    //Right menu Elements
    public Button btnSelectSim1;
    public Button btnSelectSim2;
    public Button btnSelectSim3;
    public Button btnSelectSim4;

    //Control elements
    public Label lblSelectedSim;

    public Button btnSimPlay;
    public Button btnSimPause;
    public Button btnSimOpen;
    public Button btnSimSave;
    public Button btnSimNew;

    public Label lblZoomValue1;
    public Slider sldZoom1;

    public Label lblSimSpeed;
    public Slider sldSimSpeed;

    //Details Elements
    public Label lblExtinctionCountdown;
    public Button btnExtinctionNow;
    public Button btnExtinctionReset;
    public Button btnExtinctionDisable;

    public Label lblCreaturesTotal;
    public Label lblCreaturesCarnivores;
    public Label lblCreaturesHerbivores;
    public Label lblCreaturesNonivores;
    public Label lblCreaturesOmnivores;

    public Label lblPlants;

    public Label lblTitleExtinction;
    public Label lblTitleCreatures;
    public Label lblTitleplants;

    public Label lblStepsDone;

    //fields for scorekeeping
    private int selectedSim;

    private int[] simLastShownStep;
    private int[] simZoom;
    private double[] simSpeed;
    private StepResult[] stepResults;

    private AnimationTimer timer;

    private Thread[] simulationThreads;
    private Simulation[] simulations;

    public FXMLSimulatorController() {
        super();
        selectedSim = 1;
        simulationThreads = new Thread[4];
        simulations = new Simulation[4];

        simLastShownStep = new int[4];
        Arrays.fill(simLastShownStep, 0);
        simZoom = new int[4];
        Arrays.fill(simZoom, 7);
        simSpeed = new double[4];
        Arrays.fill(simSpeed, 1);
        stepResults = new StepResult[4];
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void updateSimulationResults(StepResult simStatus, int simNumber) {
        triggerAnimationTimer();
        stepResults[simNumber - 1] = simStatus;
    }

    private void drawGrid(IGrid g, Canvas canvas, int zoom) {

        //spSim1.setPreferredSize(new Dimension(g.getWidth() * zoom, g.getHeight() * zoom));
        canvas.setWidth(g.getWidth() * zoom);
        canvas.setHeight(g.getHeight() * zoom);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //gc.setFill(javafx.scene.paint.Color.BLACK);

        gc.setStroke(convertToJavaFXColor(Color.BLACK));
        gc.setFill(null);
        gc.setLineWidth(1);


        for (GridPoint gp : g.getPointList()) {
            gc.setFill(convertToJavaFXColor(gp.getColor()));
            //gc.fillRect(gp.getX() + (zoom * gp.getX()), gp.getY() + (zoom * gp.getY()), zoom, zoom);
            gc.fillRect((zoom * gp.getX()), (zoom * gp.getY()), zoom, zoom);
            if (zoom > 5) {
                //gc.strokeRect(gp.getX() + (zoom * gp.getX()), gp.getY() + (zoom * gp.getY()), zoom, zoom);
                gc.strokeRect((zoom * gp.getX()), (zoom * gp.getY()), zoom, zoom);
            }
        }

    }

    private void triggerAnimationTimer() {
        if (timer == null) {
            timer = new AnimationTimer() {

                @Override
                public void handle(long now) {
                    for (int i = 0; i < 4; i++) {
                        if (stepResults[i] != null) {
                            switch (i) {
                                case 0:
                                    drawGrid(stepResults[i].getCurrentGrid(), canvSimulation1, simZoom[i]);
                                    break;
                                case 1:
                                    drawGrid(stepResults[i].getCurrentGrid(), canvSimulation2, simZoom[i]);
                                    break;
                                case 2:
                                    drawGrid(stepResults[i].getCurrentGrid(), canvSimulation3, simZoom[i]);
                                    break;
                                case 3:
                                    drawGrid(stepResults[i].getCurrentGrid(), canvSimulation4, simZoom[i]);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    updateSimDetails();
                }
            };
            timer.start();
        }
    }


    private javafx.scene.paint.Color convertToJavaFXColor(Color c) {
        return javafx.scene.paint.Color.rgb(c.getRed(), c.getGreen(), c.getBlue());
    }

    private void updateSimDetails() {
        lblSelectedSim.setText("Simulation " + selectedSim);
        if (stepResults[selectedSim - 1] != null) {
            toggleVisible(true);
            lblTitleExtinction.setText("Mass Extincion countdown");
            if (stepResults[selectedSim - 1].getStepCount() > simLastShownStep[selectedSim - 1]) {
                simLastShownStep[selectedSim - 1] = stepResults[selectedSim - 1].getStepCount();
                setDetailValues(simZoom[selectedSim - 1], simSpeed[selectedSim - 1], stepResults[selectedSim - 1], simLastShownStep[selectedSim - 1]);
            }
        }
    }

    private void setDetailValues(int zoom, double speed, StepResult stepResult, int simSteps) {

        sldZoom1.setValue(speed);
        lblZoomValue1.setText(Double.toString(speed));
        sldZoom1.setValue(zoom);
        lblZoomValue1.setText(Integer.toString(zoom));

        int totalCreatureCount = stepResult.getCarnivoreCount() + stepResult.getHerbivoreCount() + stepResult.getNonivoreCount() + stepResult.getOmnivoreCount();
        int totalCreatureEnergy = stepResult.getEnergyCarnivore() + stepResult.getEnergyHerbivore() + stepResult.getEnergyNonivore() + stepResult.getEnergyOmnivore();

        lblCreaturesTotal.setText(totalCreatureCount + " Creatures Total/" + totalCreatureEnergy + " Energy");
        lblCreaturesCarnivores.setText(stepResult.getCarnivoreCount() + " Carnivores Total/" + stepResult.getEnergyCarnivore() + " Energy");
        lblCreaturesHerbivores.setText(stepResult.getHerbivoreCount() + " Herbivores Total/" + stepResult.getEnergyHerbivore() + " Energy");
        lblCreaturesNonivores.setText(stepResult.getNonivoreCount() + " Nonivores Total/" + stepResult.getEnergyNonivore() + " Energy");
        lblCreaturesOmnivores.setText(stepResult.getOmnivoreCount() + " Omnivores Total/" + stepResult.getEnergyNonivore() + " Energy");

        lblPlants.setText(stepResult.getPlantCount() + "Plants Total/" + stepResult.getEnergyPlants() + " Energy in plants");

        lblStepsDone.setText(Integer.toString(simSteps) + " Steps done");
    }

    private void toggleVisible(boolean visible) {
        lblExtinctionCountdown.setVisible(visible);
        btnExtinctionNow.setVisible(visible);
        btnExtinctionDisable.setVisible(visible);
        btnExtinctionReset.setVisible(visible);
        lblCreaturesCarnivores.setVisible(visible);
        lblCreaturesHerbivores.setVisible(visible);
        lblCreaturesNonivores.setVisible(visible);
        lblCreaturesOmnivores.setVisible(visible);
        lblCreaturesTotal.setVisible(visible);
        lblPlants.setVisible(visible);
        lblTitleCreatures.setVisible(visible);
        lblTitleplants.setVisible(visible);
        lblStepsDone.setVisible(visible);
    }

    private void changeSelectedSimulation(int simNumber) {
        selectedSim = simNumber;
        if (stepResults[selectedSim - 1] != null) {
            toggleVisible(true);
        }
        updateSimDetails();
    }

    private Grid getTestingGrid() {
        int testGridWidth = 20;
        int testGridHeight = 20;

        Grid grid = new Grid(testGridWidth, testGridHeight);

        //island 1
        grid.setPointType(new Point(2, 2), GridPointType.Ground);
        grid.setPointType(new Point(2, 3), GridPointType.Ground);
        grid.setPointType(new Point(2, 4), GridPointType.Ground);
        grid.setPointType(new Point(2, 5), GridPointType.Ground);
        grid.setPointType(new Point(3, 2), GridPointType.Ground);
        grid.setPointType(new Point(3, 3), GridPointType.Ground);
        grid.setPointType(new Point(3, 4), GridPointType.Ground);
        grid.setPointType(new Point(3, 5), GridPointType.Ground);
        grid.setPointType(new Point(4, 2), GridPointType.Ground);
        grid.setPointType(new Point(4, 3), GridPointType.Ground);
        grid.setPointType(new Point(4, 4), GridPointType.Ground);
        grid.setPointType(new Point(4, 5), GridPointType.Ground);
        grid.setPointType(new Point(5, 2), GridPointType.Ground);
        grid.setPointType(new Point(5, 3), GridPointType.Ground);
        grid.setPointType(new Point(5, 4), GridPointType.Ground);
        grid.setPointType(new Point(5, 5), GridPointType.Ground);

        //island 2
        grid.setPointType(new Point(10, 2), GridPointType.Ground);
        grid.setPointType(new Point(10, 3), GridPointType.Ground);
        grid.setPointType(new Point(10, 4), GridPointType.Ground);
        grid.setPointType(new Point(10, 5), GridPointType.Ground);
        grid.setPointType(new Point(11, 2), GridPointType.Ground);
        grid.setPointType(new Point(11, 3), GridPointType.Ground);
        grid.setPointType(new Point(11, 4), GridPointType.Ground);
        grid.setPointType(new Point(11, 5), GridPointType.Obstacle);
        grid.setPointType(new Point(12, 2), GridPointType.Obstacle);
        grid.setPointType(new Point(12, 3), GridPointType.Ground);
        grid.setPointType(new Point(12, 4), GridPointType.Ground);
        grid.setPointType(new Point(12, 5), GridPointType.Ground);
        grid.setPointType(new Point(13, 2), GridPointType.Ground);
        grid.setPointType(new Point(13, 3), GridPointType.Ground);
        grid.setPointType(new Point(13, 4), GridPointType.Ground);
        grid.setPointType(new Point(13, 5), GridPointType.Ground);

        //island 3
        grid.setPointType(new Point(10, 10), GridPointType.Ground);
        grid.setPointType(new Point(10, 11), GridPointType.Ground);
        grid.setPointType(new Point(10, 12), GridPointType.Ground);
        grid.setPointType(new Point(10, 13), GridPointType.Ground);
        grid.setPointType(new Point(11, 10), GridPointType.Ground);
        grid.setPointType(new Point(11, 11), GridPointType.Ground);
        grid.setPointType(new Point(11, 12), GridPointType.Ground);
        grid.setPointType(new Point(11, 13), GridPointType.Ground);
        grid.setPointType(new Point(12, 10), GridPointType.Ground);
        grid.setPointType(new Point(12, 11), GridPointType.Ground);
        grid.setPointType(new Point(12, 12), GridPointType.Ground);
        grid.setPointType(new Point(12, 13), GridPointType.Ground);
        grid.setPointType(new Point(13, 10), GridPointType.Ground);
        grid.setPointType(new Point(13, 11), GridPointType.Ground);
        grid.setPointType(new Point(13, 12), GridPointType.Ground);
        grid.setPointType(new Point(13, 13), GridPointType.Ground);

        return grid;
    }

    //////////////////////////////////////////////////////////////
    //                                                          //
    //                  UI EVENTS                               //
    //                                                          //
    //////////////////////////////////////////////////////////////

    public void onClickToSim1() {
        changeSelectedSimulation(1);
    }

    public void onClickToSim2() {
        changeSelectedSimulation(2);
    }

    public void onClickToSim3() {
        changeSelectedSimulation(3);
    }

    public void onClickToSim4() {
        changeSelectedSimulation(4);
    }

    public void onClickPlaySim() {
        simulations[selectedSim - 1].setSimulationSpeed(sldSimSpeed.getValue());
        simulations[selectedSim - 1].startSimulation();
    }

    public void onClickPauseSim() {
        simulations[selectedSim - 1].setSimulationSpeed(0);
    }

    public void onClickOpenSim() {
    }

    public void onClickSaveSim() {
    }

    public void onClickNewSim() {
        Simulation freshSim = new Simulation(500, 40,
                1500, Digestion.Carnivore, 100, 1500, 4, 600, 500, 900, 400, 300, 10,
                1400, Digestion.Herbivore, 100, 1400, 6, 700, 500, 750, 400, 300, 10,
                2000, Digestion.Nonivore, 0, 1750, 8, 1000, 800, 100, 300, 200, 10,
                1750, Digestion.Omnivore, 45, 2500, 2, 500, 400, 1500, 600, 300, 30, getTestingGrid(), this, selectedSim);
        simulations[selectedSim - 1] = freshSim;
        Thread simThread = new Thread(String.valueOf(selectedSim)) {
            public void run() {
                freshSim.setSimulationSpeed(sldSimSpeed.getValue());
                freshSim.startSimulation();
            }
        };
        //Thread simThread = new Thread(String.valueOf(selectedSim));
        simulationThreads[selectedSim - 1] = simThread;
        simThread.setDaemon(true);
        simThread.start();
    }

    public void onZoomSlider1Finished() {
        simZoom[selectedSim - 1] = (int) sldZoom1.getValue();
        lblZoomValue1.setText(Integer.toString(simZoom[selectedSim - 1]));
    }

    public void onSpeedSliderFinished() {
        simSpeed[selectedSim-1] = sldSimSpeed.getValue();
        if (sldSimSpeed.getValue() == 100){
            lblSimSpeed.setText("Unlimited");
        }
        else{
            lblSimSpeed.setText(Double.toString(sldSimSpeed.getValue()));
        }
        simulations[selectedSim-1].setSimulationSpeed(simSpeed[selectedSim-1]);
    }

    public void onClickExtinctionNow() {
        showWarning("Notice", "Extinction events not implemented yet.");
    }

    public void onClickExtinctionReset() {
        showWarning("Notice", "Extinction events not implemented yet.");
    }

    public void onClickExtinctionDisable() {
        showWarning("Notice", "Extinction events not implemented yet.");
    }
}
