package Controllers;

import com.jfoenix.controls.JFXTimePicker;
import javafx.util.StringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatConverter {

    public void convert(JFXTimePicker timePicker, String format) {
        StringConverter<LocalTime> converter = new StringConverter<LocalTime>() {
            DateTimeFormatter timeFormatter =
                    DateTimeFormatter.ofPattern(format);//dd-mm-yyyy

            @Override
            public String toString(LocalTime time) {
                if (time != null) {
                    return timeFormatter.format(time);
                } else {
                    return "";
                }
            }

            @Override
            public LocalTime fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalTime.parse(string, timeFormatter);
                } else {
                    return null;
                }
            }
        };
        timePicker.setConverter(converter);
        timePicker.setValue(LocalTime.now());
    }
}
