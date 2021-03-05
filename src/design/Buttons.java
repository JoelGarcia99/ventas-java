package design;

import javax.swing.JButton;

public class Buttons extends JButton {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Buttons(String title) {

        // configuracion del boton
        this.setText(title);
        this.setForeground(Colors.BUTTON_TEXT_COLOR);
        this.setBackground(Colors.BUTTON_COLOR);
        this.setHorizontalAlignment(JButton.CENTER); 
        // this.setMargin(new Insets(15, 5, 15, 5));
    }

}
