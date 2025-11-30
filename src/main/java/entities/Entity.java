package entities;

import lombok.Getter;
import lombok.Setter;

import static utils.MagicNumber.D_100;
import static utils.MagicNumber.ONE_HUNDRED;

@Getter
@Setter
public abstract class Entity {
    // Coordonates for efficient environment update
    protected int x;
    protected int y;
    // Common attributes for all entities
    protected String name;
    protected double mass;
    protected String type;
    // Nu am invatat pana acum 2 zile la curs cum se face Double dispatch asa ca am aceste
    // variabile pentru a evita folosirea lui instaceof, nu mai am timp sa modific
    protected boolean isWater = false;
    protected boolean isPlant = false;
    protected boolean isAnimal = false;

    // Tinem minte la ce moment a fost scanata pentru a putea face update la env
    // Adica ii putem calcula varsta si sa decidem dupa ce actiuni va face
    protected int scannedTimestamp = -1;

    public Entity(final String name, final double mass, final String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
    /**
     * Functie pentru normalizarea si rotunjirea unei valori double
     */
    protected double normalizedAndRounded(final double score) {
        return Math.round(Math.max(0, Math.min(ONE_HUNDRED, score)) * D_100) / D_100;
    }
    /**
     * Verificam daca este scanata
     */
    public boolean isScanned() {
        return scannedTimestamp != -1;
    }
    /**
     * Functie de improve env mostenita de fiecare tip de entitate, asa ca acesta
     * nu va intoarce niciodata ""
     */
    public String improveEnvironment(final map.Box box, final String improvementType) {
        return "";
    }

}
