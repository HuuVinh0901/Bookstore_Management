package util;

import javax.swing.*;

public class DialogUtils {
    public static void showErrorDialog(Object parentComponent, String message) {
        JOptionPane.showMessageDialog((parentComponent instanceof java.awt.Component) ? (java.awt.Component) parentComponent : null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void showSuccessMessage(Object parentComponent, String message) {
        JOptionPane.showMessageDialog((parentComponent instanceof java.awt.Component) ? (java.awt.Component) parentComponent : null, message);
    }

    public static void showErrorMessage(Object parentComponent, String message) {
        JOptionPane.showMessageDialog((parentComponent instanceof java.awt.Component) ? (java.awt.Component) parentComponent : null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
