package com.example.maeriklavanderia.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class BackupMssqlFirebase {

        private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=bd_maerik";
        private static final String JDBC_USER = "sa";
        private static final String JDBC_PASSWORD = "1234";
        private static Firestore db;

        static {
            try {
                FileInputStream serviceAccount = new FileInputStream("C:/Users/Mil/Desktop/Maerik/serviceAccountKey.json");
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options);
                }
                db = FirestoreClient.getFirestore();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void backupDataToFirestore () {
            String sql = "SELECT * FROM usuarios_local";
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    String rfid = rs.getString("rfid");
                    String nombre = rs.getString("nombre");
                    DocumentReference docRef = db.collection("usuarios").document(rfid);
                    Map<String, Object> docData = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        docData.put(metaData.getColumnName(i), rs.getString(i));
                    }
                    ApiFuture<WriteResult> result = docRef.set(docData);
                    System.out.println(" RFID=  " + rfid+ " Nombre=  " + nombre+"Update time : " + result.get().getUpdateTime()) ;
                }
            } catch (SQLException | InterruptedException | ExecutionException e) {
                System.out.println("ERROR");
                e.printStackTrace();
            }
        }
    }

