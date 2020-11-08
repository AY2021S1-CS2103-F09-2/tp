package seedu.pivot.model.investigationcase;

import static java.util.Objects.requireNonNull;
import static seedu.pivot.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.pivot.commons.util.FileUtil;

/**
 * Represents a Document's reference in an investigation case.
 * Guarantees: immutable; is valid as declared in {@link #isValidReference(String)}
 */
public class Reference {

    public static final String MESSAGE_CONSTRAINTS =
            "File should be placed in the ./references folder. "
                    + "Please only enter the non-blank file name with its extension.";

    private static final String DEFAULT_FILEPATH = "./references/";
    protected final Path path;
    protected final String fileName;

    /**
     * Constructs a { @code Reference }.
     *
     * @param fileName A valid file name in the default folder.
     */
    public Reference(String fileName) {
        requireNonNull(fileName);
        checkArgument(isValidReference(fileName), MESSAGE_CONSTRAINTS);
        this.path = Paths.get(getFilePath() + fileName);
        this.fileName = fileName;
    }

    /**
     * Returns the directory path used to store the references.
     */
    public String getFilePath() {
        return DEFAULT_FILEPATH;
    }

    /**
     * Returns true if a given file path is valid.
     */
    public static boolean isValidReference(String fileName) {
        if (fileName.isEmpty()) {
            return false;
        }
        return FileUtil.isValidPath(DEFAULT_FILEPATH + fileName);
    }

    /**
     * Returns true if the {@code Reference} exists in the program.
     */
    public boolean isExists() {
        return FileUtil.isFileExists(path);
    }

    @Override
    public String toString() {
        return this.path.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reference // instanceof handles nulls
                && path.equals(((Reference) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public Path getPath() {
        return this.path;
    }

    public String getFileName() {
        return this.fileName;
    }
}
