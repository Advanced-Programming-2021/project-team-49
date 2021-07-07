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

    private final List<String> profilePicResourcePaths;
    private final List<Image> profilePics = new ArrayList<>();
    private int currentProfilePicIndex = 0;

    @FXML
    private ImageView profilePic;

    public ProfilePicView() {
        File folder = new File(Objects.requireNonNull(getClass().getResource("/image/profile")).getPath());
        profilePicResourcePaths = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .map(file -> "/image/profile/" + file.getName()).collect(Collectors.toList());
        for (String profilePicResourcePath : profilePicResourcePaths)
            profilePics.add(new Image(
                    Objects.requireNonNull(getClass().getResource(profilePicResourcePath)).toExternalForm()));
    }

    @FXML
    public void initialize() {
        profilePic.setImage(profilePics.get(currentProfilePicIndex));
    }

    public String getProfilePicResourcePath() {
        return profilePicResourcePaths.get(currentProfilePicIndex);
    }

    public void showPreviousProfilePic() {
        if (currentProfilePicIndex > 0)
            currentProfilePicIndex--;
        else
            currentProfilePicIndex = profilePicResourcePaths.size() - 1;

        profilePic.setImage(profilePics.get(currentProfilePicIndex));
    }

    public void showNextProfilePic() {
        if (currentProfilePicIndex < profilePicResourcePaths.size() - 1)
            currentProfilePicIndex++;
        else
            currentProfilePicIndex = 0;

        profilePic.setImage(profilePics.get(currentProfilePicIndex));
    }
}
