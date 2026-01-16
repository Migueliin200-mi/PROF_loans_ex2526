package es.upm.grise.profundizacion;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import es.upm.grise.profundizacion.LoanApprovalService.Decision;

public class LoanApprovalServiceTest {
    private LoanApprovalService service;

    @Before
    public void setUp() {
        service = new LoanApprovalService();
    }

    // CAMINO 1: score < 500 → REJECTED
    @Test
    public void testCamino1_scoreBajo() {
        Applicant applicant = new Applicant(3000, 400, false, false);
        Decision result = service.evaluateLoan(applicant, 10000, 12);
        assertEquals(Decision.REJECTED, result);
    }

    // CAMINO 2: score 500-649, income<2500 → REJECTED
    @Test
    public void testCamino2_scoreMedioBajoIncome() {
        Applicant applicant = new Applicant(2000, 550, false, false);  // income < 2500
        Decision result = service.evaluateLoan(applicant, 10000, 12);
        assertEquals(Decision.REJECTED, result);
    }

    // CAMINO 3: score 500-649, incomeOK, NO VIP → MANUAL_REVIEW
    @Test
    public void testCamino3_scoreMedioVIPno() {
        Applicant applicant = new Applicant(3000, 600, false, false);  // NO VIP
        Decision result = service.evaluateLoan(applicant, 10000, 12);
        assertEquals(Decision.MANUAL_REVIEW, result);
    }

    // CAMINO 4: score >=650, amount alto → MANUAL_REVIEW
    @Test
    public void testCamino4_scoreAltoAmountAlto() {
        Applicant applicant = new Applicant(3000, 700, false, false);
        Decision result = service.evaluateLoan(applicant, 30000, 12);  // > income*8=24000
        assertEquals(Decision.MANUAL_REVIEW, result);
    }

    // BONUS: CAMINO VIP (elevación a APPROVED)
    @Test
    public void testCaminoBonus_VIP_elevacion() {
        Applicant applicant = new Applicant(3000, 620, false, true);   // VIP + score>=600
        Decision result = service.evaluateLoan(applicant, 30000, 12);
        assertEquals(Decision.APPROVED, result);  // Elevado desde MANUAL_REVIEW
    }
}
