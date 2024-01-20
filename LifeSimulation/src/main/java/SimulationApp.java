import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.WorldMap;
import model.util.Energy;
import presenter.SimulationPresenter;

public class SimulationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        WorldMap map = new WorldMap();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        SimulationPresenter presenter = loader.getController();
        map.addObserver(presenter);
        presenter.setWorldMap(map);


        //wykres
        System.out.println("ezz");
        configureStage(primaryStage, viewRoot);
        primaryStage.show();

    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot){
        primaryStage.setScene(viewRoot.getScene());
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}