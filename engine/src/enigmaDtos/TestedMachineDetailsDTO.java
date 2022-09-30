package enigmaDtos;
import java.util.ArrayList;
import utils.RomanNumbers;
import javafx.util.Pair;
//import utils.Pair;

public class TestedMachineDetailsDTO {

    private ArrayList<Integer> chosenValidInUseRotors = new ArrayList<>();
    private ArrayList<Character> chosenValidStartPosRotors = new ArrayList<>();
    private RomanNumbers chosenValidReflector;
    private ArrayList<Pair> chosenPlugs = new ArrayList<>();

    public ArrayList<Integer> getChosenValidInUseRotors(){
        return chosenValidInUseRotors;
    }

    public void setChosenValidInUseRotors(ArrayList<Integer> validInUseRotors) {
        for(Integer value : validInUseRotors)
            this.chosenValidInUseRotors.add(new Integer(value));
    }

    public ArrayList<Character> getChosenValidStartPosRotors() {
        return chosenValidStartPosRotors;
    }

    public void setChosenValidStartPosRotors(ArrayList<Character> chosenValidStartPosRotors) {
        for (Character value : chosenValidStartPosRotors) {
            this.chosenValidStartPosRotors.add(new Character(value));
        }
    }

    public RomanNumbers getChosenValidReflector() {
        return chosenValidReflector;
    }

    public void setChosenValidReflector(RomanNumbers chosenValidReflector) {
        this.chosenValidReflector = chosenValidReflector;
    }

    public ArrayList<Pair> getChosenValidPlugs() {
        return chosenPlugs;
    }

    public void setChosenPlugs(ArrayList<Pair> chosenPlugs) {
        for(Pair value : chosenPlugs) {
            this.chosenPlugs.add(value);
        }
    }
}



