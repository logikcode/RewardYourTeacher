package com.decagon.rewardyourteacherapi.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * Created by SQ-OGBE PC on 22/09/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Data
public  class Data {
    /**
     * the amount for a transaction
     */
    private BigDecimal amount;
    /**
     * the currency for the transaction
     */
    private String currency;
    /**
     * the date the transaction occured
     */
    private String transaction_date;
    /**
     * status of transaction
     * if the transaction is successful, status = "success"
     */
    private String status;
    /**
     * the unique reference that identifies the transaction
     */
    private String reference;
    /**
     * the type of paystack account the transaction was made, could be "test" or "live"
     */
    private String domain;
    // private String metadata;
    /**
     * details about the transaction or why it failed
     */
    private String gateway_response;
    /**
     * message for invalid request
     */
    private String message;
    /**
     * the channel the transaction was made, could be "bank" or "card"
     */
    private String channel;
    /**
     * the ip adress of the user performing the transaction
     */
    private String ip_address;
    /**
     *
     */
    private String fees;
    /**
     * the plan code if this transaction was made for a plan
     */
    private String plan;
//        /**
//         * extra information about this transaction
//         */
//        private Log log;
    /**
     * the date the transaction was paid
     */
    private String paid_at;
//        /**
//         * details concerning the card,
//         * so that it can be used for making tranaction on behalf of the user the next time
//         */
//        private Authorization authorization;

    public Data() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

//    public String getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(String metadata) {
//        this.metadata = metadata;
//    }

    public String getGateway_response() {
        return gateway_response;
    }

    public void setGateway_response(String gateway_response) {
        this.gateway_response = gateway_response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

//        public Log getLog() {
//            return log;
//        }
//
//        public void setLog(Log log) {
//            this.log = log;
//        }

    public String getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(String paid_at) {
        this.paid_at = paid_at;
    }
//
//        public Authorization getAuthorization() {
//            return authorization;
//        }
//
//        public void setAuthorization(Authorization authorization) {
//            this.authorization = authorization;
//        }
}


