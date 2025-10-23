package familytree;

public class Marriage {
    private Person spouse;
    private int marriageYear;
    private Integer divorceYear;

    public Marriage(Person spouse, int marriageYear) {
        this.spouse = spouse;
        this.marriageYear = marriageYear;
        this.divorceYear = null;
    }

    public Person getSpouse() {
        return spouse;
    }

    public int getMarriageYear() {
        return marriageYear;
    }

    public Integer getDivorceYear() {
        return divorceYear;
    }

    public void setDivorceYear(Integer divorceYear) {
        this.divorceYear = divorceYear;
    }
}