package application;
	
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import javax.imageio.ImageIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.Arrays;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Main extends Application {

	
	static Long startTime = (long) 0.00;
	static Long intermediateTime = (long) 0.00;
	static Long finishTime = (long) 0.00;
	
	public static BufferedImage picture = null;
	public static BufferedImage newPicture = null;
	public static BufferedImage intermediarPicture = null;
	

	
	@Override
	public void start(Stage primaryStage) {
		System.out.println(System.getProperty("java.runtime.version"));
		addFirstScreen(primaryStage);
	}
	
	private void addFirstScreen(Stage primaryStage){
		Label userLabel = getUserLabel();
		Label passwordLabel = getPasswordLabel();
		ImageView dog = new ImageView();
		try {
			dog = getFirstPageImageView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextField userTextField = getTextField();
		PasswordField passwordField = getPasswordField();
		Button submitButton = getSubmitButton(primaryStage, userTextField, passwordField);
		ScrollPane scrollPane = getScrollPane();
		ScrollBar scrollBar = getScrollBar(dog);
		TableView<User> table = getTableView();
		
		Pane root1 = new Pane();
		root1.setPadding(new Insets(10));
		root1.getChildren().addAll(dog, userLabel, userTextField, table, passwordField, passwordLabel, submitButton, scrollPane, scrollBar);
		
		Scene scene1 = new Scene(root1, 1080, 800);
		primaryStage.setTitle("Prewitt Operator");
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	
	private void addSecondScreen(Stage primaryStage){
		// Declaring the space where the image will be put
		ImageView imagePanel = getImagePanel();
		
		// Load image progress bar
		ProgressBar loadingProgressBar = getLoadingProgressBar();
		
		// Progress Indicator
		ProgressIndicator loadingProgresIndicator = getProgressIndicator();
		
		// List View
		ListView<String> list = getListView();
		
		// Radio Buttons
		ToggleGroup conversionModeToggleGroup = getRadioToggle();
		RadioButton greyscaleRadioButton = getGreyscaleRadioButton(conversionModeToggleGroup);
		RadioButton prewittRadioButton = getPrewittRadioButton(conversionModeToggleGroup);
		
		// Separator
		Separator separator = getSeparator();
		
		// Label
		Label imageName = getimageNameLabel();
		
		// ComboBox
		ComboBox<String> comboBox = getComboBox(imagePanel, imageName, loadingProgressBar, loadingProgresIndicator);
		
		// Slider
		Slider transformSlider = getSlider(imagePanel);
		
		// Toggle Buttons
		ToggleGroup buttonToggleGroup = getButtonToggle(imagePanel,transformSlider, list);
		ToggleButton Button50 = get50Toggle(buttonToggleGroup);
		ToggleButton Button100 = get100Toggle(buttonToggleGroup);
		
		updateSlider(transformSlider, Button50, Button100, list);
		
		// Load image
		Button loadButton = getLoadButton(primaryStage, imagePanel, loadingProgressBar, loadingProgresIndicator, imageName);
		
		Button convertButton = getConvertButton(imagePanel, greyscaleRadioButton, prewittRadioButton, list);
	
		
		// ChoiceBox
		ChoiceBox<String> showOrHide = showOrHide(imagePanel, imageName);
		
		// HyperLink
		Hyperlink link = getLink();
		
		// CheckBox 
		CheckBox timeCheckbox = getCheckBox(list);
		
		// Tree View
		TreeView<String> tree = getTreeView();
		
		// Tree Table View
		TreeTableView<Contur> treeTableView = getTreeTableView();
		
		Pane root = new Pane();
		root.setPadding(new Insets(10));
		root.getChildren().addAll(imagePanel, loadButton, loadingProgressBar, transformSlider, 
				greyscaleRadioButton, prewittRadioButton, showOrHide, Button50, Button100,
				imageName, loadingProgresIndicator, timeCheckbox, comboBox, separator,
				link, list, tree, treeTableView, convertButton);
		
		Scene scene = new Scene(root, 1080, 800);
		primaryStage.setTitle("Prewitt Operator");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Label getUserLabel(){
		Label user = new Label("User: ");
		user.setFont(new Font("Courier", 22));
		user.setPrefSize(400, 20);
		user.setLayoutX(20);
		user.setLayoutY(160);
		return user;
	}
	
	private ImageView getFirstPageImageView() throws IOException{
		ImageView imagePanel = new ImageView();
		imagePanel.setLayoutX(0);
		imagePanel.setLayoutY(270);
		File inputFile = new File(".\\src\\running_dog.png");
		BufferedImage dog = ImageIO.read(inputFile);
		imagePanel.setImage(SwingFXUtils.toFXImage(dog, null));
		return imagePanel;
	}
	
	private Label getPasswordLabel(){
		Label password = new Label("Password: ");
		password.setFont(new Font("Courier", 22));
		password.setPrefSize(400, 20);
		password.setLayoutX(20);
		password.setLayoutY(210);
		
		return password;
	}
	
	private PasswordField getPasswordField(){
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Your password");
		passwordField.setPrefSize(355, 20);
		passwordField.setLayoutX(120);
		passwordField.setLayoutY(210);
		return passwordField;
	}
	
	private ScrollPane getScrollPane(){
		Image roses = new Image(getClass().getResourceAsStream("/preview.png"));
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setPrefSize(500, 250);
		scrollPane.setLayoutX(50);
		scrollPane.setLayoutY(400);
		scrollPane.setContent(new ImageView(roses));
		return scrollPane;
	}
	
	private ScrollBar getScrollBar(ImageView runningDog){
		ScrollBar sc = new ScrollBar();
		sc.setLayoutX(700);
		sc.setLayoutY(650);
        sc.setMin(0);
        sc.setOrientation(Orientation.HORIZONTAL);
        sc.setPrefHeight(5);
        sc.setPrefWidth(300);
        sc.setMax(360);
        
        // add action
        sc.valueProperty().addListener((ObservableValue<? extends Number> ov, 
                Number old_val, Number new_val) -> {
                	runningDog.setLayoutX(new_val.doubleValue()+130);
            });  
        return sc;
	}
	
	private Button getConvertButton(ImageView imagePanel, RadioButton greyscaleRadioButton, RadioButton prewittRadioButton, ListView<String> list){
		Button convertButton = new Button();
		convertButton.setText("CONVERT");
		convertButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(greyscaleRadioButton.isSelected()){
							newPicture = convert_grayscale(picture);
							imagePanel.setImage(SwingFXUtils.toFXImage(newPicture, null));
						} else {
							newPicture = convert_prewitt(picture);
							imagePanel.setImage(SwingFXUtils.toFXImage(newPicture, null));
						}
						ObservableList<String> items = FXCollections.observableArrayList("Start Time: 0", "Intermediate Time: " + intermediateTime.toString(), "Finish Time: " + finishTime.toString());
						list.setItems(items);
					}
		});
		convertButton.setLayoutX(640);
		convertButton.setLayoutY(110);
		return convertButton;
	}
	
	private TextField getTextField(){
		TextField textField = new TextField();
		textField.setPrefSize(400, 20);
		textField.setLayoutX(75);
		textField.setLayoutY(160);
		textField.setPromptText("Your user");
		return textField;
	}
	
	private Button getSubmitButton(Stage primaryStage, TextField user, PasswordField password){
		Button submit = new Button();
		submit.setText("Submit");
		submit.setPrefSize(400, 25);
		submit.setLayoutX(75);
		submit.setLayoutY(260);
		submit.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(user.getText().equals("admin") && password.getText().equals("admin") ||
								user.getText().equals("georgiana") && password.getText().equals("georgiana")){
							addSecondScreen(primaryStage);
						}
					}
		});
		return submit;
	}
	
	private ImageView getImagePanel(){
		ImageView imagePanel = new ImageView();
		imagePanel.setLayoutX(10);
		imagePanel.setLayoutY(10);
		return imagePanel;
	}
	
	@SuppressWarnings("unchecked")
	private TreeTableView<Contur> getTreeTableView(){
		List<Contur> parts = Arrays.<Contur>asList(
		        new Contur("Object Detection", "It enables tasks like object tracking, counting instances of objects, and scene understanding."),
		        new Contur("Face Detection", "Face detection algorithms can determine the presence, position, and orientation of faces in an image or video frame."),
		        new Contur("Text Detection", "Text detection algorithms can accurately identify and extract text, enabling further processing and analysis."));

		final TreeItem<Contur> root = 
		        new TreeItem<>(new Contur("Detection Applications", ""));
		
		root.setExpanded(true);
        parts.stream().forEach((part) -> {
            root.getChildren().add(new TreeItem<>(part));
        });

        TreeTableColumn<Contur, String> empColumn = 
            new TreeTableColumn<>("Con");
        empColumn.setPrefWidth(180);
        empColumn.setCellValueFactory(
            (TreeTableColumn.CellDataFeatures<Contur, String> param) -> 
            new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );

        TreeTableColumn<Contur, String> descriptionColumn = 
            new TreeTableColumn<>("Description");
        descriptionColumn.setPrefWidth(548);
        descriptionColumn.setCellValueFactory(
            (TreeTableColumn.CellDataFeatures<Contur, String> param) -> 
            new ReadOnlyStringWrapper(param.getValue().getValue().getDepartment())
        );

        TreeTableView<Contur> treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().setAll(empColumn, descriptionColumn);
        
        treeTableView.setLayoutX(30);
        treeTableView.setLayoutY(470);
        treeTableView.setPrefSize(570, 260);
        
        return treeTableView;
	}
	
	private TreeView<String> getTreeView(){
		 List<Contur> contours = Arrays.<Contur>asList(
		            new Contur("Object Detection", "Detection Applications"),
		            new Contur("Shape Analysis", "Analysis Applications"),           
		            new Contur("Gesture Recognition", "Analysis Applications"),
		            new Contur("Face Detection", "Detection Applications"),
		            new Contur("Text Detection", "Detection Applications"),
		            new Contur("Motion Tracking", "Analysis Applications"));
	    TreeItem<String> rootNode = new TreeItem<>("Contour Recognition Utilization");
	    
	    rootNode.setExpanded(true);
        for (Contur contur : contours) {
            TreeItem<String> empLeaf = new TreeItem<>(contur.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(contur.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> depNode = new TreeItem<>(contur.getDepartment());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }
        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setPrefSize(450, 260);
        treeView.setLayoutX(624);
        treeView.setLayoutY(470);
		return treeView;
	}
	
	@SuppressWarnings("unchecked")
	private TableView<User> getTableView(){
		final ObservableList<User> data =
	            FXCollections.observableArrayList(
	            new User("admin", "admin"),
	            new User("georgiana", "georgiana"));
		
		TableView<User> table = new TableView<User>();
		@SuppressWarnings("rawtypes")
		TableColumn userCol = new TableColumn("Username");
		userCol.setCellValueFactory(
	                new PropertyValueFactory<>("username"));
        @SuppressWarnings("rawtypes")
		TableColumn passCol = new TableColumn("Password");
        passCol.setCellValueFactory(
                new PropertyValueFactory<>("password"));
        table.getColumns().addAll(userCol, passCol);
        
        table.setItems(data);
        
        table.setLayoutX(550);
        table.setLayoutY(170);
        table.setPrefSize(175, 90);
        return table;
	}
	
	private ListView<String> getListView(){
		ListView<String> list = new ListView<>();
		ObservableList<String> items = FXCollections.observableArrayList("Start Time: 0", "Intermediate Time: " + intermediateTime.toString(), "Finish Time: " + finishTime.toString());
		list.setItems(items);
		list.setPrefWidth(200);
		list.setPrefHeight(100);
		list.setLayoutX(820);
		list.setLayoutY(320);
		return list;
	}
	
	private Hyperlink getLink(){
		Hyperlink link = new Hyperlink();
		link.setText("https://upb.ro/");
		link.setLayoutX(20);
		link.setLayoutY(750);
		return link;
	}
	
	private Label getimageNameLabel(){
		Label imageName = new Label();
		imageName.setVisible(false);
		return imageName;
	}
	
	private CheckBox getCheckBox(ListView<String> list){
		CheckBox checkbox = new CheckBox();
		checkbox.setText("Hide conversion time");
		checkbox.setLayoutX(640);
		checkbox.setLayoutY(320);
		checkbox.setSelected(false);
		
		// add action
		checkbox.selectedProperty().addListener(
		        (ObservableValue<? extends Boolean> ov,
		            Boolean old_val, Boolean new_val) -> {
		                if(new_val){
		                	list.setVisible(false);
		                } else {
		                	list.setVisible(true);
		                }
		    });
		Tooltip tooltip = new Tooltip();
		tooltip.setText(
		    "Intermediate means after greyscale\n");
		checkbox.setTooltip(tooltip);
		return checkbox;
	}
	
	private Separator getSeparator(){
		//Vertical separator
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);	
		separator.setValignment(VPos.CENTER);
		separator.setMaxWidth(40);
		separator.setPrefHeight(420);
		separator.setPrefWidth(5);
		separator.setLayoutX(620);
		separator.setLayoutY(20);
		return separator;
	}
	
	private void updateSlider(Slider slider, ToggleButton Button50, ToggleButton Button100, ListView<String> list){
		slider.valueProperty().addListener((
	            ObservableValue<? extends Number> ov, 
	            Number old_val, Number new_val) -> {
	            	// make slider only take 3 values
	            	if(new_val.intValue() == 0){
	            		slider.setValue(0);
	            		Button50.setSelected(false);
	            	}
	            	if(new_val.intValue()>= 1 && new_val.intValue() <= 50){
	            		slider.setValue(50);
	            		Button50.setSelected(true);
	            	}
	            	if(new_val.intValue()>= 51 && new_val.intValue() <= 100){
	            		slider.setValue(100); 
	            		Button100.setSelected(true);
	            	}
	        		ObservableList<String> items = FXCollections.observableArrayList("Start Time: 0", "Intermediate Time: " + intermediateTime.toString(), "Finish Time: " + finishTime.toString());
	        		list.setItems(items);
	            });
	}
	
	private Slider getSlider(ImageView imagePanel){
		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(100);
		slider.setValue(0);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(50);
		slider.setBlockIncrement(50.00);
		slider.setPrefSize(200, 3);
		slider.setLayoutX(630);
		slider.setLayoutY(250);
		slider.valueProperty().addListener((
		            ObservableValue<? extends Number> ov, 
		            Number old_val, Number new_val) -> {
		            	
		            	// add action
		            	if (new_val.intValue() == 0) {
								imagePanel.setImage(SwingFXUtils.toFXImage(picture, null));
						}
		            	if (new_val.intValue() == 50) {
								newPicture = convert_grayscale(picture);
								imagePanel.setImage(SwingFXUtils.toFXImage(newPicture, null));
						} else if (new_val.intValue() == 100) {
								newPicture = convert_prewitt(picture);
								imagePanel.setImage(SwingFXUtils.toFXImage(newPicture, null));
							}
						});
		return slider;

	}
	
	private ComboBox<String> getComboBox(ImageView imagePanel, Label imageName, ProgressBar loadingProgressBar, ProgressIndicator loadingProgressIndicator){
		ComboBox<String> imagesComboBox = new ComboBox<String>();
		imagesComboBox.getItems().addAll(
            "Image1",
            "Image2",
            "Image3"
        );
		imagesComboBox.setLayoutX(30);
		imagesComboBox.setLayoutY(370);
		
		// add action
		imagesComboBox.setOnAction((ActionEvent ev) -> {
			if (imagesComboBox.getSelectionModel().getSelectedItem().toString().equals("Image1")) {
				loadingProgressBar.setStyle("-fx-accent: red;");	
				loadingProgressBar.setProgress(0.2);
				loadingProgressIndicator.setProgress(0.2);
				
				File input = new File(".\\src\\Images\\barca.bmp");
				readImageFromFile(input);

				loadingProgressBar.setProgress(0.5);
				loadingProgressIndicator.setProgress(0.5);
				if (input != null) {
					loadingProgressBar.setProgress(0.6);
					loadingProgressIndicator.setProgress(0.6);
					imagePanel.setFitHeight(picture.getHeight());
					imagePanel.setFitWidth(picture.getWidth());
					imagePanel.setImage(SwingFXUtils.toFXImage(picture, null));
					imageName.setTranslateX(303);
					imageName.setTranslateY(350);
					imageName.setText("Image1");
					if(imagePanel.isVisible())
						imageName.setVisible(true);
					else 
						imageName.setVisible(false);
					loadingProgressBar.setProgress(1);
					loadingProgressIndicator.setProgress(1);
					loadingProgressBar.setStyle("-fx-accent: green;");
				}
			}
			else if (imagesComboBox.getSelectionModel().getSelectedItem().toString().equals("Image2")) {
				loadingProgressBar.setStyle("-fx-accent: red;");	
				loadingProgressBar.setProgress(0.2);
				loadingProgressIndicator.setProgress(0.2);
				
				File input = new File(".\\src\\Images\\baloane.bmp");
				readImageFromFile(input);

				loadingProgressBar.setProgress(0.5);
				loadingProgressIndicator.setProgress(0.5);
				if (input != null) {
					loadingProgressBar.setProgress(0.6);
					loadingProgressIndicator.setProgress(0.6);
					imagePanel.setFitHeight(picture.getHeight());
					imagePanel.setFitWidth(picture.getWidth());
					imagePanel.setImage(SwingFXUtils.toFXImage(picture, null));
					imageName.setTranslateX(303);
					imageName.setTranslateY(350);
					imageName.setText("Image2");
					if(imagePanel.isVisible())
						imageName.setVisible(true);
					else 
						imageName.setVisible(false);
					loadingProgressBar.setProgress(1);
					loadingProgressIndicator.setProgress(1);
					loadingProgressBar.setStyle("-fx-accent: green;");
				}
			} else if (imagesComboBox.getSelectionModel().getSelectedItem().toString().equals("Image3")) {
				loadingProgressBar.setStyle("-fx-accent: red;");	
				loadingProgressBar.setProgress(0.2);
				loadingProgressIndicator.setProgress(0.2);
				
				File input = new File(".\\src\\Images\\roata.bmp");
				readImageFromFile(input);
				
				loadingProgressBar.setProgress(0.5);
				loadingProgressIndicator.setProgress(0.5);
				if (input != null) {
					loadingProgressBar.setProgress(0.6);
					loadingProgressIndicator.setProgress(0.6);
					imagePanel.setFitHeight(picture.getHeight());
					imagePanel.setFitWidth(picture.getWidth());
					imagePanel.setImage(SwingFXUtils.toFXImage(picture, null));
					imageName.setTranslateX(303);
					imageName.setTranslateY(350);
					imageName.setText("Image3");
					if(imagePanel.isVisible())
						imageName.setVisible(true);
					else 
						imageName.setVisible(false);
					loadingProgressBar.setProgress(1);
					loadingProgressIndicator.setProgress(1);
					loadingProgressBar.setStyle("-fx-accent: green;");
				}
			}
        });
		return imagesComboBox;
	}
	
	private ToggleGroup getRadioToggle(){
		ToggleGroup toggleGroup = new ToggleGroup();
		return toggleGroup;
	}
	
	private ProgressBar getLoadingProgressBar(){
		// Progress bar for the loading of the file
		ProgressBar loadingProgressBar = new ProgressBar();
		loadingProgressBar.setPrefSize(250, 25);
		loadingProgressBar.setStyle("-fx-accent: red;");
		loadingProgressBar.setLayoutX(200);
		loadingProgressBar.setLayoutY(405);
		loadingProgressBar.setProgress(0.0);
		loadingProgressBar.setVisible(true);
		return loadingProgressBar;
	}
	
	private ProgressIndicator getProgressIndicator(){
		ProgressIndicator progressIndicator = new ProgressIndicator();
		progressIndicator.setLayoutX(115);
		progressIndicator.setLayoutY(150);
		progressIndicator.setProgress(0.0);
		progressIndicator.setVisible(false);
		return progressIndicator;
	}
	
	private ToggleGroup getButtonToggle(ImageView imagePanel, Slider transformSlider, ListView<String> list){
		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, final Toggle toggle, final Toggle new_toggle) {

				ToggleButton toggleButton = (ToggleButton) new_toggle;

				if (toggleButton.getText() == "50") {
						transformSlider.setValue(50);
						newPicture = convert_grayscale(picture);
						imagePanel.setImage(SwingFXUtils.toFXImage(newPicture, null));
					
				} else if (toggleButton.getText() == "100") {
						transformSlider.setValue(180);
						newPicture = convert_prewitt(picture);
						imagePanel.setImage(SwingFXUtils.toFXImage(newPicture, null));
				}
				ObservableList<String> items = FXCollections.observableArrayList("Start Time: 0", "Intermediate Time: " + intermediateTime.toString(), "Finish Time: " + finishTime.toString());
				list.setItems(items);
			}
		});
		return toggleGroup;
	}
	
	private Button getLoadButton(Stage primaryStage, ImageView imagePanel, ProgressBar loadingProgressBar, ProgressIndicator progresIndicator, Label imageName){
		// Adds the button
		Button loadButton = new Button();
		loadButton.setText("Load Image");
		loadButton.setPrefSize(250, 25);
		loadButton.setLayoutX(200);
		loadButton.setLayoutY(370);
		
		// Adds the action
		loadButton.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						File rootDir = new File(".\\src\\Images");
						FileChooser fileChooser = new FileChooser();
						fileChooser.setInitialDirectory(rootDir);
						loadingProgressBar.setProgress(0.5);
						progresIndicator.setProgress(0.5);
						loadingProgressBar.setStyle("-fx-accent: red;");
						File file = fileChooser.showOpenDialog(primaryStage);
						loadingProgressBar.setProgress(0.6);
						progresIndicator.setProgress(0.6);
							if (file != null) {
								readImageFromFile(file);
								imagePanel.setFitHeight(picture.getHeight());
								imagePanel.setFitWidth(picture.getWidth());
								loadingProgressBar.setProgress(0.9);
								imagePanel.setImage(SwingFXUtils.toFXImage(picture, null));
								imageName.setTranslateX(430);
								imageName.setTranslateY(250);
								imageName.setText(file.getName().subSequence(0, file.getName().length()-4).toString());
								if(imagePanel.isVisible())
									imageName.setVisible(true);
								else 
									imageName.setVisible(false);
								loadingProgressBar.setProgress(1);
								progresIndicator.setProgress(1);
								loadingProgressBar.setStyle("-fx-accent: green;");	}
					}
		});
		return loadButton;
	}

	
	private RadioButton getGreyscaleRadioButton(ToggleGroup toggleGroup){
		// Declaring the radio button for converting image with greyscale
		RadioButton bar = new RadioButton("Convert Image with Greyscale");
		bar.setLayoutX(640);
		bar.setLayoutY(30);
		bar.setSelected(true);
		bar.setToggleGroup(toggleGroup);
		
		return bar;
	}
	
	private ToggleButton get50Toggle(ToggleGroup buttonToggleGroup){
		ToggleButton greyscale = new ToggleButton("50");
		greyscale.setLayoutX(700);
		greyscale.setLayoutY(200);
		greyscale.setToggleGroup(buttonToggleGroup);
		
		return greyscale;
	}
	
	private ToggleButton get100Toggle(ToggleGroup buttonToggleGroup){
		ToggleButton prewitt = new ToggleButton("100");
		prewitt.setLayoutX(770);
		prewitt.setLayoutY(200);
		prewitt.setToggleGroup(buttonToggleGroup);
		
		return prewitt;
	}
	
	private ChoiceBox<String> showOrHide(ImageView imagePanel, Label imageName){
		ChoiceBox<String> showHide = new ChoiceBox<String>();
		showHide.setPrefSize(110, 20);
		showHide.setLayoutX(500);
		showHide.setLayoutY(370);
		String choices[] = { "Show", "Hide" };
		showHide.setItems(FXCollections.observableArrayList(choices));
		showHide.setValue(choices[0]);
		
		// Add action
		showHide.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			// if the item of the list is changed
			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, Number value, Number new_value) {
				if (new_value.intValue() == 1) {
					imagePanel.setVisible(false);
					imageName.setVisible(false);

				} else if (new_value.intValue() == 0) {
					imagePanel.setVisible(true);
					imageName.setVisible(true);
				}
			}
		});
		
		return showHide;
	}
	
	private RadioButton getPrewittRadioButton(ToggleGroup toggleGroup){
		// Declaring the radio button for blocking the image
		RadioButton indicator = new RadioButton("Convert Image with Prewitt");
		indicator.setLayoutX(640);
		indicator.setLayoutY(70);
		indicator.setSelected(false);
		indicator.setToggleGroup(toggleGroup);
		
		return indicator;
	}
	
	private static BufferedImage convert_prewitt(BufferedImage img){
		BufferedImage inputImg = convert_grayscale(img);
		BufferedImage outputImg = new BufferedImage(inputImg.getWidth(),inputImg.getHeight(),inputImg.getType());
        outputImg = convert_final(inputImg,outputImg);
        finishTime = System.currentTimeMillis() - startTime;
        return outputImg;
	}
	
	private static BufferedImage convert_grayscale(BufferedImage inputImg){
		startTime = System.currentTimeMillis();
		//get image width and height
		int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        
		BufferedImage img = new BufferedImage(width, height, inputImg.getType());
        
        //convert to grayscale
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = inputImg.getRGB(x,y);
                
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                
                //calculate average
                int avg = (r+g+b)/3;
                
                //replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                
                img.setRGB(x, y, p);              
            }
        }
        intermediateTime = System.currentTimeMillis() - startTime;
        return img;
    }
	
	private static double convolution(int[][] pixelMatrix){
		

        int[][] filter = {
                { -1, -1, -1 },
				{  0,  0,  0 },
                { 1, 1, 1 }
		};
        
        // inmultirea pixelilor din matrice cu masca asociata operatorului Prewitt
	    int gy=(pixelMatrix[0][0]*-1)+(pixelMatrix[0][1]*-1)+(pixelMatrix[0][2]*-1)+(pixelMatrix[2][0]*1)+(pixelMatrix[2][1]*1)+(pixelMatrix[2][2]*1);
	    int gx=(pixelMatrix[0][0]*-1)+(pixelMatrix[0][2]*1)+(pixelMatrix[1][0]*-1)+(pixelMatrix[1][2]*1)+(pixelMatrix[2][0]*-1)+(pixelMatrix[2][2]*1);
	    return Math.sqrt(Math.pow(gy,2)+Math.pow(gx,2));

	}
	
	private static BufferedImage convert_final(BufferedImage inputImg, BufferedImage outputImg){
	    int[][] pixelMatrix=new int[3][3];
	    
		for(int i=1;i<inputImg.getWidth()-1;i++){
            for(int j=1;j<inputImg.getHeight()-1;j++){
                pixelMatrix[0][0]=new Color(inputImg.getRGB(i-1,j-1)).getRed();
                pixelMatrix[0][1]=new Color(inputImg.getRGB(i-1,j)).getRed();
                pixelMatrix[0][2]=new Color(inputImg.getRGB(i-1,j+1)).getRed();
                pixelMatrix[1][0]=new Color(inputImg.getRGB(i,j-1)).getRed();
                pixelMatrix[1][2]=new Color(inputImg.getRGB(i,j+1)).getRed();
                pixelMatrix[2][0]=new Color(inputImg.getRGB(i+1,j-1)).getRed();
                pixelMatrix[2][1]=new Color(inputImg.getRGB(i+1,j)).getRed();
                pixelMatrix[2][2]=new Color(inputImg.getRGB(i+1,j+1)).getRed();

                int edge=(int) convolution(pixelMatrix);
                outputImg.setRGB(i,j,(edge<<16 | edge<<8 | edge));
            }
        }
		return outputImg;
	}
	
	
	private void readImageFromFile(File inputFile) {
		try {
			picture = ImageIO.read(inputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		newPicture = new BufferedImage(picture.getWidth(), picture.getHeight(), picture.getType());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
