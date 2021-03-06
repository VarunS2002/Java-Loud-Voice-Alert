package com.varuns2002.loud_voice_alert;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class LoudVoiceAlert {

    /**
     * The default threshold for the volume of the voice.<br>
     * Adjust the value for your own microphone and preferred volume.
     */
    public static int THRESHOLD = 500;

    static class Recorder implements Runnable {

        private final long currentTime;
        private boolean started = false;

        Recorder(long time) {
            this.currentTime = time;
        }

        @Override
        public void run() {
            AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
            final int bufferByteSize = 2048;

            TargetDataLine line;
            try {
                line = AudioSystem.getTargetDataLine(fmt);
                line.open(fmt, bufferByteSize);
            } catch (LineUnavailableException e) {
                System.out.println(e.getMessage());
                return;
            }

            byte[] buf = new byte[bufferByteSize];
            float[] samples = new float[bufferByteSize / 2];

            float lastPeak = 0f;

            line.start();
            for (int b; (b = line.read(buf, 0, buf.length)) > -1; ) {
                if (!this.started && System.currentTimeMillis() - this.currentTime > 3000) {
                    this.started = true;
                    System.out.println("Started");
                    System.out.println("Current Threshold: " + THRESHOLD);
                    System.out.println("Current Time: " + new SimpleDateFormat("hh:mm:ss aa").format(new Date()));
                    System.out.println("-----------------------------------------");
                }

                // convert bytes to samples here
                for (int i = 0, s = 0; i < b; ) {
                    int sample = 0;

                    sample |= buf[i++] & 0xFF; // (reverse these two lines
                    sample |= buf[i++] << 8;   //  if the format is big endian)

                    // normalize to range of +/-1.0f
                    samples[s++] = sample / 32768f;
                }

                float rms = 0f;
                float peak = 0f;
                for (float sample : samples) {

                    float abs = Math.abs(sample);
                    if (abs > peak) {
                        peak = abs;
                    }

                    rms += sample * sample;
                }

                rms = (float) Math.sqrt(rms / samples.length);

                if (lastPeak > peak) {
                    peak = lastPeak * 0.875f;
                }

                lastPeak = peak;

                int currentVolume = (int) (Math.abs(rms) * 1000);
                if (this.started && currentVolume > THRESHOLD) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println(currentVolume + ", " + new SimpleDateFormat("hh:mm:ss aa").format(new Date()));
                }
                // System.out.println("Amplitude: " + Math.abs(rms) * 1000 + ", Peak: " + Math.abs(peak));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean askForThreshold = true;
        try {
            FileInputStream inputStream = new FileInputStream("./config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            int thresholdProperty = Integer.parseInt(properties.getProperty("threshold"));
            if (thresholdProperty > 0) {
                THRESHOLD = thresholdProperty;
                askForThreshold = false;
            } else {
                System.out.println("Invalid threshold value.");
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println("Error while reading config.properties file.");
        }

        if (askForThreshold) {
            System.out.print("Enter the threshold value: ");
            int temporaryThreshold;
            try {
                temporaryThreshold = scanner.nextInt();
                if (temporaryThreshold > 0) {
                    THRESHOLD = temporaryThreshold;
                } else {
                    System.out.println("Invalid threshold value. Using default threshold value.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid threshold value. Using default threshold value.");
            }
        }
        System.out.println("Starting Loud Voice Alert in 3 seconds...");
        new Thread(new Recorder(System.currentTimeMillis())).start();
    }
}
