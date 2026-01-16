package es.upm.grise.profundizacion;

import java.util.Objects;

public class LoanApprovalService {

    /**
     * Método X: contiene decisiones encadenadas y compuestas para análisis estructural.
     *
     * Regla (simplificada):
     * - Entradas inválidas -> excepción
     * - Score < 500 -> REJECTED
     * - 500..649 -> si income alto y no tiene impagos -> MANUAL_REVIEW; si no -> REJECTED
     * - >= 650 -> si amount <= income*8 -> APPROVED; si no -> MANUAL_REVIEW
     * - Además, si el cliente es VIP y score>=600 y no tiene impagos -> se eleva a APPROVED si estaba en MANUAL_REVIEW
     */
public Decision evaluateLoan(Applicant applicant, int amountRequested, int termMonths) {
    // NODO 1: Entrada validate()
    validate(applicant, amountRequested, termMonths);
    
    // NODO 2: Extraer datos
    int score = applicant.creditScore();
    boolean hasDefaults = applicant.hasRecentDefaults();
    int income = applicant.monthlyIncome();
    
    // NODO 3: Inicializar decision
    Decision decision;
    
    // NODO 4: score < 500?
    if (score < 500) {
        // NODO 5: REJECTED directo
        decision = Decision.REJECTED;
    } 
    // NODO 6: else if score < 650?
    else if (score < 650) {
        // NODO 7: income >= 2500 && !hasDefaults?
        if (income >= 2500 && !hasDefaults) {
            // NODO 8: MANUAL_REVIEW
            decision = Decision.MANUAL_REVIEW;
        } else {
            // NODO 9: REJECTED
            decision = Decision.REJECTED;
        }
    } 
    // NODO 10: else (score >= 650)
    else {
        // NODO 11: amountRequested <= income * 8?
        if (amountRequested <= income * 8) {
            // NODO 12: APPROVED
            decision = Decision.APPROVED;
        } else {
            // NODO 13: MANUAL_REVIEW
            decision = Decision.MANUAL_REVIEW;
        }
    }
    
    // NODO 14: if MANUAL_REVIEW && VIP && score>=600 && !hasDefaults?
    if (decision == Decision.MANUAL_REVIEW
        && applicant.isVip()
        && score >= 600
        && !hasDefaults) {
        // NODO 15: Eleva a APPROVED
        decision = Decision.APPROVED;
    }
    
    // NODO 16: Salida return decision
    return decision;
}


    private void validate(Applicant applicant, int amountRequested, int termMonths) {
        Objects.requireNonNull(applicant, "applicant cannot be null");
        if (amountRequested <= 0) {
            throw new IllegalArgumentException("amountRequested must be > 0");
        }
        if (termMonths < 6 || termMonths > 84) {
            throw new IllegalArgumentException("termMonths must be between 6 and 84");
        }
        if (applicant.monthlyIncome() <= 0) {
            throw new IllegalArgumentException("monthlyIncome must be > 0");
        }
        if (applicant.creditScore() < 0 || applicant.creditScore() > 850) {
            throw new IllegalArgumentException("creditScore must be between 0 and 850");
        }
    }

    public enum Decision {
        APPROVED, MANUAL_REVIEW, REJECTED
    }

}

