package sources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    // stores texture handle
    private int id;

    private int width;
    private int height;

    // creates texture from file
    public Texture(String filename) {
        BufferedImage bi;

        try {
            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();

            int[] pixels_raw;
            pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {
                    int pixel = pixels_raw[i * width + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF));
                    pixels.put((byte) ((pixel >> 8) & 0xFF));
                    pixels.put((byte) (pixel & 0xFF));
                    pixels.put((byte) ((pixel >> 24) & 0xFF));
                }
            }

            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
                    GL_UNSIGNED_BYTE, pixels);

        }catch(IOException e) {
            System.err.println("Cannot find texture");
        }
    }

    // creates texture from shrunken sprite
    public Texture(BufferedImage sprite) {

        this.width = sprite.getWidth();
        this.height = sprite.getHeight();

        int[] pixels_raw;
        pixels_raw = sprite.getRGB(0, 0, width, height, null, 0, width);

        ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int pixel = pixels_raw[i * width + j];
                pixels.put((byte) ((pixel >> 16) & 0xFF));
                pixels.put((byte) ((pixel >> 8) & 0xFF));
                pixels.put((byte) (pixel & 0xFF));
                pixels.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        pixels.flip();

        id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, pixels);
    }

    // binds texture
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
}