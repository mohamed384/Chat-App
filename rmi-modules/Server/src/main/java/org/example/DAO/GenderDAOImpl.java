package org.example.DAO;

import org.example.DAO.interfaces.GenderStatisticDAO;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GenderDAOImpl implements GenderStatisticDAO {

    public  Map<String, Integer> getCountOfGender(){
        Map<String, Integer> genderCountMap = null;
        String query = "SELECT gender, COUNT(gender) AS gender_count  FROM users GROUP BY gender;";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            genderCountMap = new HashMap<>();

            while (rs.next()){
                String gender = rs.getString("gender");
                Integer count = rs.getInt("gender_count");

                genderCountMap.put(gender , count);
            }

            return genderCountMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  Collections.emptyMap();
    }


}
