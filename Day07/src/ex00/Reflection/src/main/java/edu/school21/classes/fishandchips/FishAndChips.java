package edu.school21.classes.fishandchips;

import java.util.StringJoiner;

public class FishAndChips {
    private String fish;
    private String chips;
    private int addedSouse;

    public FishAndChips() {
        this.fish = "Default fish";
        this.chips = "Default chips";
        this.addedSouse = 0;
    }

    public FishAndChips(String fish, String chips, int addedSouse) {
        this.fish = fish;
        this.chips = chips;
        this.addedSouse = addedSouse;
    }

    public void addSouse(boolean add) {
        if (add) {
            this.addedSouse += addedSouse;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FishAndChips.class.getSimpleName() + "[", "]")
                .add("fish='" + fish + "'")
                .add("chips='" + chips + "'")
                .add("addedSouse=" + addedSouse)
                .toString();
    }
}
