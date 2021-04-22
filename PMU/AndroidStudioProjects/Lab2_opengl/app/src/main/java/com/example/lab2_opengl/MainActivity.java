package com.example.lab2_opengl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

interface Object3D
{
    void Draw(GL10 gl10);
}

class QuadrilateralTextured implements Object3D
{
    private final int[] textures = new int[1];

    private final ShortBuffer order_buffer;
    private final FloatBuffer coordinates_buffer;
    private final FloatBuffer texture_coordinates_buffer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private FloatBuffer new_coordinates_buffer(float[] coordinates_buffer)
    {
        ByteBuffer byte_coordinates_buffer = ByteBuffer.allocateDirect(Float.BYTES * coordinates_buffer.length);
        byte_coordinates_buffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertex_coordinates_buffer = byte_coordinates_buffer.asFloatBuffer();
        vertex_coordinates_buffer.put(coordinates_buffer);
        vertex_coordinates_buffer.position(0);
        return vertex_coordinates_buffer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private FloatBuffer new_texture_buffer()
    {
        ByteBuffer byte_texture_buffer = ByteBuffer.allocateDirect(Float.BYTES * 8);
        byte_texture_buffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertex_texture_buffer = byte_texture_buffer.asFloatBuffer();
        vertex_texture_buffer.put(new float[] {
                0f, 0f, 0f, 1f, 1f, 0f, 1f, 1f
        });
        vertex_texture_buffer.position(0);
        return vertex_texture_buffer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ShortBuffer new_order_buffer()
    {
        ByteBuffer byte_order_buffer = ByteBuffer.allocateDirect(Short.BYTES * 4);
        byte_order_buffer.order(ByteOrder.nativeOrder());
        ShortBuffer vertex_order_buffer = byte_order_buffer.asShortBuffer();
        vertex_order_buffer.put(new short[]{
                0, 1, 2, 3
        });
        vertex_order_buffer.position(0);
        return vertex_order_buffer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    QuadrilateralTextured(GL10 gl10, float[] vertices, Bitmap bitmap)
    {
        coordinates_buffer = this.new_coordinates_buffer(vertices);
        texture_coordinates_buffer = this.new_texture_buffer();
        order_buffer = this.new_order_buffer();

        gl10.glGenTextures(1, textures, 0);
        gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0, bitmap,0);
    }

    @Override
    public void Draw(GL10 gl10)
    {
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glVertexPointer(3, GL10.GL_FLOAT, GL10.GL_ZERO, this.coordinates_buffer);

        gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl10.glTexCoordPointer(2, GL10.GL_FLOAT, GL10.GL_ZERO, this.texture_coordinates_buffer);

        gl10.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_SHORT, this.order_buffer);
    }
}


@RequiresApi(api = Build.VERSION_CODES.N)
class MatrixRenderer implements GLSurfaceView.Renderer
{
    int counter = 0;

    Activity new_activity;

    ArrayList<Object3D> matrices = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public MatrixRenderer(Activity activity)
    {
        new_activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig)
    {
        matrices.addAll(Arrays.asList(
                new QuadrilateralTextured(
                        gl10,
                        new float[] {
                                -1.5f, -1.5f, 0f,
                                -1.5f, 1.5f, 0f,
                                1.5f, -1.5f, 0f,
                                1.5f, 1.5f, 0f,
                        },
                        (Bitmap) BitmapFactory.decodeStream(new_activity.getResources().openRawResource(R.raw.sun))
                ),
                new QuadrilateralTextured(
                        gl10,
                        new float[] {
                                -1.5f, -1.5f, 0f,
                                -1.5f, 1.5f, 0f,
                                1.5f, -1.5f, 0f,
                                1.5f, 1.5f, 0f,
                        },
                        (Bitmap) BitmapFactory.decodeStream(new_activity.getResources().openRawResource(R.raw.earth))
                ),
                new QuadrilateralTextured(
                        gl10,
                        new float[]{
                                -1.5f, -1.5f, 0f,
                                -1.5f, 1.5f, 0f,
                                1.5f, -1.5f, 0f,
                                1.5f, 1.5f, 0f,
                        },
                        (Bitmap) BitmapFactory.decodeStream(new_activity.getResources().openRawResource(R.raw.moon))
                )
        ));
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1)
    {

    }

    @Override
    public void onDrawFrame(GL10 gl10)
    {
        counter = ++counter % 360;

        float dx_earth = (float) Math.cos(Math.toRadians((float) counter)) * 4;
        float dy_earth = (float) Math.sin(Math.toRadians((float) counter)) * 4;

        float dx_moon = (float) Math.cos(Math.toRadians((float) counter * 2)) * 2 + dx_earth;
        float dy_moon = (float) Math.sin(Math.toRadians((float) counter * 2)) * 2 + dy_earth;

        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl10.glLoadIdentity();
        gl10.glScalef(0.25f, 0.25f, 0.25f);

        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glDepthFunc(GL10.GL_LESS);
        gl10.glEnable(GL10.GL_BLEND);
        gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl10.glEnable(GL10.GL_TEXTURE_2D);

        matrices.get(0).Draw(gl10);
        matrices.get(1).Draw(gl10);
        matrices.get(2).Draw(gl10);

        matrices.add(1, new QuadrilateralTextured(gl10,
                new float[]
                        {
                                -1.5f + dx_earth,   -1.5f + dy_earth,  0f,
                                -1.5f + dx_earth,   1.5f + dy_earth,   0f,
                                1.5f + dx_earth,    -1.5f + dy_earth,   0f,
                                1.5f + dx_earth,    1.5f + dy_earth,   0f,
                        },
                (Bitmap) BitmapFactory.decodeStream(new_activity.getResources().openRawResource(R.raw.earth))
        ));

        matrices.add(2, new QuadrilateralTextured(
                gl10,
                new float[] {
                        -1.5f + dx_moon,   -1.5f + dy_moon,  0f,
                        -1.5f + dx_moon,   1.5f + dy_moon,   0f,
                        1.5f + dx_moon,    -1.5f + dy_moon,   0f,
                        1.5f + dx_moon,    1.5f + dy_moon,   0f,
                },
                (Bitmap) BitmapFactory.decodeStream(new_activity.getResources().openRawResource(R.raw.moon))
        ));

        gl10.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl10.glDisable(GL10.GL_TEXTURE_2D);
        gl10.glDisable(GL10.GL_BLEND);
        gl10.glDisable(GL10.GL_DEPTH_TEST);
    }
}

public class MainActivity extends AppCompatActivity
{
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        GLSurfaceView gl_surface_view = new GLSurfaceView(this);
        gl_surface_view.setRenderer(new MatrixRenderer(this));
        gl_surface_view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(gl_surface_view);
    }
}
