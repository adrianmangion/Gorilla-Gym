import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.beans.value.*;
import javafx.geometry.*;
import java.awt.event.*;
import javafx.event.*;
import java.util.*;

public class GuiView extends Application{

    Network client = new Network();
    
    public static void main(String[] args){
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

	client.setSocket();
	Stage window = primaryStage;
	Scene loginScene;
	
	//Start Application
        window.setTitle("Gorilla Gym Login");

	//Grid pane for login form
	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setVgap(10);
	grid.setHgap(10);
	grid.setPadding(new Insets(25, 25, 25, 25));

	Label sceneTitle = new Label("Gorilla Gym Login");
	sceneTitle.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
	grid.add(sceneTitle, 0, 0, 2, 1);

	Label lblUser = new Label("Username:");
	grid.add(lblUser, 0, 1);

	TextField txtUser = new TextField();
	txtUser.setPromptText("Enter username");
	grid.add(txtUser, 1, 1);

	Label lblPassword = new Label("Password:");
	grid.add(lblPassword, 0, 2);

	PasswordField pwBox = new PasswordField();
	pwBox.setPromptText("Enter password");
	grid.add(pwBox, 1, 2);

	final Text actionTarget = new Text();
	grid.add(actionTarget, 1, 5);

	Button loginBtn = new Button("Login");
	grid.add(loginBtn, 1, 3);

	//EventHandler
	loginBtn.setOnAction(e -> {
		if(txtUser.getText().equals("admin") && pwBox.getText().equals("admin")){
		    mainScreen(window);
		}
		else{
		    actionTarget.setFill(Color.FIREBRICK);
		    actionTarget.setText("Credentials are incorrect");
		}});
	
	loginScene = new Scene(grid, 500, 500);
	window.setScene(loginScene);
	window.show();
    }

    /* Method for creating the main screen of the application
     * Includes booking functionality such as list and add.
     **/
    public void mainScreen(Stage window){

	List<Booking> bookingList = new ArrayList<Booking>();
	Scene startScreen;
	window.setTitle("Gorilla Gym Tool");

	try{

	    //Populate booking list
	    client.sendString("listall");
	    bookingList = client.getList();


	    //Layout for main screen
	    BorderPane bp = new BorderPane();
	    MenuBar menuBar = createMenu();
	    VBox vbox = addVBox(window, bookingList);
	    bp.setTop(menuBar);
	    bp.setLeft(vbox);

	    startScreen = new Scene(bp, 800, 600);
	    window.setScene(startScreen);
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    public MenuBar createMenu(){
	//Menu Tabs
	Menu viewMenu = new Menu("View");
	Menu helpMenu = new Menu("Help");

	//Initialising Menu Items
	MenuItem viewClients = new MenuItem("Cleints");
	MenuItem viewTrainers = new MenuItem("Personal Trainers");
	MenuItem viewSpecialisms = new MenuItem("Specialisms");
	MenuItem viewPTSpecialisms = new MenuItem("Trainer Specialisms");
	MenuItem helpBug = new MenuItem("Report a bug");
	MenuItem helpAbout = new MenuItem("About Gorilla Gym tool... ");
	
	
	//Adding Menu Items to Menu Tabs
	viewMenu.getItems().add(viewClients);
	viewMenu.getItems().add(viewTrainers);
	viewMenu.getItems().add(viewSpecialisms);
	viewMenu.getItems().add(viewPTSpecialisms);
	helpMenu.getItems().add(helpBug);
	helpMenu.getItems().add(helpAbout);

	//Events for Menu Items
	viewClients.setOnAction(e ->{
		System.out.println("hey");
	    }
	    );
	
	//Menu Bar
	MenuBar menuBar = new MenuBar();
	menuBar.getMenus().addAll(viewMenu, helpMenu);
	return menuBar;
    }

    public VBox addVBox(Stage window, List<Booking> bookingList){
	TreeView<String> tree;
	VBox vbox = new VBox();
	vbox.setPadding(new Insets(10));
	vbox.setSpacing(8);

	Text title = new Text("Bookings Filter");
	vbox.getChildren().add(title);

	//Grid Pane for buttons
	GridPane btnGrid = new GridPane();
	btnGrid.setHgap(10);
	btnGrid.setVgap(10);
	
	//Add Button
	Button btnAddBooking = new Button("Add booking");
	btnGrid.add(btnAddBooking, 0, 0);

	//Refresh Button
	Button btnRefresh = new Button("Refresh");
        btnGrid.add(btnRefresh, 1, 0);

	//Add btnGrid to vbox
	vbox.getChildren().add(btnGrid);

	//SidePanel with ScrollPane, Treeview and Buttons
	
	//Tree Items
	TreeItem<String> root, allBookings;

	//Root
	root = new TreeItem<>();
	root.setExpanded(true);

	//All Bookings Branch
	allBookings = makeBranch("List All", root);
	for(Booking booking: bookingList){
	    makeBranch("Booking "+Integer.toString(booking.getBookingID()), allBookings);
	}
	
	tree = new TreeView<>(root);
	tree.setShowRoot(false);
	
	//Scroll Pane for Tree
	ScrollPane sp = new ScrollPane();
	sp.setContent(tree);
	vbox.getChildren().add(sp);

	//Filtering
	GridPane grid = new GridPane();
	grid.setHgap(10);
	grid.setVgap(10);

	Label lblSearchClient = new Label("Search by client:");
	grid.add(lblSearchClient, 0, 0); //Col, Row
	
	TextField txtSearchClient = new TextField();
	txtSearchClient.setMaxWidth(105);
	grid.add(txtSearchClient, 1, 0);

	Button btnSearchClient = new Button("Search Client");
	btnSearchClient.setMaxWidth(105);
	grid.add(btnSearchClient, 1, 1);

	Label lblSearchPT = new Label("Search by trainer:");
	grid.add(lblSearchPT, 0, 2);
	
	TextField txtSearchPT = new TextField();
	txtSearchPT.setMaxWidth(105);
	grid.add(txtSearchPT, 1, 2);

	Button btnSearchPT = new Button("Search PT");
	btnSearchPT.setMaxWidth(105);
	grid.add(btnSearchPT, 1, 3);

	Label lblSearchDate = new Label("Search by date:");
	grid.add(lblSearchDate, 0, 4);
	
	TextField txtSearchDate = new TextField();
	txtSearchDate.setMaxWidth(105);
	grid.add(txtSearchDate, 1, 4);

	Button btnSearchDate = new Button("Search Date");
	btnSearchDate.setMaxWidth(105);
	grid.add(btnSearchDate, 1, 5);

        final Text actionTarget = new Text();
	grid.add(actionTarget, 0, 6, 2, 1);

	vbox.getChildren().add(grid);

	//Add Button EventHandler
	btnAddBooking.setOnAction(add ->{
		BorderPane bPane = new BorderPane();
		MenuBar menuBar = createMenu();
		GridPane bookingGrid = addBookingPane(window);
		bPane.setTop(menuBar);
		bPane.setLeft(vbox);
		bPane.setCenter(bookingGrid);
		Scene addBookingScene  = new Scene(bPane, 800, 600);
		window.setScene(addBookingScene);
	    });

	//Refresh Button EventHandler
	btnRefresh.setOnAction(refresh ->{
		List<Booking> updatedList = new ArrayList<Booking>();
		client.sendString("listall");
		updatedList = client.getList();
		
		BorderPane bPane = new BorderPane();
		MenuBar menuBar = createMenu();			
		VBox updatedVBox = addVBox(window, updatedList);
		bPane.setTop(menuBar);
		bPane.setLeft(updatedVBox);
		Scene addBookingScene = new Scene(bPane, 800, 600);
		window.setScene(addBookingScene);
	    });

	//Search Client EventHandler
	btnSearchClient.setOnAction(search ->{
		//Check if there is any text in field
		if(!txtSearchClient.getText().equals("")){
		    List<Booking> updatedList = new ArrayList<Booking>();
		    client.sendString("listclient");
		    System.out.println("GUI sending "+txtSearchClient.getText()+" ID");
		    client.sendString(txtSearchClient.getText());
		    updatedList = client.getList();
		    
		    BorderPane bPane = new BorderPane();
		    MenuBar menuBar = createMenu();			
		    VBox updatedVBox = addVBox(window, updatedList);
		    bPane.setTop(menuBar);
		    bPane.setLeft(updatedVBox);
		    Scene addBookingScene = new Scene(bPane, 800, 600);
		    window.setScene(addBookingScene);
		}
		else{
		    actionTarget.setFill(Color.FIREBRICK);
		    actionTarget.setText("Kindly enter client ID");
		}
	    });

	//Search Trainer EventHandler
	btnSearchPT.setOnAction(search ->{
		//Check if there is any text in field
		if(!txtSearchPT.getText().equals("")){
		    List<Booking> updatedList = new ArrayList<Booking>();
		    client.sendString("listtrainer");
		    System.out.println("GUI sending "+txtSearchPT.getText()+" ID");
		    client.sendString(txtSearchPT.getText());
		    updatedList = client.getList();
		    
		    BorderPane bPane = new BorderPane();
		    MenuBar menuBar = createMenu();			
		    VBox updatedVBox = addVBox(window, updatedList);
		    bPane.setTop(menuBar);
		    bPane.setLeft(updatedVBox);
		    Scene addBookingScene = new Scene(bPane, 800, 600);
		    window.setScene(addBookingScene);
		}
		else{
		    actionTarget.setFill(Color.FIREBRICK);
		    actionTarget.setText("Kindly enter PT ID");
		}
	    });

	//Search Date EventHandler
	btnSearchDate.setOnAction(search ->{
		//Check if there is any text in field
		if(!txtSearchDate.getText().equals("")){
		    List<Booking> updatedList = new ArrayList<Booking>();
		    client.sendString("listdate");
		    System.out.println("GUI sending "+txtSearchDate.getText()+" as date");
		    client.sendString(txtSearchDate.getText());
		    updatedList = client.getList();
		    
		    BorderPane bPane = new BorderPane();
		    MenuBar menuBar = createMenu();			
		    VBox updatedVBox = addVBox(window, updatedList);
		    bPane.setTop(menuBar);
		    bPane.setLeft(updatedVBox);
		    Scene addBookingScene = new Scene(bPane, 800, 600);
		    window.setScene(addBookingScene);
		}
		else{
		    actionTarget.setFill(Color.FIREBRICK);
		    actionTarget.setText("Kindly enter Date (dd-mm-yyyy)");
		}
	    });

	//Tree View EventHandler
	tree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) ->{
		String currentBookingID = newValue.getValue();
		currentBookingID = currentBookingID.toLowerCase();
		if(currentBookingID.contains("booking")){
		    //Remove whitespace
		    currentBookingID = currentBookingID.replaceAll("\\s", "");
		    //Check BookingID
		    currentBookingID = currentBookingID.substring(7);

		    for(Booking booking: bookingList){
			if(Integer.parseInt(currentBookingID) == booking.getBookingID())
			    {
				//Layout for Scene 3
				BorderPane bPane = new BorderPane();
				MenuBar menuBar = createMenu();
				GridPane bookingGrid = addBookingPane(booking);
				bPane.setTop(menuBar);
				bPane.setLeft(vbox);
				bPane.setCenter(bookingGrid);
				Scene toolScene  = new Scene(bPane, 800, 600);
				window.setScene(toolScene);
			    }
		    }
		}
	    });
	return vbox;
    }

    public TreeItem<String> makeBranch(String title, TreeItem<String> parent){
	TreeItem<String> branch = new TreeItem<>(title);
	branch.setExpanded(true);
	parent.getChildren().add(branch);
	return branch;
    }

    public GridPane addBookingPane(Booking booking){
	
        GridPane grid = new GridPane();
	grid.setAlignment(Pos.TOP_LEFT);
	grid.setVgap(10);
	grid.setHgap(10);
	grid.setPadding(new Insets(25, 25, 25, 25));

	Label title = new Label("Booking " + booking.getBookingID());
	title.setFont(Font.font("Tahoma", FontWeight.LIGHT, 20));
	grid.add(title, 0, 0, 2, 1); //Col, Row

	Label lblPTID = new Label("Personal Trainer ID: ");
	grid.add(lblPTID, 0, 1);

	TextField txtPTID = new TextField();
	txtPTID.setText(Integer.toString(booking.getPTID()));
	grid.add(txtPTID, 1, 1);

	Label lblSpecialisms = new Label("Focus: ");
	grid.add(lblSpecialisms, 0, 2);

	ChoiceBox<String> specialisms = new ChoiceBox<>();
	specialisms.setMinWidth(170);
	specialisms.getItems().add(booking.getSpecialism());
	specialisms.setValue(booking.getSpecialism());
	grid.add(specialisms, 1, 2);

	Label lblClientID = new Label("Client ID: ");
	grid.add(lblClientID, 0, 3);

	TextField txtClientID = new TextField();
	txtClientID.setText(Integer.toString(booking.getClientID()));
	grid.add(txtClientID, 1, 3);

	Label lblDate = new Label("Date of Booking: ");
	grid.add(lblDate, 0, 4);

	TextField txtDate = new TextField();
	txtDate.setText(booking.getDate());
	grid.add(txtDate, 1, 4);

	Label lblStart = new Label("Start Time: ");
	grid.add(lblStart, 0, 5);

	TextField txtStart = new TextField();
	txtStart.setText(booking.getStartTime());
	grid.add(txtStart, 1, 5);

	Label lblEnd = new Label("End Time: ");
	grid.add(lblEnd, 0, 6);

	TextField txtEnd = new TextField();
	txtEnd.setText(booking.getEndTime());
	grid.add(txtEnd, 1, 6);

	Button btnUpdate = new Button("Update");
	grid.add(btnUpdate, 0, 7);

	Button btnDelete = new Button("Delete");
	grid.add(btnDelete, 1, 7);

	final Text actionTarget = new Text();
	grid.add(actionTarget, 0, 8, 2, 1);

	GridPane.setHalignment(btnUpdate, HPos.RIGHT);

	//Update Button EventHandler
	btnUpdate.setOnAction(update -> {
		int ptID, clientID, effectedRows;
		String start, end, date, specialism;
		date  = txtDate.getText();
		end   = txtEnd.getText();
		start = txtStart.getText();
	        ptID  = Integer.parseInt(txtPTID.getText());
		clientID = Integer.parseInt(txtClientID.getText());
		specialism = specialisms.getSelectionModel().getSelectedItem().toString();

		client.sendString("update");
		Booking updatedBooking = new Booking(booking.getBookingID(), ptID, specialism, clientID, date, start, end);
		client.sendBooking(updatedBooking);
		effectedRows = client.getMessage();

		switch(effectedRows){
		case 0:
		    actionTarget.setFill(Color.FIREBRICK);
		    actionTarget.setText("Failed to update booking " + booking.getBookingID() + "\nKindly refresh list.");
		    break;

		case 1:
		    actionTarget.setFill(Color.GREEN);
		    actionTarget.setText("Successfully updated booking " + booking.getBookingID() + "\nKindly refresh list.");
		    break;
		}
	    });

	//Delete Button EventHandler
	btnDelete.setOnAction(event -> {
		int effectedRows;
		
		client.sendString("delete");
		client.sendBooking(booking);
		effectedRows = client.getMessage();

		switch(effectedRows){
		case 0:
		    actionTarget.setFill(Color.FIREBRICK);
		    actionTarget.setText("Failed to delete booking " + booking.getBookingID() + "\nKindly refresh list.");
		    break;

		case 1:
		    actionTarget.setFill(Color.GREEN);
		    actionTarget.setText("Successfully deleted booking " + booking.getBookingID() + "\nKindly refresh list.");
		    break;
		}
	    });

	//Personal Trainer ID Text Change Event Handler
	txtPTID.textProperty().addListener(new ChangeListener<String>(){
		@Override
		public void changed(ObservableValue<? extends String> observable,
				    String oldValue, String newValue){
		    
		    List<TrainerSpecialism> ptSpecialisms = new ArrayList<TrainerSpecialism>();
		    client.sendString("listtrainerspecialisms");
		    ptSpecialisms = client.getPTSpecialismList();

		    specialisms.getItems().clear();

		    for(TrainerSpecialism ptSpecialism: ptSpecialisms){
			if(newValue.equals(Integer.toString(ptSpecialism.getID()))){
			    specialisms.getItems().add(ptSpecialism.getName());
			}
		    }
		}
	    });
	
	return grid;
    }
    public GridPane addBookingPane(Stage window){
	
        GridPane grid = new GridPane();
	grid.setAlignment(Pos.TOP_LEFT);
	grid.setVgap(10);
	grid.setHgap(10);
	grid.setPadding(new Insets(25, 25, 25, 25));

	//Display nodes
	Label title = new Label("New Booking");
	title.setFont(Font.font("Tahoma", FontWeight.LIGHT, 20));
	grid.add(title, 0, 0, 2, 1);

	Label lblPTID = new Label("Personal Trainer ID: ");
	grid.add(lblPTID, 0, 1); //Col, Row

	TextField txtPTID = new TextField();
	grid.add(txtPTID, 1, 1);

	Label lblSpecialisms = new Label("Focus: ");
	grid.add(lblSpecialisms, 0, 2);

	ChoiceBox<String> specialisms = new ChoiceBox<>();
	specialisms.setMinWidth(170);
	grid.add(specialisms, 1, 2);

	Label lblClientID = new Label("Client ID: ");
	grid.add(lblClientID, 0, 3);

	TextField txtClientID = new TextField();
	grid.add(txtClientID, 1, 3);

	Label lblDate = new Label("Date of Booking: ");
	grid.add(lblDate, 0, 4);

	TextField txtDate = new TextField();
	grid.add(txtDate, 1, 4);

	Label lblStart = new Label("Start Time: ");
	grid.add(lblStart, 0, 5);

	ChoiceBox<String> cbStartTime = new ChoiceBox<>();
	for(int i = 8; i < 20; i++){
	    cbStartTime.getItems().add(String.format("%02d",i)+":00");
	}
	grid.add(cbStartTime, 1, 5);

	Label lblEnd = new Label("End Time: ");
	grid.add(lblEnd, 0, 6);

	ChoiceBox<String> cbEndTime = new ChoiceBox<>();
	cbEndTime.setMinWidth(78);
	grid.add(cbEndTime, 1, 6);

	Button btnAdd = new Button("Add");
	grid.add(btnAdd, 0, 8, 2, 1);

	final Text actionTarget = new Text();
	grid.add(actionTarget, 0, 9, 2, 1);

	//Add Button EventHandler
	btnAdd.setOnAction(event ->{
		int ptID, clientID, effectedRows;
		String start, end, date, specialism;
		Validator validator = new Validator();
		
		date  = txtDate.getText();
		end   = cbEndTime.getValue();
		start = cbStartTime.getValue();
	        ptID  = Integer.parseInt(txtPTID.getText());
		clientID = Integer.parseInt(txtClientID.getText());
		specialism = specialisms.getSelectionModel().getSelectedItem().toString();

		// Validation.
		
		// Check if date format is correct
	        if(validator.checkDateFormat(date)){
		    // Check if date is not in the past
		    if(validator.validateDate(date)){
			Booking booking = new Booking(ptID, specialism, clientID, date, start, end);
			if(!validator.checkDoubleBooking(booking)){
			    client.sendString("add");
			    client.sendBooking(booking);
			    effectedRows = client.getMessage();

			    actionTarget.setFill(Color.GREEN);
			    actionTarget.setText(effectedRows + " booking added. Please refresh.");
			}
			else{System.out.println("Double booking bro");}
		    }
		}
		else{
		    System.out.println("Bad date");
		}
	    });

	//Personal Trainer ID Text Change Event Handler
	txtPTID.textProperty().addListener(new ChangeListener<String>(){
		@Override
		public void changed(ObservableValue<? extends String> observable,
				    String oldValue, String newValue){
		    
		    List<TrainerSpecialism> ptSpecialisms = new ArrayList<TrainerSpecialism>();
		    client.sendString("listtrainerspecialisms");
		    ptSpecialisms = client.getPTSpecialismList();

		    specialisms.getItems().clear();

		    for(TrainerSpecialism ptSpecialism: ptSpecialisms){
			if(newValue.equals(Integer.toString(ptSpecialism.getID()))){
			    specialisms.getItems().add(ptSpecialism.getName());
			}
		    }
		}
	    });

	//End Time Text Change Event Handler
	cbStartTime.setOnAction(event -> {
		if(cbStartTime.getValue() != null){
		    int startTime = Integer.parseInt(cbStartTime.getValue().substring(0 ,2));
		    int endTime = startTime + 1;
		    String eTime = Integer.toString(endTime) + ":00";
		    cbEndTime.getItems().add(eTime);
		}
	    });

	GridPane.setHalignment(btnAdd, HPos.CENTER);
	
	return grid;
    }
}


