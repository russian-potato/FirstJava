package com.example.stockapp.service;

import com.example.stockapp.model.StockPrice;
import com.example.stockapp.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockPriceRepository stockPriceRepository;

    public List<StockPrice> getAllStockPrices() {
        return stockPriceRepository.findAll();
    }
}
