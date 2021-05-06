package model.card;

public enum Status {
    LIMITED("Limited"),
    UNLIMITED("Unlimited");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
