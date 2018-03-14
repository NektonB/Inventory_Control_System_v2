package Controllers;

import com.jfoenix.controls.JFXTextField;

public class TextValidator {

    public void validateDigit(JFXTextField txtValue, int digitCount, int decimalPoints) {
        txtValue.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0," + digitCount + "}([\\.]\\d{0," + decimalPoints + "})?")) {
                txtValue.setText(oldValue);
            }
        });

        txtValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (txtValue.isFocused()) {
                if (txtValue.getText().equals("0") | txtValue.getText().equals("0.0")) {
                    txtValue.setText("");
                }
            } else {
                if (txtValue.getText().equals("")) {
                    txtValue.setText("0");
                }
            }
        });
    }

}
