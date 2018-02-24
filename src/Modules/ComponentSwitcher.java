package Modules;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

public class ComponentSwitcher {

    private JFXTextField txt01;
    private JFXTextField txt02;
    private JFXTextArea txta01;
    private JFXTextArea txta02;

    public JFXTextField getTxt01() {
        return txt01;
    }

    public void setTxt01(JFXTextField txt01) {
        this.txt01 = txt01;
    }

    public JFXTextField getTxt02() {
        return txt02;
    }

    public void setTxt02(JFXTextField txt02) {
        this.txt02 = txt02;
    }

    public JFXTextArea getTxta01() {
        return txta01;
    }

    public void setTxta01(JFXTextArea txta01) {
        this.txta01 = txta01;
    }

    public JFXTextArea getTxta02() {
        return txta02;
    }

    public void setTxta02(JFXTextArea txta02) {
        this.txta02 = txta02;
    }
}
