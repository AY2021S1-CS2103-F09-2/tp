package seedu.pivot.model.investigationcase;

import java.util.List;
import java.util.function.Predicate;

import seedu.pivot.model.investigationcase.caseperson.CasePerson;
import seedu.pivot.model.investigationcase.caseperson.Suspect;
import seedu.pivot.model.investigationcase.caseperson.Victim;
import seedu.pivot.model.investigationcase.caseperson.Witness;

/**
 * Tests that a {@code Case}'s details matches any of the keywords given.
 */
public class DetailsContainsKeywordsPredicate implements Predicate<Case> {
    private final List<String> keywords;

    public DetailsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Case investigationCase) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    String keywordLowerCase = keyword.toLowerCase();
                    String titleLowerCase = investigationCase.getTitle().getAlphaNum().toLowerCase();
                    String descriptionLowerCase = investigationCase.getDescription().toString().toLowerCase();
                    String statusLowerCase = investigationCase.getStatus().toString().toLowerCase();
                    String documentsLowerCase = getDocumentsInfo(investigationCase).toLowerCase();
                    String suspectsLowerCase = getSuspectsInfo(investigationCase).toLowerCase();
                    String victimsLowerCase = getVictimsInfo(investigationCase).toLowerCase();
                    String witnessLowerCase = getWitnessesInfo(investigationCase).toLowerCase();

                    return titleLowerCase.contains(keywordLowerCase)
                            || descriptionLowerCase.contains(keywordLowerCase)
                            || statusLowerCase.contains(keywordLowerCase)
                            || documentsLowerCase.contains(keywordLowerCase)
                            || suspectsLowerCase.contains(keywordLowerCase)
                            || victimsLowerCase.contains(keywordLowerCase)
                            || witnessLowerCase.contains(keywordLowerCase);
                }
                );
    }

    /**
     * Obtains information of all documents in a case, with each word separated by an empty space.
     *
     * @param investigationCase The case to obtain information from.
     * @return String containing all documents information (name and reference).
     */
    public String getDocumentsInfo(Case investigationCase) {
        StringBuilder documentsInfo = new StringBuilder();
        for (Document doc : investigationCase.getDocuments()) {
            documentsInfo.append(doc.getName()).append(" ");
            documentsInfo.append(doc.getReference().getFileName()).append(" ");
        }
        return documentsInfo.toString();
    }

    /**
     * Obtains information of all suspects in a case, with each word separated by an empty space.
     *
     * @param investigationCase The case to obtain information from.
     * @return String containing all witnesses information.
     */
    public String getSuspectsInfo(Case investigationCase) {
        StringBuilder suspectsInfo = new StringBuilder();
        for (Suspect suspect : investigationCase.getSuspects()) {
            appendPersonDetails(suspectsInfo, suspect);
        }
        return suspectsInfo.toString();
    }

    /**
     * Obtains information of all victims in a case, with each word separated by an empty space.
     *
     * @param investigationCase The case to obtain information from.
     * @return String containing all witnesses information.
     */
    public String getWitnessesInfo(Case investigationCase) {
        StringBuilder witnessesInfo = new StringBuilder();
        for (Witness witness : investigationCase.getWitnesses()) {
            appendPersonDetails(witnessesInfo, witness);
        }
        return witnessesInfo.toString();
    }

    /**
     * Obtains information of all victims in a case, with each word separated by an empty space.
     *
     * @param investigationCase The case to obtain information from.
     * @return String containing all victims information.
     */
    public String getVictimsInfo(Case investigationCase) {
        StringBuilder victimsInfo = new StringBuilder();
        for (Victim victim : investigationCase.getVictims()) {
            appendPersonDetails(victimsInfo, victim);
        }
        return victimsInfo.toString();
    }

    /**
     * Appends all fields of a person into the StringBuilder defined as first parameter,
     * with each field separated by an empty space.
     *
     * @param builder The StringBuilder to append to.
     * @param person The CasePerson whose details are to be added.
     */
    public void appendPersonDetails(StringBuilder builder, CasePerson person) {
        builder.append(person.getName()).append(" ");
        builder.append(person.getSex().toString()).append(" ");
        builder.append(person.getPhone()).append(" ");
        builder.append(person.getEmail()).append(" ");
        builder.append(person.getAddress()).append(" ");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DetailsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DetailsContainsKeywordsPredicate) other).keywords)); // state check
    }

}
