package com.example.stockapp.controller;

import com.example.stockapp.model.StockPrice;
import com.example.stockapp.service.StockService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/")
    public String index(Model model) throws IOException {
        List<StockPrice> stockPrices = stockService.getAllStockPrices();
        TimeSeries series = new TimeSeries("Stock Price");

        for (StockPrice stockPrice : stockPrices) {
            series.addOrUpdate(new Second(java.sql.Timestamp.valueOf(stockPrice.getDate())), stockPrice.getClose());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Stock Prices",
                "Date",
                "Price",
                dataset,
                true,
                true,
                false
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(out, chart, 800, 600);
        String base64 = java.util.Base64.getEncoder().encodeToString(out.toByteArray());
        model.addAttribute("chart", base64);

        return "index";
    }
}
