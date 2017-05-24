/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.resttest.model.single;

import se.nackademin.resttest.model.Loan;

/**
 *
 * @author daniel
 */
public class SingleLoan {
    private Loan loan;

    public SingleLoan(Loan loan) {
        this.loan = loan;
    }

    /**
     * @return the loan
     */
    public Loan getLoan() {
        return loan;
    }

    /**
     * @param loan the loan to set
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
    
}
