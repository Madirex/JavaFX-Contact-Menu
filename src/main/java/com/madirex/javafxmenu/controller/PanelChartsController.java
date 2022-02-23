package com.madirex.javafxmenu.controller;

import com.madirex.javafxmenu.dto.PersonYearAverageDTO;
import com.madirex.javafxmenu.model.UserStorageAndConfiguration;
import com.madirex.javafxmenu.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class PanelChartsController {
    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Double> barChart;

    @FXML
    public void initialize() {
        initCharts();
    }

    public void initCharts(){
        initPieChartGenders();
        initBarChartAges();
    }

    private void initPieChartGenders() {
        ObservableList<PieChart.Data> dataChart = FXCollections.observableArrayList(
                new PieChart.Data(Util.getString("text.male"), UserStorageAndConfiguration.getInstance().returnPersonaGenderQuantity("male")),
                new PieChart.Data(Util.getString("text.female"), UserStorageAndConfiguration.getInstance().returnPersonaGenderQuantity("female")));
        pieChart.setData(dataChart);
        pieChart.setClockwise(false);
        pieChart.setTitle(Util.getString("text.genders"));
    }

    private void initBarChartAges(){
        barChart.getData().clear();
        XYChart.Series youngAge = new XYChart.Series<>();
        XYChart.Series adultAge = new XYChart.Series<>();
        barChart.setTitle(Util.getString("title.ageAverage"));

        youngAge.setName(Util.getString("text.young"));
        adultAge.setName(Util.getString("text.adult"));

        PersonYearAverageDTO pya = UserStorageAndConfiguration.getInstance().returnYoungPersonYearAverage();
        youngAge.getData().add(new XYChart.Data(Util.getString("text.young") + " (1-24)",pya.getYoungAvg()));
        adultAge.getData().add(new XYChart.Data(Util.getString("text.adult") + " (25-*)",pya.getAdultAvg()));

        barChart.getData().addAll(youngAge, adultAge);
        barChart.verticalGridLinesVisibleProperty();
    }
}
