package com.example.mymoney.pojo;

import java.math.BigDecimal;

import com.example.mymoney.enums.AssetType;

public class Asset {

    private AssetType type;
    private BigDecimal amount;

    public Asset(AssetType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void addAmount(BigDecimal amount) {
        if (this.amount == null)
            this.amount = amount;
        else
            this.amount = this.amount.add(amount);

    }

}
