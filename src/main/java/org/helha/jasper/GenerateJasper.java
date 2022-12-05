package org.helha.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GenerateJasper {

    public static void main(String[] args) {
        // Oracle Database connexion
        String url = "jdbc:postgresql://localhost:5432/helhafit";
        Properties props = new Properties();
        props.setProperty("user", "helhafit");
        props.setProperty("password", "helhafit");
        Connection connection = null;

        try {

            connection = DriverManager.getConnection(url, props);

            JasperReport jasperReport
                    = (JasperReport) JRLoader.loadObjectFromFile("/home/bbr/JaspersoftWorkspace/MyReports/people_list.jasper");

            Map parameters = new HashMap();
            parameters.put("nbMember", 20);

            // Fill the Jasper Report
            JasperPrint jasperPrint
                    = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Creation of the HTML Jasper Reports
            JasperExportManager.exportReportToPdfFile(jasperPrint, "/tmp/people_list.pdf");

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }
}
