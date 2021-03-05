package main;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Observer.Observador;

import java.awt.event.*;

import design.Buttons;
import design.Colors;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Esta clase es un Observable
 */
public class LateralPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public static ArrayList<Observador> observadores = new ArrayList<Observador>();

    // ALERTA: No cambiar las posiciones de estos elementos
    private static final String [] buttonsNames = {
        "Empleados",
        "Productos",
        "Ventas",
        "Vendedores",
        "Clientes",
        "Salir" // es necesario que esta sea la posicion 0, NO CAMBIAR ESTO
    };

    private int selectedItem = 0;

    private final ArrayList<Buttons> btns;
    private final BorderLayout layout;
    private final BoxLayout btnLayout;
    private final JPanel buttonsPanel;

    public LateralPanel(Dimension parentSize) {
        this.btns = new ArrayList<Buttons>();
        this.layout = new BorderLayout();
        this.buttonsPanel = new JPanel();
        this.btnLayout = new BoxLayout(this.buttonsPanel, BoxLayout.Y_AXIS);

        // Configuracion de los botones
        this.buttonsPanel.setLayout(this.btnLayout);
        this.buttonsPanel.setOpaque(false);
        loadButtons();  // cargando los botones del panel

        // Configuraciones del panel
        this.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        this.setBackground(Colors.SECONDARY_COLOR);
        this.setSize((int) (parentSize.getWidth()*0.3), (int) parentSize.getHeight());

        this.setLayout(this.layout);
        this.add(new JLabel("<html><body>"+
            "<h2 style=\"color: white; margin: 20px;\">"+
                "Ventas"+
            "</h2></body></html>"), BorderLayout.NORTH);
        this.add(this.buttonsPanel, BorderLayout.CENTER);

        this.buttonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        for (Buttons button : btns) {
            this.buttonsPanel.add(button);
            this.buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        notifyObservadores();
        
    }

    private void loadButtons() {
        // Creando botones
        for (String btnName : buttonsNames) {
            this.btns.add(new Buttons(btnName));
        }

        // Evento de salir
        for (Buttons button : btns) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(this.event);
        }
    }

    private ActionListener event = new ActionListener() {

        public void actionPerformed (ActionEvent e){

            if(e.getActionCommand() == buttonsNames[buttonsNames.length - 1]) {
                System.exit(0);
            }
            
            selectedItem = stringToInteger(e.getActionCommand());
            resetButtons();
            notifyObservadores();
        }

    };

    public void resetButtons() {
        for (Buttons buttons : btns) {
            buttons.setBackground(Colors.BUTTON_COLOR);
        }
    }

    public void notifyObservadores() {
        btns.get(this.selectedItem).setBackground(Color.white);

        for (Observador observador : observadores) {
            observador.notifyAction(this.selectedItem);
        }

        btns.get(this.selectedItem).updateUI();
    }

    private int stringToInteger(String name) {
        switch(name) {
            case "Empleados": return 0;
            case "Productos": return 1;
            case "Ventas": return 2;
            case "Vendedores": return 3;
            case "Clientes": return 4;
            case "Salir":
            default: return 5;
        }
    }
}
