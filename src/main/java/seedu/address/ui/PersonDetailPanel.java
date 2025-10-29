package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;


/**
 * Panel containing the list of persons.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonDetailPanel.class);

    @FXML
    private Label unknownLabel;

    @FXML
    private Label copyGuideLabel;

    @FXML
    private GridPane detailGrid;

    @FXML
    private Label focusName;

    @FXML
    private Label focusPhone;

    @FXML
    private Label focusCompany;

    @FXML
    private Label focusEmail;

    @FXML
    private Label focusDetail;


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
     *
     */
    @FXML
    private void handleDetailClick(ActionEvent event) {
        Object source = event.getSource();
        if (source instanceof Node) {
            Node button = (Node) source;
            String id = button.getId();
            id = id.replace("Button", "");

            Label target;
            switch (id) {
            case "focusName":
                target = focusName;
                break;
            case "focusPhone":
                target = focusPhone;
                break;
            case "focusEmail":
                target = focusEmail;
                break;
            case "focusCompany":
                target = focusCompany;
                break;
            case "focusDetail":
                target = focusDetail;
                break;
            default:
                target = null;
                break;
            }

            if (target != null) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(target.getText());
                clipboard.setContent(content);

                // Show copied notification over button
                Tooltip tooltip = new Tooltip("Copied!");
                tooltip.show(button, button.localToScreen(0, 0).getX(), button.localToScreen(0, 0).getY() - 30);

                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> tooltip.hide());
                pause.play();
            }
        }
    }

    /**
     * Helper method to streamline updating of UI Elements
     */
    private void updateDetails(Person p) {
        if (p == null) {
            unknownLabel.setVisible(true);
            unknownLabel.setManaged(true);

            detailGrid.setVisible(false);
            detailGrid.setManaged(false);
            copyGuideLabel.setManaged(false);
            copyGuideLabel.setVisible(false);
        } else {
            unknownLabel.setVisible(false);
            unknownLabel.setManaged(false);
            detailGrid.setVisible(true);
            detailGrid.setManaged(true);
            copyGuideLabel.setVisible(true);
            copyGuideLabel.setManaged(true);

            focusName.setText(p.getName().toString());
            focusPhone.setText(p.getPhone().toString());
            focusEmail.setText(p.getEmail().toString());
            focusCompany.setText(p.getCompany().toString());

            // Display detail if present, otherwise show placeholder
            String detailText = p.getDetail().toString();
            if (detailText.isEmpty()) {
                focusDetail.setText("(No detail)");
            } else {
                focusDetail.setText(detailText);
            }
        }
    }
}
