package com.example.mymoney.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.mymoney.enums.AssetType;
import com.example.mymoney.enums.Month;
import com.example.mymoney.pojo.Asset;
import com.example.mymoney.pojo.Holding;
import com.example.mymoney.utils.InvestmentCalculator;

public class PortfolioManager implements Portfolio {
    private Map<Month, Holding> statment = new LinkedHashMap<>();
    private List<BigDecimal> sip = new ArrayList<>();
    private List<Float> derivedPercents = new ArrayList<>();

    public Map<Month, Holding> getStatment() {
        return statment;
    }

    public List<Asset> balance(Month month) {
        if (statment.containsKey(month)) {
            return statment.get(month).getAllAssets();
        }
        return new ArrayList<>();
    }

    public boolean canRebalance() {
        return this.statment.containsKey(Month.JUNE) || this.statment.containsKey(Month.DECEMBER);
    }

    public void rebalance(Month month) {
        if (this.statment.containsKey(month)) {
            int index = 0;
            Holding currenHolding = this.statment.get(month);
            BigDecimal totalAmount = currenHolding.getTotalAmountOfHoldings();
            List<BigDecimal> derivedAmounts = new ArrayList<>();
            AssetType[] types = AssetType.values();
            for (AssetType type : types) {
                type.name();
                derivedAmounts.add(InvestmentCalculator.change(totalAmount,
                        this.derivedPercents.get(index++)));
            }
            allocate(derivedAmounts, month);
        }

    }

    public void change(List<Float> percents, Month month) {
        if (this.statment.containsKey((month))) {
            addPreviousHoldingToCurrentMonth(month);
            Holding currenHolding = this.statment.get(month);
            applyMarketChange(currenHolding, percents);
        } else {
            this.sip(this.sip, month);
            addPreviousHoldingToCurrentMonth(month);
            Holding afterSIP = this.statment.get(month);
            applyMarketChange(afterSIP, percents);
            if (month == Month.JUNE || month == Month.DECEMBER)
                this.rebalance(month);
        }
    }

    public void applyMarketChange(Holding holdings, List<Float> percents) {
        int index = 0;
        for (Asset asset : holdings.getAllAssets()) {
            asset.addAmount(InvestmentCalculator.change(asset.getAmount(),
                    percents.get(index++)));

        }
    }

    public void allocate(List<BigDecimal> amounts, Month month) {
        List<Asset> assets = new ArrayList<>();
        AssetType[] types = AssetType.values();
        if (month == Month.JANUARY)
            this.calculatDerivedPercent(amounts);
        if (amounts.size() == types.length) {
            for (int i = 0; i < types.length; i++) {
                assets.add(new Asset(types[i], amounts.get(i)));
            }
        }
        this.statment.put(month == null ? Month.JANUARY : month, new Holding(assets));
    }

    private void calculatDerivedPercent(List<BigDecimal> amounts) {
        BigDecimal total = amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        amounts.forEach(amount -> this.derivedPercents.add(InvestmentCalculator.percent(amount, total)));
    }

    public void sip(List<BigDecimal> amounts, Month month) {

        if (month == Month.JANUARY)
            return;

        if (month == Month.FEBRUARY) {
            this.sip.addAll(amounts);
            this.allocate(amounts, month);
        } else {
            this.allocate(amounts, month);
        }

    }

    public void addPreviousHoldingToCurrentMonth(Month month) {
        if (Month.previousMonth(month) != month)
            if (this.statment.containsKey(month)) {
                Holding previouHolding = this.statment.get(Month.previousMonth(month));
                if (this.statment.containsKey(month)) {
                    Holding currentHolding = this.statment.get(month);
                    currentHolding.addAmountToAsset(previouHolding.getAllAssets());
                }
            }
    }
}
