package enigmaUI;

import UiUtils.MenuOption;
import exceptions.MachineDetailsFromFileException;
import exceptions.MachineDetailsFromUserException;
import exceptions.ReadFileException;
import exceptions.UserInputException;
import operation.Operator;
import enigmaDtos.MachineDetailsDTO;
import java.util.Scanner;

public class ConsoleUI {

    private Menu menu = new Menu();
    private Operator operator = new Operator();

    protected void activeSystem() {
        while (!menu.isIfExit()) {
            flow();
        }
        System.out.println("You've logged out");
    }

    private void flow() {
        menu.activateMenu();
        MenuOption menuOption = menu.getMenuOptionChoice();
        switch (menuOption) {
            case READFILE:
                doReadXmlFile();
                break;
            case PRESENTMACHINEDETAILS:
                doPresentMachineDetails();
                break;
            case ENTERDETAILS:
                doEnterMachineDetailsManually();
                break;
            case GETRANDOMDETAILS:
                doGetRandomMachineDetails();
                break;
            case RUNCODE:
                doRunCode();
                break;
            case RESETCODE:
                doResetCode();
                break;
            case PRINTHISTORYANDSTATS:
                doPrintHistoryAndStats();
                break;
            case EXIT:
                doExit();
                break;
        }
    }

    private void doReadXmlFile() {
        System.out.println("Enter the XML path: ");
        Scanner in = new Scanner(System.in);
        String XmlPathInput = in.nextLine();
        try {
            operator.setXmlPath(XmlPathInput);
            menu.setIfReadXML(true);
            menu.setIfGotMachineDetails(false);
            System.out.println("The operation completed successfully");
        } catch (MachineDetailsFromFileException | ReadFileException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doPresentMachineDetails() {
        System.out.println("Machine Details");
        System.out.println(System.lineSeparator());
        String allDetails = operator.getMachineDetails();
        System.out.println(allDetails);
    }

    private void doEnterMachineDetailsManually() {
        String rotorsInUse = menu.getInputFromUser("Please enter a string of digital numbers each one divided by comas: ");
        String rotorsInitialPositions = menu.getInputFromUser("Please enter a string of rotors initial positions: ");
        String reflectorInUse = menu.getReflectorInUseFromUser(operator.getNumOfReflectorsFromEnigma());
        String plugsBoardInUse = menu.getInputFromUser("Please enter the plugs in use: ");
        MachineDetailsDTO machineDetailsDto = new MachineDetailsDTO(rotorsInUse, rotorsInitialPositions, reflectorInUse, plugsBoardInUse);
        try {
            operator.setMachineDetails(machineDetailsDto);
            menu.setIfGotMachineDetails(true);
            System.out.println("The operation completed successfully");
        } catch (MachineDetailsFromUserException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doGetRandomMachineDetails() {
        operator.randomMode();
        menu.setIfGotMachineDetails(true);
        System.out.println("The machine initialized with random details successfully");
    }

    private void doRunCode() {
        String textToCode = menu.getInputFromUser("Enter the text you want to code: ");
        try {
            String outputStr = operator.setTextToCode(textToCode);
            System.out.println("Output: " + outputStr);
        } catch (UserInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void doResetCode() {
        operator.resetEnigma();
        System.out.println("The machine reset successfully");
    }

    private void doPrintHistoryAndStats() {
        System.out.println("Machine History & Statistics");
        System.out.println(System.lineSeparator());
        String allStats = operator.getMachineStats();
        System.out.println(allStats);
    }

    private void doExit() {
        menu.setIfExit(true);
    }
}