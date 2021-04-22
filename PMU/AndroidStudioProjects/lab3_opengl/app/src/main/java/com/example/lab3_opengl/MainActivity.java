package com.example.lab3_opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GLSurfaceView myGLSurfaceView = new GLSurfaceView(this);
        myGLSurfaceView.setEGLContextClientVersion(2);
        myGLSurfaceView.setRenderer(new MyGL20Renderer());
        setContentView(myGLSurfaceView);
    }
}