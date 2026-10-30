package org.sopt.play

import android.app.Application
import org.sopt.play.di.LocalModule

class PlaySoptApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LocalModule.init(this)
    }
}
