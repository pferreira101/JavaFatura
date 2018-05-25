import java.io.Serializable;

public enum Distritos implements Serializable {

    AVEIRO("Aveiro"),
    BEJA("Beja"),
    BRAGA("Braga"),
    BRAGANCA("Bragança"),
    CASTELOBRANCO("Castelo Branco"),
    COIMBRA("Coimbra"),
    EVORA("Évora"),
    FARO("Faro"),
    GUARDA("Guarda"),
    LEIRIA("Leiria"),
    LISBOA("Lisboa"),
    PORTALEGRE("Portalegre"),
    PORTO("Porto"),
    SANTAREM("Santarém"),
    SETUBAL("Setúbal"),
    VIANACASTELO("Viana do Castelo"),
    VILAREAL("Vila Real"),
    VISEU("Viseu");

    private final String nome;

    private Distritos(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}