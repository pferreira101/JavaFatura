import java.io.Serializable;

public enum Distrito implements Serializable {

    AVEIRO("Aveiro", false, 0),
    BEJA("Beja", true, 0.05),
    BRAGA("Braga", false, 0),
    BRAGANCA("Bragança", true, 0.05),
    CASTELOBRANCO("Castelo Branco", true, 0.05),
    COIMBRA("Coimbra", false, 0),
    EVORA("Évora", true, 0.05),
    FARO("Faro", false, 0),
    GUARDA("Guarda", true, 0.05),
    LEIRIA("Leiria", false, 0),
    LISBOA("Lisboa", false, 0),
    PORTALEGRE("Portalegre", true, 0.05),
    PORTO("Porto", false, 0),
    SANTAREM("Santarém", true, 0.05),
    SETUBAL("Setúbal", false, 0),
    VIANACASTELO("Viana do Castelo", false, 0),
    VILAREAL("Vila Real", true, 0.05),
    VISEU("Viseu", true, 0.05);

    private final String nome;
    private final boolean interior;
    private final double taxa;

    private Distrito(String nome, boolean interior, double taxa) {
        this.nome = nome;
        this.interior = interior;
        this.taxa = taxa;
    }

    public String getNome() {
        return nome;
    }

    public boolean isInterior() {
        return interior;
    }

    public double getTaxa() {
        return taxa;
    }
}