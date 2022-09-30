package components;

import generated.CTEEnigma;
import enigmaDtos.TestedMachineDetailsDTO;
import javafx.util.Pair;

import java.util.ArrayList;

public class Enigma {

    private Machine machine;

    public Enigma(CTEEnigma cteEnigma) {
        this.machine = new Machine(cteEnigma.getCTEMachine());
    }

    public Machine getMachine() {
        return machine;
    }


    public void setEnigmaMachine(TestedMachineDetailsDTO machineDetails) {
        machine.insertMachineDetails(machineDetails);
    }

    public void reset() {
        machine.reset();
    }

    public ArrayList<Integer> getAllRotorsNotches() {
        return machine.getAllRotorsNotches();
    }

    public Pair execute(String textToCode) {
        long begin = System.nanoTime();
        String textAfterCode = machine.startCodeText(textToCode);
        long end = System.nanoTime();
        Long timeExecute = new Long(end - begin);
        Pair codedTextAndTime = new Pair<>(textAfterCode, timeExecute);

        return codedTextAndTime;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public int getAmountOfAllReflectors() {
        return machine.getAmountOfAllReflectors();
    }

    public int getAmountOfAllRotors() {
        return machine.getAmountOfAllRotors();
    }

    public int getAmountOfRotorsInUse() {
        return machine.getCountRotors();
    }

    public int getAmountOfAllRotorsInXml() {
        return machine.getAmountOfAllRotorsInXml();
    }

    public String getAbc() {
        return machine.getAbc();
    }

    public boolean checkIfAllReflectorsValid() {
        return machine.checkIfAllReflectorsValid();
    }

    @Override
    public String toString() {
        return machine.toString();
    }
}
