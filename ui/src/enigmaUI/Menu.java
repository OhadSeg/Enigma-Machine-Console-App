package enigmaUI;

import UiUtils.MenuOption;
import exceptions.UserInputException;

import java.util.InputMismatchException;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class Menu {

    private boolean ifGotMachineDetails;
    private boolean ifReadXML;

    private boolean isValidMenuSelection;
    private boolean ifExit;
    private MenuOption menuOptionChoice = MenuOption.READFILE;

    private void printMenu() {
        StringBuilder printMenu = new StringBuilder();
        printMenu.append(System.lineSeparator());
        printMenu.append("Please Choose an action by entering 1-8 :");
        printMenu.append(System.lineSeparator());
        printMenu.append("===================================");
        printMenu.append(System.lineSeparator());
        printMenu.append("1. Get Machine details from file");
        printMenu.append(System.lineSeparator());
        printMenu.append("2. Present Machine details");
        printMenu.append(System.lineSeparator());
        printMenu.append("3. Enter Machine details manually");
        printMenu.append(System.lineSeparator());
        printMenu.append("4. Get machine details randomly");
        printMenu.append(System.lineSeparator());
        printMenu.append("5. Get Encrypted or Decrypted code");
        printMenu.append(System.lineSeparator());
        printMenu.append("6. Reset current code");
        printMenu.append(System.lineSeparator());
        printMenu.append("7. Print history and stats");
        printMenu.append(System.lineSeparator());
        printMenu.append("8. Exit");
        printMenu.append(System.lineSeparator());
        printMenu.append("===================================");
        System.out.println(printMenu);
    }

    protected void activateMenu() {
        int userChoice = 0;
        do {
            printMenu();
            try {
                isValidMenuSelection = false;
                Scanner in = new Scanner(System.in);
                userChoice = in.nextInt();
                ifValidInput(userChoice);
            } catch (InputMismatchException e) {
                System.out.println("~Wrong input~");
            } catch (UserInputException e) {
                System.out.println(e.getMessage());
            }
        } while (!isValidMenuSelection);

        this.menuOptionChoice = MenuOption.values()[userChoice - 1];
    }

    private void ifValidInput(int userChoice) throws UserInputException {

        switch (userChoice) {
            case (1):
            case (8):
                isValidMenuSelection = true;
                break;
            case (5):
                if (ifReadXML && ifGotMachineDetails) {
                    isValidMenuSelection = true;
                } else if (ifReadXML) {
                    throw new UserInputException("Some of the machine details are missing, Please enter 3/4 to complete missing details.");
                } else {
                    throw new UserInputException("Enigma machine is not set, Please press 1 to enter xml path and then 3/4 to complete machine details.");
                }
                break;
            case (2):
            case (3):
            case (4):
            case (6):
            case (7):
                if (ifReadXML) {
                    isValidMenuSelection = true;
                } else {
                    throw new UserInputException("An XML file has not yet been loaded");
                }
                break;
            default:
                throw new UserInputException("~Wrong input~");
        }
    }

    protected MenuOption getMenuOptionChoice() {
        return menuOptionChoice;
    }

    protected String getInputFromUser(String message) {
        System.out.println(message);
        Scanner in = new Scanner(System.in);
        String inputFromUser = in.nextLine();
        return inputFromUser;
    }

    protected String getReflectorInUseFromUser(int numOfReflectors) {
        StringBuilder getReflectorInUseMSG = new StringBuilder();
        getReflectorInUseMSG.append("Please enter a digital number between " + 1 + " - " + numOfReflectors + " which represents a reflector number\n");
        getReflectorInUseMSG.append("1. for reflector I\n");
        getReflectorInUseMSG.append("2. for reflector II\n");
        getReflectorInUseMSG.append("3. for reflector III\n");
        getReflectorInUseMSG.append("4. for reflector IV\n");
        getReflectorInUseMSG.append("5. for reflector V\n");
        String[] lines = getReflectorInUseMSG.toString().split("\\n");
        for (int i = 0; i <= numOfReflectors; i++) {
            System.out.println(lines[i]);
        }
        Scanner in = new Scanner(System.in);
        String inputFromUser = in.nextLine();
        return inputFromUser;
    }

    protected void setIfReadXML(boolean ifReadXML) {
        this.ifReadXML = ifReadXML;
    }

    protected void setIfGotMachineDetails(boolean ifGotMachineDetails) {
        this.ifGotMachineDetails = ifGotMachineDetails;
    }

    protected void setIfExit(boolean ifExit) {
        this.ifExit = ifExit;
    }

    protected boolean isIfExit() {
        return ifExit;
    }
}
