package com.example.mymoney.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.example.mymoney.enums.Month;
import com.example.mymoney.pojo.Asset;
import com.example.mymoney.pojo.Holding;

public interface Portfolio {
    public List<Asset> balance(Month month);

    public void allocate(List<BigDecimal> collect, Month january);

    public void change(List<Float> percentChange, Month asMonth);

    public void sip(List<BigDecimal> collect, Month february);

    public boolean canRebalance();

    public Map<Month, Holding> getStatment();
}
