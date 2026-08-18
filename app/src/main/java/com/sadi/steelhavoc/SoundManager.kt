package com.sadi.steelhavoc

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool

object SoundManager {

    private var menuPlayer: MediaPlayer? = null
    private var battlePlayer: MediaPlayer? = null

    private var soundPool: SoundPool? = null

    private var gunSoundId: Int = 0
    private var explosionSoundId: Int = 0

    private var soundsLoaded = false

    // =========================
    // INITIALIZE EVERYTHING
    // =========================

    fun initialize(
        context: Context
    ) {

        // Prepare music early
        if (menuPlayer == null) {

            menuPlayer =
                MediaPlayer.create(
                    context,
                    R.raw.menu_music
                )

            menuPlayer?.isLooping = true

            // Louder
            menuPlayer?.setVolume(
                0.85f,
                0.85f
            )
        }

        if (battlePlayer == null) {

            battlePlayer =
                MediaPlayer.create(
                    context,
                    R.raw.battle_music
                )

            battlePlayer?.isLooping = true

            // Louder
            battlePlayer?.setVolume(
                0.85f,
                0.85f
            )
        }

        // Sound effects
        if (soundPool == null) {

            soundPool =
                SoundPool.Builder()
                    .setMaxStreams(8)
                    .build()

            gunSoundId =
                soundPool!!.load(
                    context,
                    R.raw.gun_fire,
                    1
                )

            explosionSoundId =
                soundPool!!.load(
                    context,
                    R.raw.car_explosion,
                    1
                )

            soundPool!!
                .setOnLoadCompleteListener {
                        _,
                        _,
                        _ ->

                    soundsLoaded = true
                }
        }
    }

    // =========================
    // MENU MUSIC
    // =========================

    fun playMenuMusic() {

        stopBattleMusic()

        menuPlayer?.let {

            if (!it.isPlaying) {

                // Start from beginning
                it.seekTo(0)

                it.start()
            }
        }
    }

    fun stopMenuMusic() {

        menuPlayer?.let {

            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    // =========================
    // BATTLE MUSIC
    // =========================

    fun playBattleMusic() {

        stopMenuMusic()

        battlePlayer?.let {

            if (!it.isPlaying) {

                it.seekTo(0)

                it.start()
            }
        }
    }

    fun stopBattleMusic() {

        battlePlayer?.let {

            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    // =========================
    // GUN SOUND
    // =========================

    fun playGunFire() {

        if (
            soundsLoaded &&
            gunSoundId != 0
        ) {

            soundPool?.play(
                gunSoundId,
                1f,
                1f,
                1,
                0,
                1f
            )
        }
    }

    // =========================
    // EXPLOSION SOUND
    // =========================

    fun playExplosion() {

        if (
            soundsLoaded &&
            explosionSoundId != 0
        ) {

            soundPool?.play(
                explosionSoundId,
                1f,
                1f,
                1,
                0,
                1f
            )
        }
    }

    // =========================
    // RELEASE
    // =========================

    fun releaseAll() {

        menuPlayer?.release()
        menuPlayer = null

        battlePlayer?.release()
        battlePlayer = null

        soundPool?.release()
        soundPool = null

        soundsLoaded = false
    }
}