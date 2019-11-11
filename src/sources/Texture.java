package sources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_8_8_8_8_REV;

class Texture {

    int id;

    // creates texture from shrunken sprite
    Texture(BufferedImage sprite) {

        // get RGB values from buffered image
        int[] pixels_raw;
        pixels_raw = sprite.getRGB(0, 0, sprite.getWidth(),
                sprite.getHeight(), null, 0, sprite.getWidth());

        this.id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // create texture image
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, sprite.getWidth(),
                sprite.getHeight(), 0, GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV, pixels_raw);
    }

    // creates texture from filePath
    Texture(String filePath) throws IOException {
        this(ImageIO.read(new File(filePath)));
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
}