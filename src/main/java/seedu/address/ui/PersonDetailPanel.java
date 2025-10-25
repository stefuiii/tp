package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;


/**
 * Panel containing the list of persons.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailPanel.class);

    @FXML
    private Label tempFocusName;


    /**
     * Creates a {@code PersonDetailPanel} with the given {@code Person}.
     */
    public PersonDetailPanel(SimpleObjectProperty<Person> focusPersonObservable) {
        super(FXML);

        focusPersonObservable.addListener((obs, oldPerson, newPerson) -> {
            updateDetails(newPerson);
        });

        // Init
        Person focusPerson = focusPersonObservable.get();
        updateDetails(focusPerson);
    }

    /**
     * Helper method to streamline updating of UI Elements
     */
    private void updateDetails(Person p) {
        if (p == null) {
            tempFocusName.setText("UNKNOWN");
        } else {
            tempFocusName.setText(p.getName().toString());
        }
    }
}
