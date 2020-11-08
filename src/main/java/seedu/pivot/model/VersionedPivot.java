package seedu.pivot.model;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.pivot.logic.commands.Page;
import seedu.pivot.logic.commands.Undoable;

public class VersionedPivot {

    public static final String INITIAL_COMMAND = "Initial command";
    public static final int INITIAL_STATE = 0;

    private final List<PivotState> pivotStateList = new ArrayList<>();

    private int currentStatePointer;
    private Undoable commandResult = null;
    private String commandMessageResult = "";

    /**
     * Creates a VersionedPivot object with the list of Pivot states being
     * initialised with the initial state. The current state pointer points to the
     * initial state.
     * @param pivot The initial Pivot state.
     */
    public VersionedPivot(ReadOnlyPivot pivot) {
        requireNonNull(pivot);
        pivotStateList.add(new PivotState(pivot, null, INITIAL_COMMAND));
        currentStatePointer = INITIAL_STATE;
    }

    /**
     * Creates a VersionedPivot object with the fields directly initialised with
     * the arguments.
     * @param pivotStateList The list of Pivot states.
     * @param currentStatePointer The index of the current Pivot state in the pivotStateList.
     */
    public VersionedPivot(List<PivotState> pivotStateList, int currentStatePointer,
            Undoable commandResult, String commandMessageResult) {
        this.pivotStateList.addAll(pivotStateList);
        this.currentStatePointer = currentStatePointer;
        this.commandResult = commandResult;
        this.commandMessageResult = commandMessageResult;

    }

    public List<PivotState> getPivotStateList() {
        return this.pivotStateList;
    }

    public int getCurrentStatePointer() {
        return this.currentStatePointer;
    }

    public Undoable getCommandResult() {
        return this.commandResult;
    }

    public String getCommandMessageResult() {
        return this.commandMessageResult;
    }

    /**
     * Checks if the Command being undone or redone is a main page or case page command.
     * @return True if the Command is a main page command, False otherwise.
     */
    public boolean isMainPageCommand() {
        Page page = this.commandResult.getPage();
        return page.equals(Page.MAIN);
    }

    /**
     * Checks if the current state can be undone.
     * @return True if the currentStatePointer is not at the initial state, false otherwise.
     */
    public boolean canUndo() {
        assert currentStatePointer < pivotStateList.size() && currentStatePointer >= 0 : "Index out of bounds";
        return currentStatePointer != INITIAL_STATE;
    }

    /**
     * Checks if the current state can be redone.
     * @return True if the currentStatePointer is not at the most recent state, false otherwise.
     */
    public boolean canRedo() {
        assert currentStatePointer < pivotStateList.size() && currentStatePointer >= 0 : "Index out of bounds";
        return currentStatePointer != (pivotStateList.size() - 1);
    }

    /**
     * Removes all states after the current state and add the current Pivot state into the list of Pivot states.
     * @param pivot Current Pivot state.
     */
    public void commit(ReadOnlyPivot pivot, String commandMessage, Undoable command) {
        requireAllNonNull(pivot, commandMessage, command);
        pivotStateList.add(new PivotState(pivot, command, commandMessage));
        currentStatePointer++;

        assert currentStatePointer < pivotStateList.size() : "Index out of bounds";
    }

    /**
     * Removes all states after the current state.
     */
    public void purgeStates() {
        int len = pivotStateList.size();
        for (int i = len - 1; i > currentStatePointer; i--) {
            pivotStateList.remove(i);
        }

        commandResult = null;
        commandMessageResult = "";
    }

    /**
     * Updates the message of the command that was being redone or undone, as well as
     * whether the command was a main page command.
     */
    public void updateRedoUndoResult() {
        PivotState currentPivotState = pivotStateList.get(currentStatePointer);
        commandMessageResult = currentPivotState.commandMessage;
        commandResult = currentPivotState.command;
    }

    /**
     * Undoes the previous Pivot state.
     * @return Previous Pivot state.
     */
    public ReadOnlyPivot undo() {
        updateRedoUndoResult();

        currentStatePointer--;
        assert currentStatePointer >= 0 : "Index out of bounds";

        PivotState newPivotState = pivotStateList.get(currentStatePointer);
        return newPivotState.pivotState;
    }

    /**
     * Redoes the most recent pivot state.
     * @return Recent pivot state.
     */
    public ReadOnlyPivot redo() {
        currentStatePointer++;
        assert currentStatePointer < pivotStateList.size() : "Index out of bounds";

        updateRedoUndoResult();
        PivotState newPivotState = pivotStateList.get(currentStatePointer);
        return newPivotState.pivotState;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VersionedPivot)) {
            return false;
        }

        VersionedPivot otherVersionedPivot = (VersionedPivot) other;
        return otherVersionedPivot.getPivotStateList().equals(getPivotStateList())
                && otherVersionedPivot.getCurrentStatePointer() == getCurrentStatePointer()
                && otherVersionedPivot.getCommandMessageResult().equals(getCommandMessageResult())
                && (otherVersionedPivot.getCommandResult() == getCommandResult()
                        || otherVersionedPivot.getCommandResult().equals(getCommandResult()));
    }

    public static class PivotState {
        final ReadOnlyPivot pivotState;
        final Undoable command;
        final String commandMessage;

        /**
         * Creates a PivotState with a PIVOT state, the command that caused the change in state, and its corresponding
         * message that was displayed to the user when the command was used.
         * @param pivotState PIVOT state.
         * @param command Command that caused the change in PIVOT state.
         * @param commandMessage Message displayed to user when command was used.
         */
        public PivotState(ReadOnlyPivot pivotState, Undoable command, String commandMessage) {
            this.pivotState = pivotState;
            this.command = command;
            this.commandMessage = commandMessage;
        }

        public ReadOnlyPivot getPivotState() {
            return this.pivotState;
        }

        public String getCommandMessage() {
            return this.commandMessage;
        }

        public Undoable getCommand() {
            return this.command;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof PivotState)) {
                return false;
            }

            PivotState otherPivotState = (PivotState) other;
            return otherPivotState.getPivotState().equals(getPivotState())
                    && otherPivotState.getCommandMessage().equals(getCommandMessage())
                    && (otherPivotState.getCommand() == getCommand()
                            || otherPivotState.getCommand().equals(getCommand()));
        }
    }
}
