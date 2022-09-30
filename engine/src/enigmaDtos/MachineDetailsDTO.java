package enigmaDtos;

import components.Machine;

public class MachineDetailsDTO {
    String rotorsInUse;
    String rotorsInitialPositions;
    String reflectorInUse;
    String plugsBoardInUse;

    public MachineDetailsDTO(String rotorsFromUser, String rotorsInitalPosFromUser, String reflectorFromUser, String plugBoardFromUser) {
        this.rotorsInUse = new String(rotorsFromUser.toUpperCase());
        this.rotorsInitialPositions = new String(rotorsInitalPosFromUser.toUpperCase());
        this.reflectorInUse = new String(reflectorFromUser.toUpperCase());
        this.plugsBoardInUse = new String(plugBoardFromUser.toUpperCase());
    }

    public String getRotorsInUse() {
        return rotorsInUse;
    }

    public String getRotorsInitialPositions() {
        return rotorsInitialPositions;
    }

    public String getReflectorInUse() {
        return reflectorInUse;
    }

    public String getPlugsBoardInUse() {
        return plugsBoardInUse;
    }
}
