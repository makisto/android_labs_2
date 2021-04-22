package com.example.lab1_opengl;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GLSurfaceView g = new GLSurfaceView(this);

        //g.setRenderer(new OpenGLRendererSquare());
        //g.setRenderer(new OpenGLRendererCube());
        g.setRenderer(new OpenGLRendererSphere());

        g.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(g);
    }
}