# Simple TV Display Box

Android app that automatically launches when the device boots & displays a webpage. That's all :) 

## Setup

1. Set your web URL in `config.java`:
   ```
   public static final String URL = "https://your-website.com";
   ```
2. Build and install the app with Android Studio
3. On first run, give it the permission to draw over other apps when the system settings dialog opens

## Usage

1. Connect the device to a screen
2. The app will launch and display your website on boot
3. Enjoy

## Notes

If the page doesn't load at first, the app will refresh until it does.
