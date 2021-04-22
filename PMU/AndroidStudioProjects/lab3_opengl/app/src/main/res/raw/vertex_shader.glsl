uniform mat4 uMVPMatrix, uMVMatrix, uNormalMat;
attribute vec4 vPosition;
attribute vec4 vColor;
attribute vec3 vNormal;
varying vec4 varyingColor;
varying vec3 varyingNormal;
varying vec3 varyingPos;

void main() 
{
	varyingColor = vColor;
    varyingNormal= vec3(uNormalMat * vec4(vNormal, 0.0));
    varyingPos = vec3(uMVMatrix * vPosition);
    gl_Position = uMVPMatrix * vPosition;
}