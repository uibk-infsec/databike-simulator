package com.example.compass

import android.opengl.GLES20
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

const val COORDINATES_PER_VERTEX = 3

class Arrow {
    private val vertices: FloatArray = floatArrayOf(
        0f, 0f, 1f,         //front
        -.3f, -.3f, -1f,    //bottom left
        -.3f, .3f, -1f,     //top left
        .3f, -.3f, -1f,     //bottom right
        .3f, .3f, -1f       //top right
    )

    private val vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(vertices.size * 4).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer().apply {
                put(vertices)
                position(0)
            }
        }

    private val faceOrder: ShortArray = shortArrayOf(
        0, 1, 2,
        0, 2, 4,
        0, 1, 3,
        0, 3, 4,
        1, 2, 3,
        2, 3, 4
    )

    private val faceElementBuffer: ShortBuffer =
        ByteBuffer.allocateDirect(faceOrder.size * 2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(faceOrder)
                position(0)
            }
        }

    private val edgeOrder: ShortArray = shortArrayOf(
        0, 1,
        0, 2,
        0, 3,
        0, 4,
        1, 2,
        2, 4,
        3, 4,
        1, 3,
        1, 4,
        2, 3
    )

    private val edgeElementBuffer: ShortBuffer =
        ByteBuffer.allocateDirect(edgeOrder.size * 2).run {
            order(ByteOrder.nativeOrder())
            asShortBuffer().apply {
                put(edgeOrder)
                position(0)
            }
        }

    private val faceColor = floatArrayOf(0.636f, 0.769f, 0.222f, 1f)
    private val edgeColor = floatArrayOf(0.2f, 0.3f, 0f, 1f)

    private val vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
                "uniform mat4 uRotationMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * uRotationMatrix * vPosition;" +
                "}"

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"

    private var mProgram: Int
    private var positionHandle: Int = 0
    private var colorHandle: Int = 0
    private var mvpMatrixHandle: Int = 0
    private var rotationMatrixHandle: Int = 0

    init {
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        mProgram = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }
    }

    fun draw(mvpMatrix: FloatArray, rotationMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)

        // projection matrix is same for edges and faces
        mvpMatrixHandle =
            GLES20.glGetUniformLocation(mProgram, "uMVPMatrix").also {
                GLES20.glUniformMatrix4fv(it, 1, false, mvpMatrix, 0)
            }

//        Matrix.rotateM(rotationMatrix, 0, 180f, 0f, 0f, 1f)

        rotationMatrixHandle =
            GLES20.glGetUniformLocation(mProgram, "uRotationMatrix").also {
                GLES20.glUniformMatrix4fv(it, 1, false, rotationMatrix, 0)
            }

        // get handle for position and color
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")

        // set buffer for drawing faces
        GLES20.glVertexAttribPointer(
            positionHandle,
            COORDINATES_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            0,
            vertexBuffer
        )
        GLES20.glEnableVertexAttribArray(positionHandle)

        // draw edges
        GLES20.glLineWidth(5.3f)
        GLES20.glUniform4fv(colorHandle, 1, edgeColor, 0)
        GLES20.glDrawElements(
            GLES20.GL_LINES,
            edgeOrder.size,
            GLES20.GL_UNSIGNED_SHORT,
            edgeElementBuffer
        )

        // draw faces
        GLES20.glUniform4fv(colorHandle, 1, faceColor, 0)
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLES,
            faceOrder.size,
            GLES20.GL_UNSIGNED_SHORT,
            faceElementBuffer
        )


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}