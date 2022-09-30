package operation;

import components.Enigma;
import exceptions.MachineDetailsFromFileException;
import exceptions.MachineDetailsFromUserException;
import exceptions.ReadFileException;
import exceptions.UserInputException;
import generated.CTEEnigma;
import javafx.util.Pair;
//import utils.Pair;
import utils.RomanNumbers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import enigmaDtos.MachineDetailsDTO;
import enigmaDtos.TestedMachineDetailsDTO;
import enigmaDtos.MachineExecutionDocumentationDTO;
public class Operator {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";
    private Enigma enigmaTest;
    private Enigma enigma;
    private String xmlPath;
    private Tests tests = new Tests();
    private MachineDetailsDTO machineDetailsDTO;
    private int amountOfCodedMessages;
    private ArrayList<MachineExecutionDocumentationDTO> machineExeStatsAndHistory = new ArrayList<>();
    private Random rnd = new Random();

    public void setXmlPath(String inputXmlPath) throws MachineDetailsFromFileException, ReadFileException {
        xmlPath = new String(inputXmlPath);
        setEnigma();
    }

    private void setEnigma() throws MachineDetailsFromFileException, ReadFileException {
        CTEEnigma enigma123;
        try {
            ifPathValid();
            InputStream inputStream = new FileInputStream(new File(xmlPath));
            enigma123 = desrializeFrom(inputStream);
            enigmaTest = new Enigma(enigma123);
            tests = new Tests();
            tests.runEnigmaSetTests(enigmaTest);
            if (tests.getEnigmaSetValid()) {
                enigma = new Enigma(enigma123);
            }
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ifPathValid() throws ReadFileException {
        if (!xmlPath.endsWith(".xml")) {
            throw new ReadFileException("ERROR: The path is not a XML file path type");
        }
        Path path = Paths.get(xmlPath);
        if (Files.notExists(path)) {
            throw new ReadFileException(("ERROR: file does not exist"));
        }
    }

    private static CTEEnigma desrializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (CTEEnigma) u.unmarshal(in);
    }

    public void setMachineDetails(MachineDetailsDTO dtoFromUI) throws MachineDetailsFromUserException {

        this.machineDetailsDTO = dtoFromUI;
        TestedMachineDetailsDTO dtoFilteredToEnigma = new TestedMachineDetailsDTO();
        tests.runMachineDetailsTests(enigma, machineDetailsDTO, dtoFilteredToEnigma);
        enigma.setEnigmaMachine(dtoFilteredToEnigma);
        amountOfCodedMessages = 0;
        MachineExecutionDocumentationDTO newMachineExecution = new MachineExecutionDocumentationDTO(enigma.toString());
        this.machineExeStatsAndHistory.add(newMachineExecution);
    }

    public String setTextToCode(String textToCode) throws UserInputException {
        String textToCodeAfterUpper = new String(textToCode.toUpperCase());
        tests.checkIfTextToCodeFromUserValid(textToCodeAfterUpper, enigma);
        Long timeExecute = null;
        Pair codedTextAndTime = enigma.execute(textToCodeAfterUpper);
        amountOfCodedMessages++;
        Pair beforeAndAfterText = new Pair(textToCodeAfterUpper, codedTextAndTime.getKey().toString());
        Pair cryptographicHistoryDataUnit = new Pair(beforeAndAfterText, codedTextAndTime.getValue());
        this.machineExeStatsAndHistory.get(machineExeStatsAndHistory.size() - 1).addNewCodingDataUnit(cryptographicHistoryDataUnit);
        return codedTextAndTime.getKey().toString();
    }

    public int getNumOfReflectorsFromEnigma() {
        return enigma.getAmountOfAllReflectors();
    }

    public void resetEnigma() {
        enigma.reset();
    }

    public void randomMode() {
        TestedMachineDetailsDTO randomMachineDetailsDTO = new TestedMachineDetailsDTO();
        randomMachineDetailsDTO.setChosenValidInUseRotors(randomInUseRotors());
        randomMachineDetailsDTO.setChosenValidStartPosRotors(randomStartingPos());
        randomMachineDetailsDTO.setChosenValidReflector(randomReflectorInUse());
        randomMachineDetailsDTO.setChosenPlugs(randomPlugBoard());

        enigma.setEnigmaMachine(randomMachineDetailsDTO);
        amountOfCodedMessages = 0;
        MachineExecutionDocumentationDTO newMachineExecution = new MachineExecutionDocumentationDTO(enigma.toString());
        this.machineExeStatsAndHistory.add(newMachineExecution);
    }

    private ArrayList<Integer> randomInUseRotors() {
        ArrayList<Integer> randomInUseToReturn = new ArrayList<>();
        ArrayList<Integer> randomFrom = new ArrayList<>();
        for (int i = 1; i <= enigma.getAmountOfAllRotors(); i++) {
            randomFrom.add(i);
        }
        Collections.shuffle(randomFrom);
        for (int i = 0; i < enigma.getAmountOfRotorsInUse(); i++) {
            randomInUseToReturn.add(randomFrom.get(i));
        }
        return randomInUseToReturn;
    }

    private ArrayList<Character> randomStartingPos() {
        ArrayList<Character> startingPosToReturn = new ArrayList<>();
        ArrayList<Character> randomFrom = settingAbcToRandom();
        for (int i = 0; i < enigma.getAmountOfRotorsInUse(); i++) {
            startingPosToReturn.add(randomFrom.get(i));
        }
        return startingPosToReturn;
    }

    private ArrayList<Character> settingAbcToRandom() {
        ArrayList<Character> randomFrom = new ArrayList<>();
        String abcStr = enigma.getAbc();
        for (int i = 0; i < abcStr.length(); i++) {
            randomFrom.add(abcStr.charAt(i));
        }
        Collections.shuffle(randomFrom);
        return randomFrom;
    }

    private RomanNumbers randomReflectorInUse() {
        int randInt = rnd.nextInt(enigma.getAmountOfAllReflectors());
        return RomanNumbers.values()[randInt];
    }

    private ArrayList<Pair> randomPlugBoard() {

        int rangeAmountOfPlugs = enigma.getAbc().length() / 2;
        int AmountOfPlugs = rnd.nextInt(rangeAmountOfPlugs + 1);
        ArrayList<Pair> plugBoardToReturn = new ArrayList<>();
        ArrayList<Character> randomFrom = settingAbcToRandom();
        for (int i = 0; i < AmountOfPlugs; i++) {
            plugBoardToReturn.add(new Pair(randomFrom.get(i), randomFrom.get(randomFrom.size() - 1 - i)));
        }
        return plugBoardToReturn;
    }

    public String getMachineStats() {

        StringBuilder allStats = new StringBuilder();
        for (MachineExecutionDocumentationDTO dtoValue : machineExeStatsAndHistory) {
            allStats.append(dtoValue.toString());
            allStats.append(System.lineSeparator());
        }
        return (allStats.toString());
    }

    public String getMachineDetails() {

        StringBuilder allDetails = new StringBuilder();
        allDetails.append("1. Possible amount of rotors / Amount of in-use rotors: ");
        allDetails.append(enigma.getAmountOfAllRotors() + " / " + enigma.getAmountOfRotorsInUse());
        allDetails.append(System.lineSeparator());
        allDetails.append("2. Amount of reflectors: " + enigma.getAmountOfAllReflectors());
        allDetails.append(System.lineSeparator());
        allDetails.append("Amount of messages coded by the machine: " + this.amountOfCodedMessages);
        allDetails.append(System.lineSeparator());
        allDetails.append("Original Configuration: ");
        if (this.machineExeStatsAndHistory.size() != 0) {
            allDetails.append(machineExeStatsAndHistory.get(0).getConfiguration());
        } else {
            allDetails.append("----/----");
        }
        allDetails.append(System.lineSeparator());
        allDetails.append("Current Configuration: ");
        if (this.machineExeStatsAndHistory.size() != 0) {
            allDetails.append(machineExeStatsAndHistory.get(machineExeStatsAndHistory.size() - 1).getConfiguration());
        } else {
            allDetails.append(" ----/----");
        }
        allDetails.append(System.lineSeparator());
        return allDetails.toString();
    }
}