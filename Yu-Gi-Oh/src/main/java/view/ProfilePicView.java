package view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfilePicView {

    private final List<String> profilePicPaths;
    private final List<Image> profilePics = new ArrayList<>();
    private int currentProfilePicIndex = 0;

    @FXML
    private ImageView profilePic;

    public ProfilePicView() throws FileNotFoundException {
        File folder = new File("src/main/resources/image/profile");
        profilePicPaths = Arrays.stream(Objects.requireNonNull(folder.listFiles())).map(File::getPath)
                .collect(Collectors.toList());
        for (String profilePicPath : profilePicPaths)
            profilePics.add(new Image(new FileInputStream(profilePicPath)));
    }

    @FXML
    public void initialize() throws FileNotFoundException {
        profilePic.setImage(new Image(new FileInputStream(profilePicPaths.get(currentProfilePicIndex))));
    }

    public String getProfilePicPath() {
        return profilePicPaths.get(currentProfilePicIndex);
    }

    public void showPreviousProfilePic() {
        if (currentProfilePicIndex > 0)
            currentProfilePicIndex--;
        else
            currentProfilePicIndex = profilePicPaths.size() - 1;

        profilePic.setImage(profilePics.get(currentProfilePicIndex));
    }

    public void showNextProfilePic() {
        if (currentProfilePicIndex < profilePicPaths.size() - 1)
            currentProfilePicIndex++;
        else
            currentProfilePicIndex = 0;

        profilePic.setImage(profilePics.get(currentProfilePicIndex));
    }
}
