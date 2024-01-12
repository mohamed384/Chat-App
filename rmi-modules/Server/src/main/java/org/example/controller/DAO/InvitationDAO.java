package org.example.controller.DAO;

import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class InvitationDAO implements DAO<InvitationDAO>{

    @Override
    public boolean create(InvitationDAO invitationDAO) {

        try (Connection connection = DBConnection.getConnection()) {
            

        }catch(SQLException e){

        }

        return false;
    }


}
