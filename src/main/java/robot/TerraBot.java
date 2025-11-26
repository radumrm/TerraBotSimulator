package robot;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerraBot {
    private int x;
    private int y;
    private int energy;

    public TerraBot(int energy) {
        this.x = 0;
        this.y = 0;
        this.energy = energy;
    }
}