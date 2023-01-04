package com.github.seastar.wdm.service.captcha.built;

import com.github.seastar.wdm.io.WdmBytesIO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**********************************
 * Date: 2022/12/28
 * Author: hchery
 * Home: https://github.com/hchery
 *********************************/
public record ImageCreator(int width, int height, int lineCount, String val) {

    private static final Map<Integer, Font> FONT_CACHE = new ConcurrentHashMap<>();

    private static Font getFont(int h) {
        try(var fIn = new WdmBytesIO("captcha.ttf").getInputStream()) {
            var baseFont = Font.createFont(Font.TRUETYPE_FONT, fIn);
            return baseFont.deriveFont(Font.PLAIN, h);
        } catch (Exception e) {
            return new Font("Arial", Font.PLAIN, h);
        }
    }

    public byte[] create(String fmt) throws IOException {
        var code = val.toCharArray();
        var x = width / (code.length + 2);
        var y = height - 2;
        var h = height - 4;
        var buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        var graphics = buffImage.createGraphics();
        var random = new Random();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(FONT_CACHE.computeIfAbsent(h, ImageCreator::getFont));
        for (var i = 0; i < lineCount; i ++) {
            var xs = random.nextInt(width);
            var ys = random.nextInt(height);
            var xe = xs + random.nextInt(width / 8);
            var ye = ys + random.nextInt(height / 8);
            graphics.setColor(nextColor());
            graphics.drawLine(xs, ys, xe, ye);
        }
        for (var i = 0; i < code.length; i ++) {
            graphics.setColor(nextColor());
            graphics.drawString(String.valueOf(code[i]), (i + 1) * x, y);
        }
        try (var out = new ByteArrayOutputStream()) {
            ImageIO.write(buffImage, fmt, out);
            return out.toByteArray();
        }
    }

    private static Color nextColor() {
        var random = new Random();
        var r = random.nextInt(255);
        var g = random.nextInt(255);
        var b = random.nextInt(255);
        return new Color(r, g, b);
    }
}
