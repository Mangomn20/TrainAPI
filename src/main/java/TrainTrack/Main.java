package TrainTrack;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private VBox resultsBox;
    private Label messageLabel;

    private String selectedStation = "Maynooth";

    private static final String STATION_CODE_REGEX = "^[A-Z]{3,8}$";

    @Override
    public void start(Stage stage) {

        stage.setTitle("TrainTrack");

        // ---------------- HEADER ----------------

        Label title = new Label("TrainTrack");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        HBox header = new HBox(title);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15));

        // ---------------- STATION LIST ----------------

        Label stationsTitle = new Label("Stations");
        stationsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ListView<String> stationList = new ListView<>();
        stationList.setPrefHeight(250);

        // ---------------- SIDEBAR BUTTONS ----------------

        Label filtersTitle = new Label("Station categories");
        filtersTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button allButton = new Button("A - All trains");
        Button mainlineButton = new Button("M - Mainline");
        Button suburbanButton = new Button("S - Suburban");
        Button dartButton = new Button("D - DART");

        allButton.setMaxWidth(Double.MAX_VALUE);
        mainlineButton.setMaxWidth(Double.MAX_VALUE);
        suburbanButton.setMaxWidth(Double.MAX_VALUE);
        dartButton.setMaxWidth(Double.MAX_VALUE);

        // ---------------- SIDEBAR ----------------

        VBox sidebar = new VBox(
            10,
            stationsTitle,
            stationList,
            filtersTitle,
            allButton,
            mainlineButton,
            suburbanButton,
            dartButton
        );

        sidebar.setPadding(new Insets(15));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #f0f0f0;");

        // ---------------- SEARCH FORM ----------------

        Label subtitle = new Label("Irish Rail · Live station data");

        Label stationLabel = new Label("Station");
        TextField stationInput = new TextField("Maynooth");

        Label minutesLabel = new Label("Time window (minutes)");
        TextField minutesInput = new TextField("90");

        Button searchButton = new Button("Search trains");

        // Category checkboxes
        CheckBox mainlineCheck = new CheckBox("Mainline");
        CheckBox suburbanCheck = new CheckBox("Suburban");
        CheckBox dartCheck = new CheckBox("DART");

        HBox searchControls = new HBox(
            12,
            searchButton,
            mainlineCheck,
            suburbanCheck,
            dartCheck
        );

        searchControls.setAlignment(Pos.CENTER_LEFT);

        // Make checkboxes mutually exclusive
        mainlineCheck.setOnAction(event -> {
            if (mainlineCheck.isSelected()) {
                suburbanCheck.setSelected(false);
                dartCheck.setSelected(false);
            }
        });

        suburbanCheck.setOnAction(event -> {
            if (suburbanCheck.isSelected()) {
                mainlineCheck.setSelected(false);
                dartCheck.setSelected(false);
            }
        });

        dartCheck.setOnAction(event -> {
            if (dartCheck.isSelected()) {
                mainlineCheck.setSelected(false);
                suburbanCheck.setSelected(false);
            }
        });

        // ---------------- RESULTS ----------------

        Label upcomingTitle = new Label("Upcoming trains");
        upcomingTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        messageLabel = new Label("Select a station category.");

        resultsBox = new VBox(10);

        ScrollPane resultsScroll = new ScrollPane(resultsBox);
        resultsScroll.setFitToWidth(true);
        resultsScroll.setPrefHeight(300);

        // ---------------- MAIN CONTENT ----------------

        VBox content = new VBox(
            12,
            subtitle,
            stationLabel,
            stationInput,
            minutesLabel,
            minutesInput,
            searchControls,
            upcomingTitle,
            messageLabel,
            resultsScroll
        );

        content.setPadding(new Insets(25));

        // ====================================================
        // EVENT HANDLERS
        // ====================================================

        // ---------------- SIDEBAR BUTTONS ----------------

        allButton.setOnAction(event -> loadStations("A", stationList));
        mainlineButton.setOnAction(event -> loadStations("M", stationList));
        suburbanButton.setOnAction(event -> loadStations("S", stationList));
        dartButton.setOnAction(event -> loadStations("D", stationList));

        // ---------------- STATION SELECTION ----------------

        stationList.setOnMouseClicked(event -> {

            String selected = stationList
                .getSelectionModel()
                .getSelectedItem();

            if (selected != null) {
                stationInput.setText(selected);
            }
        });

        // ---------------- MAIN SEARCH ----------------

        searchButton.setOnAction(event -> {

            String station = stationInput.getText().trim();
            String minutesText = minutesInput.getText().trim();

            if (station.isEmpty()) {
                messageLabel.setText("Enter a station name or code.");
                return;
            }

            int minutes;

            try {
                minutes = Integer.parseInt(minutesText);
            } catch (NumberFormatException e) {
                messageLabel.setText("Minutes must be a whole number.");
                return;
            }

            if (minutes < 5 || minutes > 90) {
                messageLabel.setText(
                    "Time window must be between 5 and 90 minutes."
                );
                return;
            }

            selectedStation = station;

            // Station codes bypass category selection.
            if (station.matches(STATION_CODE_REGEX)) {
                loadTrainsByCode(station, minutes);
                return;
            }

            // Determine selected station category.
            String type = null;

            if (mainlineCheck.isSelected()) {
                type = "M";
            } else if (suburbanCheck.isSelected()) {
                type = "S";
            } else if (dartCheck.isSelected()) {
                type = "D";
            }

            if (type == null) {
                messageLabel.setText(
                    "Select a station category to search by name."
                );
                return;
            }

            loadTrainsByName(station, minutes, type);
        });

        // ---------------- MAIN LAYOUT ----------------

        BorderPane layout = new BorderPane();

        layout.setTop(header);
        layout.setLeft(sidebar);
        layout.setCenter(content);

        Scene scene = new Scene(layout, 850, 600);

        stage.setScene(scene);
        stage.show();
    }

    // ========================================================
    // FUNCTION 1: loadStations()
    // ========================================================

    private void loadStations(String type, ListView<String> stationList) {

        try {
            stationList.getItems().clear();

            messageLabel.setText("Loading stations...");

            ArrayList<Station> stations = TrainAPI.getStations(type);

            for (Station station : stations) {
                stationList.getItems().add(station.getName());
            }

            messageLabel.setText(stations.size() + " stations found.");

        } catch (Exception e) {
            messageLabel.setText("Error loading stations.");
        }
    }

    // ========================================================
    // FUNCTION 2: loadTrainsByName()
    // ========================================================

    private void loadTrainsByName(String stationName, int minutes, String type) {

        try {
            messageLabel.setText("Finding station...");
            resultsBox.getChildren().clear();

            // Find the Station object using its name and category.
            ArrayList<Station> stations = TrainAPI.getStations(type);
            
//            for (Station station : stations) {
//                if(station.getName().equals(stationName))
//                {
//                    loadTrainsByCode(station.getCode(), minutes);
//                }
//            }

System.out.println("Searching for: " + stationName);

for (Station station : stations) {

    System.out.println(
        "Comparing: [" + station.getName() + "]"
    );

    if (station.getName().trim().equalsIgnoreCase(stationName.trim())) {

        System.out.println("MATCH FOUND: " + station.getCode());

        loadTrainsByCode(station.getCode(), minutes);
        return;
    }
}

System.out.println("NO MATCH FOUND");
messageLabel.setText("Station not found.");
//            Station station = TrainAPI.findStation(stationName, type);
//
//            if (station == null) {
//                messageLabel.setText("Station not found in selected category.");
//                return;
//            }
//
//            // Retrieve trains using the station's code.
//            loadTrainsByCode(station.getCode(), minutes);

        } catch (Exception e) {
            messageLabel.setText("Error finding station.");
        }
    }

    // ========================================================
    // FUNCTION 3: loadTrainsByCode()
    // ========================================================

    private void loadTrainsByCode(String stationCode, int minutes) {

        try {
            messageLabel.setText("Loading trains...");
            resultsBox.getChildren().clear();

            ArrayList<Train> trains =
                TrainAPI.getReqStationCode(stationCode, minutes);

            displayTrains(trains);

        } catch (Exception e) {
            messageLabel.setText("Error retrieving trains.");
        }
    }

    // ========================================================
    // FUNCTION 4: displayTrains()
    // ========================================================

    private void displayTrains(ArrayList<Train> trains) {

        resultsBox.getChildren().clear();

        if (trains.isEmpty()) {
            messageLabel.setText("No trains found.");
            return;
        }

        messageLabel.setText(trains.size() + " train(s) found.");

        for (Train train : trains) {

            String details =
                train.gettraincode()
                + "\nFrom: " + train.getorigin()
                + "\nTo: " + train.getdestination()
                + "\nStatus: " + train.getstatus()
                + "\nExpected arrival: " + train.getexparrival()
                + "\nExpected departure: " + train.getexpdepart();

            Label trainLabel = new Label(details);

            trainLabel.setWrapText(true);
            trainLabel.setMaxWidth(Double.MAX_VALUE);
            trainLabel.setPadding(new Insets(12));

            trainLabel.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: #cccccc;"
                + "-fx-border-radius: 4;"
            );

            resultsBox.getChildren().add(trainLabel);
        }
    }

    // ========================================================
    // FUNCTION 5: main()
    // ========================================================

    public static void main(String[] args) {
        launch(args);
    }
}