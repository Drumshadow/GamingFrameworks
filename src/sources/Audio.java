package sources;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Audio {
    String fileName;

    Audio(){}
    Audio(String file){
        this.fileName = file;
    }

    void loadPlaySound(){
        //Initialization
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        long   device            = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        long  context    = alcCreateContext(device, attributes);
        alcMakeContextCurrent(context);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
        ALCapabilities alCapabilities  = AL.createCapabilities(alcCapabilities);

        ShortBuffer rawAudioBuffer;

        int channels;
        int sampleRate;

        try (MemoryStack stack = stackPush()) {
            //Allocate space to store return information from the function
            IntBuffer channelsBuffer   = stack.mallocInt(1);
            IntBuffer sampleRateBuffer = stack.mallocInt(1);

            rawAudioBuffer = stb_vorbis_decode_filename(fileName, channelsBuffer, sampleRateBuffer);

            //Retreive the extra information that was stored in the buffers by the function
            channels = channelsBuffer.get(0);
            sampleRate = sampleRateBuffer.get(0);
        }

        //Find the correct OpenAL format
        int format = -1;
        if (channels == 1) {
            format = AL_FORMAT_MONO16;
        } else if (channels == 2) {
            format = AL_FORMAT_STEREO16;
        }

        //Request space for the buffer
        int bufferPointer = alGenBuffers();

        //Send the data to OpenAL
        alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

        //Free the memory allocated by STB
        free(rawAudioBuffer);

        //Request a source
        int sourcePointer = alGenSources();

        //Assign the sound we just loaded to the source
        alSourcei(sourcePointer, AL_BUFFER, bufferPointer);

        //Play the sound
        alSourcePlay(sourcePointer);

        try {
            //Wait for a second
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        //Terminate OpenAL
        alDeleteSources(sourcePointer);
        alDeleteBuffers(bufferPointer);
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    void playSound(){
        //might not need this function, but im leaving it here to be safe
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
