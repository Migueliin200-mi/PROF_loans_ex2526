package es.upm.grise.profundizacion;

public class Applicant {
    private final int monthlyIncome;
    private final int creditScore;
    private final boolean hasRecentDefaults;
    private final boolean isVip;

    public Applicant(int monthlyIncome, int creditScore, boolean hasRecentDefaults, boolean isVip) {
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
        this.hasRecentDefaults = hasRecentDefaults;
        this.isVip = isVip;
    }

    public int monthlyIncome() { return monthlyIncome; }
    public int creditScore() { return creditScore; }
    public boolean hasRecentDefaults() { return hasRecentDefaults; }
    public boolean isVip() { return isVip; }
}
