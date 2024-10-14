package com.example.digital.automation.stepdefinitions;
//import org.monte.media.Format;
//import org.monte.media.FormatKeys;
//import org.monte.media.math.Rational;
//import org.monte.screenrecorder.ScreenRecorder;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.IOException;

//import static org.monte.media.VideoFormatKeys.*;

@Component
public class VideoRecorder {

//    private ScreenRecorder screenRecorder;

//    public void startRecording(String scenarioName) throws IOException, AWTException {
//        // Create the directory for storing videos
//        File file = new File("./target/videos/");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        // Get screen size for capturing
//        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//
//        // Define video and file format
//        Format fileFormat = new Format(FormatKeys.MediaTypeKey, FormatKeys.MediaType.FILE,
//                FormatKeys.MimeTypeKey, FormatKeys.MIME_AVI);
//
//        Format videoFormat = new Format(FormatKeys.MediaTypeKey, FormatKeys.MediaType.VIDEO,
//                FormatKeys.EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                DepthKey, 24, FrameRateKey, Rational.valueOf(15),
//                QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60);
//
//        // No audio format for simplicity (null is acceptable if audio is not needed)
//        Format audioFormat = null;
//
//        // Get graphics configuration (required by ScreenRecorder)
//        GraphicsConfiguration gc = GraphicsEnvironment
//                .getLocalGraphicsEnvironment()
//                .getDefaultScreenDevice()
//                .getDefaultConfiguration();
//
//        // Initialize the specialized screen recorder
//        screenRecorder = new SpecializedScreenRecorder(gc, captureSize, fileFormat, videoFormat,
//                null, audioFormat, file, scenarioName);
//
//        // Start recording
//        screenRecorder.start();
//    }
//
//    public void stopRecording() throws IOException {
//        if (screenRecorder != null) {
//            screenRecorder.stop();
//        }
//    }
}
