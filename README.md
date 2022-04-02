# Java-Loud-Voice-Alert

## [Downloads](https://github.com/VarunS2002/Java-Loud-Voice-Alert/releases)

[![Latest: v1.0](https://img.shields.io/badge/release-v1.0-brightgreen)](https://github.com/VarunS2002/Java-Loud-Voice-Alert/releases/download/1.0/Java-Loud-Voice-Alert_1.0.jar)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

A simple command line application to alert you by sounding a beep when you speak too loudly.

Example use cases:
- When you're in a meeting and you're not sure if your voice is being heard
- When you're playing games and casting slurs on voice chat
- When you're listening to music and speaking

#### This program is basically a fork of the Volume Meter built by [Radiodef](https://stackoverflow.com/users/2891664/radiodef) which was posted [here](https://stackoverflow.com/a/26576548/13978447).

## Requirements:

- Java Runtime Environment (JRE)

## Usage:

- Download the `.jar` (Java Archive) file
- Method 1:
  - Place the `config.properties` file in the same directory as the `.jar` file
  - Enter your preferred threshold value (should be an integer>0) in the `config.properties` file
  - Run the `.jar` file from the command line using the following command:
    ```
    java -jar Java-Loud-Voice-Alert.jar
    ```
- Method 2:
  - Run the `.jar` file from the command line using the following command:
    ```
    java -jar Java-Loud-Voice-Alert.jar
    ```
  - Enter your preferred threshold value (should be an integer>0)

## Note:

- If an invalid threshold value is entered, the default value of `500` will be used
- You have to adjust your threshold value based on the sensitivity of your microphone and system values
- You can adjust it step-by-step by starting with a lower value and increasing it until you're satisfied with the result
- If the input device is changed, you will not be alerted and you will have to restart the application

## Screenshot:

&nbsp;&nbsp;&nbsp;&nbsp;![Main_Screen](https://i.imgur.com/UEYcgLR.png)
