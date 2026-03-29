
package com.parkinglot.domain;

public abstract class Payment {
    private final double fee;
    private final PaymentType type;

    public Payment(double fee, PaymentType type) {
        this.fee = fee;
        this.type = type;
    }

    public static Payment create(PaymentType type, double fee) {
        return switch (type) {
            case CARD -> new CardPayment(fee);
            case CASH -> new CashPayment(fee);
        };
    }

    /** Runs domain-specific payment logic (card vs cash). */
    public void pay() {
        makePayment();
    }

    public double getFee() {
        return fee;
    }

    public PaymentType getType() {
        return type;
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