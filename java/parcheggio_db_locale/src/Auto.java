public class Auto {
    private String targa;
    private String marca;
    private String modello;

    public Auto(String targa, String marca, String modello) {
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
    }

    public String getTarga() {
        return targa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    @Override
    public String toString() {
        return "Targa: " + targa + ", Marca: " + marca + ", Modello: " + modello;
    }
}