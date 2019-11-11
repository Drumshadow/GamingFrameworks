package sources;

import org.lwjgl.system.MemoryStack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

class MultiThread implements Runnable
{
    private String file;
    void setFileName(String fileN){
        this.file = fileN;
    }

    @Override
    public void run() {

        ShortBuffer rawAudioBuffer;

        int channels;
        int sampleRate;

        try (MemoryStack stack = stackPush()) {

            // allocate space to store return information from the function
            IntBuffer channelsBuffer   = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);
            rawAudioBuffer = stb_vorbis_decode_filename(this.file, channelsBuffer, sampleRateBuffer);

            // retrieve the extra information that was stored in the buffers by the function
            channels = channelsBuffer.get(0);
            sampleRate = sampleRateBuffer.get(0);
        }

        // find the correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        // request space for the buffer
        int bufferPointer = alGenBuffers();

        // send the data to OpenAL
        alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

        // free the memory allocated by STB
        free(rawAudioBuffer);

        // request a source
        int sourcePointer = alGenSources();

        // assign the sound we just loaded to the source
        alSourcei(sourcePointer, AL_BUFFER, bufferPointer);

        // play the sound
        // thread this part
        alSourcePlay(sourcePointer);

        while(alGetSourcei(sourcePointer, AL_SOURCE_STATE) == AL_PLAYING) {
        }

        // terminate OpenAL
        alDeleteSources(sourcePointer);
        alDeleteBuffers(bufferPointer);
    }
}

public class Audio {

    String fileName;
    private String bgMusic;

    static String randomName = "BackgroundMusic";
    private static Clip clip = null;

    Audio(){}
    Audio(String file){
        this.fileName = file;
    }

    Audio(String file, String bg){
        this.fileName = file;
        this.bgMusic = bg;
    }

    void loadPlaySound(){
        MultiThread obj = new MultiThread();
        obj.setFileName(this.fileName);
        Thread mT = new Thread(obj);
        mT.start();
    }

    public String getFileName() {
        return fileName;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    void playSound(String name) throws Exception{

        if (clip != null && clip.isOpen()) {
            clip.close();
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
        clip = AudioSystem.getClip();

        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-24f);

        clip.start();
    }
}


