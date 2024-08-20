package com.example.maeriklavanderia.service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class backupMssqlFirebase {


    private static Firestore db;


    static {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("path/to/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://your-database-name.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=bd_maerik";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "1234";


    public static void main(String[] args) {
        com.example.maeriklavanderia.service.CrudOperations crud = new com.example.maeriklavanderia.service.CrudOperations();

        crud.printTable();


    }
}
class DatabaseManager {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=bd_maerik";
        String user = "sa";
        String password = "1234";
        return DriverManager.getConnection(url, user, password);
    }
}

class CrudOperations {


    public void printTable() {
        String sql = "SELECT * FROM usuarios_local";

        try (Connection conn = com.example.maeriklavanderia.service.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                // Create a new document in the Firestore database
                DocumentReference docRef = backupMssqlFirebase.db.collection("usuarios_local").document();

                // Create a map to hold the document data
                Map<String, Object> docData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    docData.put(metaData.getColumnName(i), rs.getString(i));
                }

                // Set the document data in Firestore
                ApiFuture<WriteResult> result = docRef.set(docData);
                System.out.println("Update time : " + result.get().getUpdateTime());
            }

        } catch (SQLException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

