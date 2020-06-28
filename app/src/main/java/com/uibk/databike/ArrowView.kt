package com.uibk.databike

import android.content.Context
import android.opengl.GLSurfaceView
import com.uibk.databike.ArrowRenderer

class ArrowView(context: Context, rotationMatrix: FloatArray) : GLSurfaceView(context) {
    private val renderer: ArrowRenderer

    init {
        setEGLContextClientVersion(2)
        renderer = ArrowRenderer(rotationMatrix)
        setRenderer(renderer)
    }
}