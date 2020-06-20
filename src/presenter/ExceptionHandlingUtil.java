package presenter;

import javax.swing.*;

class ExceptionHandlingUtil {
    static void handleUnexpectedError(Exception ex, JFrame view) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(view, "Unexpected error occurred",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
