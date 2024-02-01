package org.example.DAO;

import org.example.DAO.interfaces.CountryDAO;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CountryDAOImpl implements CountryDAO {
    @Override
    public Map<String, Integer> getCountOfCountry() {
        Map<String, Integer> countryCountMap = null;
        String query = "SELECT Country, COUNT(Country) AS Country_count FROM users\n" +
                "GROUP BY Country;";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            countryCountMap = new HashMap<>();

            while (rs.next()){
                String country = rs.getString("Country");
                Integer count = rs.getInt("Country_count");

                countryCountMap.put(country , count);
            }

            return countryCountMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  Collections.emptyMap();

    }
}
