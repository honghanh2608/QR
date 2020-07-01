package com.example.qrapp;

import android.app.Application;

import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;

public class MainApplication extends Application implements CameraXConfig.Provider {

    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }
}