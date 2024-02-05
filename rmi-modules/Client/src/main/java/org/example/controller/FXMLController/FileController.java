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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
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


    public void downloadFile() {
        Message22Controller message22Controller = new Message22Controller();
        if (fileName != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.setInitialFileName(fileName.replace("Name:", ""));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                Path sourcePath = null;
                if (message22Controller.uploadedFilePath != null) {
                    sourcePath = Paths.get(message22Controller.uploadedFilePath.toUri());
                }
                Path destinationPath = file.toPath();

                Path finalSourcePath = sourcePath;
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try (FileChannel sourceChannel = new FileInputStream(finalSourcePath.toFile()).getChannel();
                             FileChannel destinationChannel = new FileOutputStream(destinationPath.toFile()).getChannel()) {

                            long totalSize = sourceChannel.size();
                            long transferredSize = 0;
                            while (transferredSize < totalSize) {
                                long transferred = destinationChannel.transferFrom(sourceChannel, transferredSize, 1024);
                                transferredSize += transferred;
                                updateProgress(transferredSize, totalSize);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };

                progressBar.progressProperty().bind(task.progressProperty());

                task.setOnSucceeded(event -> {
                    // Download completed, show a notification
                    showNotification("File Download", "Download completed successfully.");
                });

                task.setOnFailed(event -> {
                    // Download failed, show an error notification
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
