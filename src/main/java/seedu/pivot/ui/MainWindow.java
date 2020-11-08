package seedu.pivot.ui;

import static seedu.pivot.logic.commands.Command.TYPE_DOC;
import static seedu.pivot.logic.commands.Command.TYPE_SUSPECT;
import static seedu.pivot.logic.commands.Command.TYPE_VICTIM;
import static seedu.pivot.logic.commands.Command.TYPE_WITNESS;

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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.pivot.commons.core.GuiSettings;
import seedu.pivot.commons.core.LogsCenter;
import seedu.pivot.commons.core.index.Index;
import seedu.pivot.logic.Logic;
import seedu.pivot.logic.commands.CommandResult;
import seedu.pivot.logic.commands.exceptions.CommandException;
import seedu.pivot.logic.parser.exceptions.ParseException;
import seedu.pivot.model.investigationcase.ArchiveStatus;
import seedu.pivot.model.investigationcase.Case;
import seedu.pivot.model.investigationcase.caseperson.CasePerson;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String EMPTY = "";

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
    private SimpleObjectProperty<String> tabSimpleObjectProperty;
    private SimpleObjectProperty<ArchiveStatus> archiveStatusSimpleObjectProperty;

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

    @FXML
    private TabPane caseTabPane;

    @FXML
    private Tab documentTab;

    @FXML
    private Tab suspectTab;

    @FXML
    private Tab witnessTab;

    @FXML
    private Tab victimTab;

    @FXML
    private Label sectionLabel;

    private final String homepage = "Home";
    private final String archive = "Archive";
    private final String casePanel = "Case Page";
    private final String mainPanel = "Main Page";
    private String currentPage = homepage;
    private String currentPanel = mainPanel;

    // For changing CSS
    private final String darkThemeResource = getClass().getResource("/view/DarkTheme.css").toExternalForm();
    private final String blueThemeResource = getClass().getResource("/view/BlueTheme.css").toExternalForm();

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
                currentPanel = newValue == null ? mainPanel : casePanel;
                updateSectionLabel(EMPTY);
            }
        });

        tabSimpleObjectProperty = UiStateManager.getTabState();
        UiStateManager.getTabState().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                setTabSelected((String) newValue);
            }
        });

        archiveStatusSimpleObjectProperty = UiStateManager.getCurrentSection();
        UiStateManager.getCurrentSection().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    boolean isDefaultPage = newValue.equals(ArchiveStatus.DEFAULT);
                    currentPage = isDefaultPage ? homepage : archive;

                    if (isDefaultPage) {
                        primaryStage.getScene().getStylesheets().remove(darkThemeResource);
                        if (!primaryStage.getScene().getStylesheets().contains(blueThemeResource)) {
                            primaryStage.getScene().getStylesheets().add(blueThemeResource);
                        }
                    } else {
                        primaryStage.getScene().getStylesheets().remove(blueThemeResource);
                        if (!primaryStage.getScene().getStylesheets().contains(darkThemeResource)) {
                            primaryStage.getScene().getStylesheets().add(darkThemeResource);
                        }
                    }
                }
                updateSectionLabel(EMPTY);
            }
        });

        caseTabPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            caseTabPane.setTabMinWidth(caseTabPane.getWidth() / 4 - 25);
            caseTabPane.setTabMaxWidth(caseTabPane.getWidth() / 4 - 25);
        });

        caseTabPane.setOnMouseClicked(event -> {
            Tab tab = caseTabPane.getSelectionModel().getSelectedItem();
            logger.info("Tab pane clicked, setting tabState: " + tab.getText());
            if (tab.getText().equals(documentTab.getText())) {
                UiStateManager.setTabState(TYPE_DOC);
            } else if (tab.getText().equals(suspectTab.getText())) {
                UiStateManager.setTabState(TYPE_SUSPECT);
            } else if (tab.getText().equals(witnessTab.getText())) {
                UiStateManager.setTabState(TYPE_WITNESS);
            } else if (tab.getText().equals(victimTab.getText())) {
                UiStateManager.setTabState(TYPE_VICTIM);
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
        logger.info("MainWindow: Setup Placeholders");
        personListPanel = new PersonListPanel(logic.getFilteredCaseList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getPivotFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // For Section Label
        String firstInit = "Welcome to PIVOT. ";
        updateSectionLabel(firstInit);

        updateCaseInformationPanel(indexSimpleObjectProperty.get());
    }

    /**
     * Updates the CaseInformationPanel using a Case by the given Index.
     * @param index
     */
    private void updateCaseInformationPanel(Index index) {
        logger.info("MainWindow: Updating Case Information Panel");
        setMainWindowPanel(index);
    }

    private void setMainWindowPanel(Index index) {
        logger.info("Updating Case Information Panel with index:" + index);
        Case investigationCase = null;
        caseTabPane.setVisible(index != null);

        if (index != null) {
            logger.info("Updating Case Information Panel with Case");
            investigationCase = logic.getFilteredCaseList().get(indexSimpleObjectProperty.get().getZeroBased());
        }

        caseTitle.setText(investigationCase == null ? EMPTY : investigationCase.getTitle().toString());
        caseDescription.setText(investigationCase == null ? EMPTY : investigationCase.getDescription().toString());
        caseStatus.setText(investigationCase == null ? EMPTY : investigationCase.getStatus().toString());

        documentListPanel = new DocumentListPanel(FXCollections.observableList(
                investigationCase == null ? new ArrayList<>() : investigationCase.getDocuments()));
        documentListPanelPlaceholder.getChildren().add(documentListPanel.getRoot());

        suspectListPanel = new CasePersonListPanel(FXCollections.observableList(
                investigationCase == null ? new ArrayList<>() : investigationCase.getSuspects().stream()
                        .map(x -> (CasePerson) x).collect(Collectors.toList())));
        suspectListPanelPlaceholder.getChildren().add(suspectListPanel.getRoot());

        witnessListPanel = new CasePersonListPanel(FXCollections.observableList(
                investigationCase == null ? new ArrayList<>() : investigationCase.getWitnesses().stream()
                        .map(x -> (CasePerson) x).collect(Collectors.toList())));
        witnessListPanelPlaceholder.getChildren().add(witnessListPanel.getRoot());

        victimListPanel = new CasePersonListPanel(FXCollections.observableList(
                investigationCase == null ? new ArrayList<>() : investigationCase.getVictims().stream()
                        .map(x -> (CasePerson) x).collect(Collectors.toList())));
        victimListPanelPlaceholder.getChildren().add(victimListPanel.getRoot());
    }

    private void setTabSelected(String tabType) {
        if (tabType == null) {
            caseTabPane.getSelectionModel().clearSelection();
        } else {
            switch (tabType) {
            case TYPE_DOC:
                caseTabPane.getSelectionModel().select(documentTab);
                break;
            case TYPE_SUSPECT:
                caseTabPane.getSelectionModel().select(suspectTab);
                break;
            case TYPE_VICTIM:
                caseTabPane.getSelectionModel().select(victimTab);
                break;
            case TYPE_WITNESS:
                caseTabPane.getSelectionModel().select(witnessTab);
                break;
            default:
                caseTabPane.getSelectionModel().clearSelection();
            }
        }

    }

    private void updateSectionLabel(String firstInit) {
        String divider = " - ";
        String preamble = "You are now at ";
        sectionLabel.setText(firstInit + preamble + currentPage + divider + currentPanel);
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
        logger.info("MainWindow: Handling Help");
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
        logger.info("MainWindow: Handling Exit");
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
     * @see seedu.pivot.logic.Logic#execute(String)
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
