package seedu.address;

import org.junit.jupiter.api.Test;

public class MainAppTest {
    @Test
    public void init_doesNotThrow() throws Exception {
        MainApp app = new MainApp();
        app.init();
    }
}
