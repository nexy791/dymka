package com.ribsky.common.utils.sound

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer

@SuppressLint("StaticFieldLeak")
object SoundHelper {

    private var listMp = mutableListOf<MediaPlayer>()
    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
    }


    fun playSound(id: Int) {
        listMp.add(MediaPlayer.create(context, id).apply {
            setOnCompletionListener {
                release()
                listMp.remove(this)
            }
            setVolume(1f, 1f)
            start()
        })
    }

}