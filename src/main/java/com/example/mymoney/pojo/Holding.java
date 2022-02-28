package com.example.mymoney.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.mymoney.enums.AssetType;

public class Holding {
    private Map<AssetType, Asset> assets = new LinkedHashMap<>();

    public Holding(List<Asset> assets) {
        assets.forEach(asset -> {
            this.assets.put(asset.getType(), asset);
        });
    }

    public void addAmountToAsset(BigDecimal amount, AssetType assetType) {
        if (this.assets.containsKey(assetType))
            this.assets.get(assetType).addAmount(amount);
    }

    public void addAmountToAsset(List<Asset> assets) {
        assets.forEach(asset -> {
            if (this.assets.containsKey(asset.getType()))
                this.assets.get(asset.getType()).addAmount(asset.getAmount());
        });
    }

    public BigDecimal getTotalAmountOfHoldings() {
        return this.assets.values().stream().map(Asset::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Asset> getAllAssets() {
        return this.assets.values().stream().collect(Collectors.toList());
    }

}
