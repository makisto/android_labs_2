package com.example.lab4_opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GLSurfaceView myGLSurfaceView = new GLSurfaceView(this);
        myGLSurfaceView.setEGLContextClientVersion(2);
        myGLSurfaceView.setRenderer(new MyGL20Renderer(this));
        setContentView(myGLSurfaceView);
    }
}