package administration;

import java.util.ArrayList;

import java.util.Scanner;

import administration.Housetypes.*;

import administration.Exceptions.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Streetmanager {

    Scanner s = new Scanner(System.in);

    private ArrayList<Street> streetList = new ArrayList<Street>();

    int year = 2000;
    int properties;

    public Streetmanager(){

        /**
         * Sets up the Frame and Layout of the GUI.
         */
        JFrame frame = new JFrame("Streetmanager");
        JPanel panel = new JPanel();
        JButton nextYearButton = new JButton("Next Year");
        JLabel yearLabel = new JLabel(String.format("Current Year: %d", year));

        JTabbedPane streetTabs = new JTabbedPane();   

        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BorderLayout());
        panel.add(nextYearButton, BorderLayout.WEST);
        panel.add(yearLabel,BorderLayout.NORTH);
        panel.add(streetTabs,BorderLayout.CENTER);

        /**
         * Sets up the Streets and Properties.
         */
        String stringNumberOfStreets = JOptionPane.showInputDialog("How many Streets do you want to manage?");
        int numberOfStreets = Integer.parseInt(stringNumberOfStreets);
        for(int i = 0; i< numberOfStreets;i++){
            JPanel streetPanel = new JPanel();
            String streetname = String.format("Street %d", i+1);
            streetTabs.addTab(streetname, streetPanel);

            String stringNumberOfProperties = JOptionPane.showInputDialog(String.format("How many properties should Street %d have?", i+1));
            int NumberOfProperties = Integer.parseInt(stringNumberOfProperties);
            Street street = new Street(NumberOfProperties);
            streetList.add(street);

            for(int j =0; j<NumberOfProperties;j++){
                JPanel property = new JPanel();
                streetPanel.add(property);
                property.setBorder(BorderFactory.createLineBorder(Color.black));
                property.add(new JLabel());
                JSpinner spinner = new JSpinner(new SpinnerNumberModel());
                property.add(spinner);
                spinner.addChangeListener(new ChangeListener(){                
                    public void stateChanged(ChangeEvent e) {
                    int spinvalue = (int) spinner.getValue();
                    }
                });
            }
        }

        nextYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < streetList.size();){
                    loop(streetList.get(i));
                }
            }
        });

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void loop(Street street) {

        int emptyProperty = -1; // "houseNumber" of an empty property

        try{
            for (int houseNumber = 0; houseNumber < street.getStreetSize(); houseNumber++) {
                House house = street.getHouses()[houseNumber];
                if (house != null) {
                    System.out.println(String.format("New occupied flats for house: %d? \n(current occ. flats: %d)",
                            houseNumber, house.getOccupiedFlats()));
                    String input = s.nextLine();

                    int occupiedFlats = Integer.parseInt(input);
                    street.getHouses()[houseNumber].setOccupiedFlats(occupiedFlats);
                } else {
                    emptyProperty = houseNumber;
                }
            }
        }
        catch(NumberFormatException bruh){
            JOptionPane.showMessageDialog(null,"Please enter a valid number. Year restarts now.");
            loop(street);
        }

        /**
         * Wrecks a house if its empty and 5 years or older.
         */
        for (int houseNumber = 0; houseNumber < street.getStreetSize(); houseNumber++) {
            House house = street.getHouses()[houseNumber];
            if (house != null && house.checkWreck(year)) {
                street.getHouses()[houseNumber] = null;
                JOptionPane.showMessageDialog(null, "House got wrecked :(");
            }
        }

        /**
         * Checks if new Houses should be built.
         */
        boolean buildNewHouse = true;
        for (int houseNumber = 0; houseNumber < street.getStreetSize(); houseNumber++) {
            House house = street.getHouses()[houseNumber];
            if (house != null && !house.checkFullHouse()) {
                buildNewHouse = false;
            }
        }

        /**
         * Builds new House every year if theres an empty property and every house is
         * full.
         */
        if (buildNewHouse) {
            if (emptyProperty == -1) {
                JOptionPane.showMessageDialog(null, "there is no empty property");
            } 
            else {
                String houseType = JOptionPane.showInputDialog(null, "Would you like to build 'One family house' or a 'Apartmentcomplex'?");
                try{
                if (houseType.equals("One family house")) {
                    street.buildHouse(emptyProperty, new SingleHouse(1, year));
                    System.out.println(String.format("Build house at: %d", emptyProperty));
                } 
                else if (houseType.equals("Apartmentcomplex")) {
                    String flatnumber = JOptionPane.showInputDialog(null, "How many flats?");
                    int complexFlatNumber = Integer.parseInt(flatnumber);
                    street.buildHouse(emptyProperty, new ApartmentComplex(complexFlatNumber, year));
                    System.out.println(String.format("Build Apartmentcomplex at: %d", emptyProperty));
                }
                else{
                    NoHousetype noHousetype = new NoHousetype();
                    throw noHousetype;
                }
                }
                catch(NoHousetype noHousetype){
                    JOptionPane.showMessageDialog(null,"Please insert an available Housetype. Year restarts now.");
                    year--;
                }
            }
        }
        
        year++;
        
        if(year == 2021) {
            JOptionPane.showMessageDialog(null,"Humanity got erradicated by the so called Coronavirus. All flats are empty.");
            System.exit(0);
        }
    }
}