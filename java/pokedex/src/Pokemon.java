public class Pokemon {
    private int pokedexNumber;
    private String name;
    private String type1;
    private String type2;

    public Pokemon(int pokedexNumber, String name, String type1, String type2) {
        this.pokedexNumber = pokedexNumber;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public String getName() {
        return name;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }
}
