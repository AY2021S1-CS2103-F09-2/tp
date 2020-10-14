package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.investigationcase.Case;
import seedu.address.model.investigationcase.CasePerson;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private DocumentListPanel documentListPanel;
    private CasePersonListPanel suspectListPanel;
    private CasePersonListPanel witnessListPanel;
    private CasePersonListPanel victimListPanel;
    private SimpleObjectProperty<Index> indexSimpleObjectProperty;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    // Case Summary
    @FXML
    private Label caseTitle;

    @FXML
    private Label caseDescription;

    @FXML
    private Label caseStatus;

    // Case Document
    @FXML
    private StackPane documentListPanelPlaceholder;

    // Case Persons
    @FXML
    private StackPane suspectListPanelPlaceholder;

    @FXML
    private StackPane witnessListPanelPlaceholder;

    @FXML
    private StackPane victimListPanelPlaceholder;

    // Titles
    @FXML
    private Label caseDocumentsTitle;

    @FXML
    private Label caseSuspectsTitle;

    @FXML
    private Label caseWitnessesTitle;

    @FXML
    private Label caseVictimsTitle;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        indexSimpleObjectProperty = UiStateManager.getCaseState();
        UiStateManager.getCaseState().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                updateCaseInformationPanel((Index) newValue);
            }
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredCaseList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        updateCaseInformationPanel(indexSimpleObjectProperty.get());
    }

    /**
     * Updates the CaseInformationPanel using a Case by the given Index.
     * @param index
     */
    private void updateCaseInformationPanel(Index index) {
        if (index == null) {
            showBlankPanel();
        } else {
            fillPanel();
        }
    }

    private void showBlankPanel() {
        caseTitle.setText("");
        caseDescription.setText("");
        caseStatus.setText("");

        caseDocumentsTitle.setText("");
        caseSuspectsTitle.setText("");
        caseWitnessesTitle.setText("");
        caseVictimsTitle.setText("");

        documentListPanel = new DocumentListPanel(FXCollections.observableList(new ArrayList<>()));
        documentListPanelPlaceholder.getChildren().add(documentListPanel.getRoot());

        suspectListPanel = new CasePersonListPanel(FXCollections.observableList(new ArrayList<>()));
        suspectListPanelPlaceholder.getChildren().add(suspectListPanel.getRoot());

        witnessListPanel = new CasePersonListPanel(FXCollections.observableList(new ArrayList<>()));
        witnessListPanelPlaceholder.getChildren().add(witnessListPanel.getRoot());

        victimListPanel = new CasePersonListPanel(FXCollections.observableList(new ArrayList<>()));
        victimListPanelPlaceholder.getChildren().add(victimListPanel.getRoot());
    }

    private void fillPanel() {
        Case investigationCase = logic.getFilteredCaseList().get(indexSimpleObjectProperty.get().getZeroBased());

        caseTitle.setText(investigationCase.getTitle().toString());
        caseDescription.setText(investigationCase.getDescription().toString());
        caseStatus.setText(investigationCase.getStatus().toString());

        caseDocumentsTitle.setText("DOCUMENTS");
        caseSuspectsTitle.setText("SUSPECTS");
        caseWitnessesTitle.setText("WITNESSES");
        caseVictimsTitle.setText("VICTIMS");

        documentListPanel = new DocumentListPanel(FXCollections.observableList(investigationCase.getDocuments()));
        documentListPanelPlaceholder.getChildren().add(documentListPanel.getRoot());

        suspectListPanel = new CasePersonListPanel(FXCollections.observableList(
                investigationCase.getSuspects().stream().map(x -> (CasePerson) x).collect(Collectors.toList())));
        suspectListPanelPlaceholder.getChildren().add(suspectListPanel.getRoot());

        witnessListPanel = new CasePersonListPanel(FXCollections.observableList(
                investigationCase.getWitnesses().stream().map(x -> (CasePerson) x).collect(Collectors.toList())));
        witnessListPanelPlaceholder.getChildren().add(witnessListPanel.getRoot());

        victimListPanel = new CasePersonListPanel(FXCollections.observableList(
                investigationCase.getVictims().stream().map(x -> (CasePerson) x).collect(Collectors.toList())));
        victimListPanelPlaceholder.getChildren().add(victimListPanel.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
