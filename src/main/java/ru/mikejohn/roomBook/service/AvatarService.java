package ru.mikejohn.roomBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mikejohn.roomBook.model.Avatar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class AvatarService {
    @Autowired
    private Avatar avatar;

    private final String avatarPath = "src/main/resources/static/images/";

    public void makeAvatar(String userName){
        try {

            String fullPath = avatarPath+userName+".png";
            ImageIO.write(avatar.generateImage(), "png", new File(fullPath));
        } catch (IOException | NullPointerException e) {
            String localPath = "C:/Temp/test.png";
            try {
                    ImageIO.write(avatar.generateImage(), "png", new File(localPath));
                }catch (IOException | NullPointerException e2) {
                e2.printStackTrace();
                }
            e.printStackTrace();
        }
    }
}
