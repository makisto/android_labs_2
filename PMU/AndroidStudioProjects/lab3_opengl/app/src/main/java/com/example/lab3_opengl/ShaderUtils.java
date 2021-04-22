package com.example.lab3_opengl;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

public class ShaderUtils
{
    public static int shader_init(int vertex_shader, int fragment_shader)
    {
        final int init_id = glCreateProgram();
        glAttachShader(init_id, vertex_shader);
        glAttachShader(init_id, fragment_shader);
        glLinkProgram(init_id);
        final int[] linkStatus = new int[1];
        glGetProgramiv(init_id, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0)
        {
            glDeleteProgram(init_id);
            return -2;
        }
        return init_id;
    }

    static int shader_handler(int type, String shader)
    {
        final int shader_id = glCreateShader(type);
        if (shader_id == 0)
        {
            return -1;
        }
        glShaderSource(shader_id, shader);
        glCompileShader(shader_id);
        final int[] is_shader_good = new int[1];
        glGetShaderiv(shader_id, GL_COMPILE_STATUS, is_shader_good, 0);
        if (is_shader_good[0] == 0)
        {
            glDeleteShader(shader_id);
            return -2;
        }
        return shader_id;
    }

    public static int create_shaders()
    {
        String vertex_shader = "uniform mat4 uMVPMatrix, uMVMatrix, uNormalMat;" +
                "attribute vec4 vPosition;" +
                "attribute vec4 vColor;" +
                "attribute vec3 vNormal;" +
                "varying vec4 varyingColor;" +
                "varying vec3 varyingNormal;" +
                "varying vec3 varyingPos;" +
                "void main()" +
                "{" +
                "varyingColor = vColor;" +
                "varyingNormal= vec3(uNormalMat * vec4(vNormal, 0.0));" +
                "varyingPos = vec3(uMVMatrix * vPosition);" +
                "gl_Position = uMVPMatrix * vPosition;" +
                "}";
        String fragment_shader = "precision mediump float;" +
                "varying vec4 varyingColor; varying vec3 varyingNormal;" +
                "varying vec3 varyingPos;" +
                "uniform vec3 lightDir;" +
                "void main()" +
                "{" +
                "float Ns = 40.0;" +
                "float kd = 0.9, ks = 0.9;" +
                "vec4 light = vec4(1.0, 1.0, 1.0, 1.0);" +
                "vec4 lightS = vec4(1.0, 1.0, 1.0, 1.0);" +
                "vec3 Nn = normalize(varyingNormal);" +
                "vec3 Ln = normalize(lightDir);" +
                "vec4 diffuse = kd * light * max(dot(Nn, Ln), 0.0);" +
                "vec3 Ref = reflect(Nn, Ln);" +
                "float spec = pow(max(dot(Ref, normalize(varyingPos)), 0.0), Ns);" +
                "vec4 specular = lightS * ks * spec;" +
                "gl_FragColor = varyingColor * diffuse + specular;" +
                "}";

        int vertex_shader_id = shader_handler(GL_VERTEX_SHADER, vertex_shader);
        int fragment_shader_id = shader_handler(GL_FRAGMENT_SHADER, fragment_shader);

        return shader_init(vertex_shader_id, fragment_shader_id);
    }
}
