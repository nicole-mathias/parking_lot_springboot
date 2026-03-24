
package com.parkinglot.domain;

public abstract class Payment {
    private double fee;
    private PaymentType type;

    public Payment(double fee, PaymentType type){
        this.fee = fee;
        this.type = type;
    }

    protected abstract void makePayment();
}

class CardPayment extends Payment {
    public CardPayment(double fee) {
        super(fee, PaymentType.CARD);
    }

    // add Card payment specific logic here, and en extra fee can also be charged for using a card
    @Override
    protected void makePayment() {
        System.out.println("Insert Card");
        System.out.println("Processed Payment");
    }
}


class CashPayment extends Payment {
    public CashPayment(double fee) {
        super(fee, PaymentType.CASH);
    }

    // add Card payment specific logic here, and en extra fee can also be charged for using a card
    @Override
    protected void makePayment() {
        System.out.println("Insert cash");
        System.out.println("Cash Accepted");
    }
}