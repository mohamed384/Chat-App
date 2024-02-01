package org.example.controllerFx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.util.Duration;
import org.example.DAO.CountryDAOImpl;
import org.example.DAO.GenderDAOImpl;
import org.example.DAO.UserStateDAOImpl;
import org.example.callBackImp.CallBackServerImp;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class StatisticController {

    @FXML
    private PieChart genderChat;


    @FXML
    private PieChart userStateChart;


    @FXML
    private Tab genderTap;
    @FXML
    private Tab userStateTap;

    @FXML
    private LineChart<String, Number> countryChart;


    @FXML
    private Tab countryTap;

    private ObservableList<PieChart.Data> genderChartData = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<String, Number>> countryChartData = FXCollections.observableArrayList();

    private GenderDAOImpl genderDAO;
    private CountryDAOImpl countryDAO;

    private UserStateDAOImpl userStateDAO;

    // Timeline for periodic updates
    private Timeline updateTimeline;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);




    @FXML
    void initialize() {

        genderDAO = new GenderDAOImpl();
        countryDAO = new CountryDAOImpl();
        userStateDAO = new UserStateDAOImpl();

        updateGenderChart();
        updateCountryChart();
        updateUserStatusChart();


        genderTap.setOnSelectionChanged(event -> {
            if (genderTap.isSelected()) {

                updateGenderChart();
            }else if (countryTap.isSelected()){
                countryChart.setData(countryChartData);
                updateCountryChart();
            } else if (userStateTap.isSelected()){
                updateUserStatusChart();

            }
        });


        updateTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    updateGenderChart();
                    updateCountryChart();
                    updateUserStatusChart();
                })
        );

        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();


    }


    private void updateGenderChart() {

        Map<String, Integer> genderMap = genderDAO.getCountOfGender();

        genderChartData.clear();

        int totalCount = genderMap.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : genderMap.entrySet()) {
            double percentage = (double) entry.getValue() / totalCount * 100;
            genderChartData.add(new PieChart.Data(entry.getKey() + " (" + String.format("%.2f", percentage) + "%)", entry.getValue()));
        }


        genderChat.setTitle("Gender User Statistics");
        genderChat.setData(genderChartData);

    }



    private void updateCountryChart() {

            Map<String, Integer> countryMap = countryDAO.getCountOfCountry();

            countryChartData.clear();

            XYChart.Series<String, Number> countSeries = new XYChart.Series<>();
            countSeries.setName("Count"); // Optional: Set a name for the series

            for (Map.Entry<String, Integer> entry : countryMap.entrySet()) {
                // Add data point to the count series
                countSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            countryChart.getXAxis().setLabel("Country");
            countryChart.getYAxis().setLabel("User Count");
            countryChart.setTitle("Country User Statistics");

            countryChart.getData().clear();
            countryChartData.add(countSeries);
            countryChart.setData(countryChartData);
            countryTap.setContent(countryChart);

            if(countryMap.size() > 20){
                countryChart.getXAxis().setTickLabelRotation(90); // Rotate labels by 45 degrees
            } else if (countryMap.size() > 10){
                countryChart.getXAxis().setTickLabelRotation(-45); // Rotate labels by 45 degrees
            } else {
                countryChart.getXAxis().setTickLabelRotation(0); // Rotate labels by 45 degrees

            }


    }

    private void updateUserStatusChart() {


        int totalUsers = userStateDAO.getCounteUsers();

        int onlineCount = CallBackServerImp.getClients();
        int offlineCount =  totalUsers - onlineCount;


        double onlinePercentage = (double) onlineCount / totalUsers * 100;
        double offlinePercentage = (double) offlineCount / totalUsers * 100;

        userStateChart.getData().clear(); // Clear existing data

        userStateChart.getData().add(new PieChart.Data("Online (" + String.format("%.2f%%", onlinePercentage) + ")", onlineCount));
        userStateChart.getData().add(new PieChart.Data("Offline (" + String.format("%.2f%%", offlinePercentage) + ")", offlineCount));

        userStateChart.setTitle("User Status Statistics");


    }



}