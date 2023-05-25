package com.vydra.death.screen.manager;

import com.vydra.death.screen.Main;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class IconTitleManager {

    public IconTitleManager() {
        setupIcon();
        setTitle("VydraHack");
    }


    public void setupIcon() {
        try (InputStream inputStream16x = Main.class.getClassLoader().getResourceAsStream("assets/icon16.png");
             InputStream inputStream32x = Main.class.getClassLoader().getResourceAsStream("assets/icon32.png")) {
            ByteBuffer[] icons = new ByteBuffer[]{readImageToBuffer(inputStream16x), readImageToBuffer(inputStream32x)};
            Display.setIcon(icons);
        } catch (Exception e) {
            System.out.println("Icon Exception: " + e);
        }
    }

    public void setTitle(String t) {
        Display.setTitle(t);
    }


    private ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(inputStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        Arrays.stream(aint).map(i -> i << 8 | (i >> 24 & 255)).forEach(bytebuffer::putInt);
        ((Buffer) bytebuffer).flip();
        return bytebuffer;
    }
}
