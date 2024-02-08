package org.example.controller.FXMLController;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.DTOs.MessageDTO;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class FileController implements Initializable {

    @FXML
    private Label FileName;

    @FXML
    private Label FileSize;

    @FXML
    private Label FileType;

    @FXML
    private Button downloadBtn;

    @FXML
    private ProgressBar progressBar;

    private String fileName;
    private String fileSize;
    private String fileType;

    public FileController() {
        progressBar = new ProgressBar();
    }

   public  FileController(String fileName, String fileSize, String fileType) {
       progressBar = new ProgressBar();
        this.fileName = "Name:"+fileName;
       this.fileSize = "Size: "+fileSize + " MB";
       this.fileType = "type: "+fileType;

    }

    public void setProgress(double value) {
        progressBar.setProgress(value);
    }

    public void setProgressBar(ProgressBar par) {
         this.progressBar = par;
    }
    public ProgressBar getProgressBar() {
        return progressBar;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FileName.setWrapText(true);
        FileName.setText(fileName);
        FileSize.setText(fileSize);
        FileType.setText(fileType);

        downloadBtn.setOnAction(event -> downloadFile());

    }


    private  String attachmentPathName;
    private  byte[] attachment;



    public void setAttachmentFromDB(MessageDTO messageDTO)  {
        attachmentPathName = messageDTO.getMessageContent();
        attachment = messageDTO.getAttachment();
    }

    public void downloadFile() {
        String filePath = attachmentPathName;
        if (filePath != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            if (filePath.contains(".")) {
                String extension = filePath.substring(filePath.lastIndexOf("."));
                fileChooser.setInitialFileName(filePath.replace("Name:", "") + extension);
            } else {
                fileChooser.setInitialFileName(filePath.replace("Name:", ""));
            }

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                Path destinationPath = file.toPath();

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destinationPath.toFile()))) {
                            int totalBytes = attachment.length;
                            int bytesWritten = 0;
                            int bufferSize = 1024;
                            byte[] buffer = new byte[bufferSize];

                            for (int i = 0; i < totalBytes; i += bufferSize) {
                                int bytesToWrite = Math.min(bufferSize, totalBytes - i);
                                System.arraycopy(attachment, i, buffer, 0, bytesToWrite);
                                bos.write(buffer, 0, bytesToWrite);
                                bytesWritten += bytesToWrite;

                                // Update the progress
                                updateProgress(bytesWritten, totalBytes);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                progressBar.progressProperty().bind(task.progressProperty());

                task.setOnSucceeded(event -> {
                    showNotification("File Download", "Download completed successfully.");
                });

                task.setOnFailed(event -> {
                    showNotification("File Download Error", "An error occurred during the download.");
                });

                new Thread(task).start();
            }
        }
    }

    private void showNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .owner(null) // You can set a specific owner if needed
                .hideAfter(Duration.seconds(5)) // Notification duration
                .position(Pos.TOP_RIGHT)
                .showInformation(); // Use showInformation() for an information notification
    }
}
