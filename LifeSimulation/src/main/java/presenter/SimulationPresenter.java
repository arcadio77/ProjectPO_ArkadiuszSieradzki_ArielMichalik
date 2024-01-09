package presenter;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.WorldMap;
import model.interfaces.WorldElement;


public class SimulationPresenter {
    private WorldMap map;
    public Button startBtn;
    public TextField getParameters;
    public Label animalMoves;
    public GridPane gridMap;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    private void drawMap(){
        clearGrid();
        createGrid(map.getWidth(), map.getHeight());
        putElements(map.getHeight());
    }

    private void putElements(int height) {
        for(WorldElement element: map.getElements()){
            int newX = element.position().x() + 1;
            int newY = height - ( element.position().y()); //because I input values inot column from biggest to smallest
            Label elem = new Label(element.toString());
            gridMap.add(elem, newX, newY);
            GridPane.setHalignment(elem, HPos.CENTER);
        }
    }

    private void clearGrid() {
        gridMap.getChildren().retainAll(gridMap.getChildren().get(0)); // hack to retain visible grid lines
        gridMap.getColumnConstraints().clear();
        gridMap.getRowConstraints().clear();
    }

    private void createGrid(int width, int height) {
        Label separator = new Label("y\\x");
        gridMap.add(separator, 0, 0);
        GridPane.setHalignment(separator, HPos.CENTER);
        gridMap.getColumnConstraints().add(new ColumnConstraints(30));
        gridMap.getRowConstraints().add(new RowConstraints(30));

        for(int i=0; i < width; i++){
            gridMap.getColumnConstraints().add(new ColumnConstraints(30));
            Label digit = new Label(String.valueOf(i));
            gridMap.add(digit, i+1, 0); //row
            GridPane.setHalignment(digit, HPos.CENTER);
        }
        for(int i=0; i < height; i++){
            gridMap.getRowConstraints().add(new RowConstraints(30));
            Label digit = new Label(String.valueOf(height-i-1));
            gridMap.add(digit, 0, i+1);
            GridPane.setHalignment(digit, HPos.CENTER);
        }


    }

    //TODO repair that part someway
//    @Override
//    public void mapChanged(WorldMap worldMap, String message){
//        Platform.runLater(() ->{
//            this.drawMap();
//            this.animalMoves.setText(message);
//        });
//
//    }
//
//    public void onSimulationStartClicked() throws IllegalArgumentException{
//        List<MoveDirection> moves = OptionsParser.parseDirections(getParameters.getText().split(" "));
//        List<Vector2d> animalPositions = new ArrayList<>(List.of(new Vector2d(2, 2), new Vector2d(3, 5)));
//        Simulation simulation = new Simulation(animalPositions, moves, map);
//        SimulationEngine engine = new SimulationEngine(new ArrayList<>( List.of(simulation)), 6);
//        engine.runAsync();
//    }
}
