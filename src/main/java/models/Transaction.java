package models;

import java.sql.Timestamp;

public class Transaction {
    private User client;
    private String id;
    private Timestamp transactionDate;
    private String card;
    private String account;
    private Timestamp accountValidTo;

    private String operationType;
    private String amount; // TODO: change type to Number
    private String operationResult;
    private String terminal;
    private String terminalType;
    private String city;
    private String address;

    public Transaction(User client, String id, Timestamp transactionDate, String card, String account, String operationType,
                       String amount, String operationResult, String terminal, String terminalType, String city, String address) {
        this.client = client;
        this.id = id;
        this.transactionDate = transactionDate;
        this.card = card;
        this.account = client.getClientId();
        this.operationType = operationType;
        this.amount = amount;
        this.operationResult = operationResult;
        this.terminal = terminal;
        this.terminalType = terminalType;
        this.city = city;
        this.address =address;
    }

    public String getAccount() {
        return account;
    }

    public String getAddress() {
        return address;
    }

    public String getAmount() {
        return amount;
    }

    public String getCard() {
        return card;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public User getClient() {
        return client;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
}
