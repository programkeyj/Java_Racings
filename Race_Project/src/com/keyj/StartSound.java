package com.keyj;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class StartSound extends Thread{

    File file;

    @Override
    public void run(){
        File start = file;
        StartSound.Play(start);
    }
    public static void Play(File sound){
        try{
            Clip clip = AudioSystem.getClip();

            clip.open(AudioSystem.getAudioInputStream(sound));

            clip.start();

            Thread.sleep(clip.getMicrosecondLength()/1000);

            clip.stop();
        }catch (Exception e){

        }
    }
}
