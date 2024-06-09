//This class is to crearte an array list to store and modify Video objects

import java.util.*;

public class VideoLibrary {
    static ArrayList<Video> videos= new ArrayList<Video>(); // create a video list to store video objects
    private int current_index=0;
    Scanner scan = new Scanner(System.in);

    public void addVideo(Video video_name) {
        videos.add(video_name);
    }

    public void removeVideo(int video_number) {
        videos.removeIf(video -> video.getVideoNumber() == video_number);
    }

    public Video getVideo(int video_number) {
        for (Video video : videos) {
            if (video.getVideoNumber() == video_number) {
                return video;
            }
        }
        return null;
    }

    public void updateVideo(int video_number, String new_vid_title) {
        Video udpate_video = getVideo(video_number);
        if (udpate_video != null && udpate_video == this.getVideo(video_number)) {
            udpate_video.setVideoTitle(new_vid_title);
        }
        else{
            System.out.println("Please recheck the current available videos to update");
        }

        // if update_vid doesn't exist (invalid video number), ask users to reenter another video number
    }

    public ArrayList<Video> listAllVideos() {
        return videos;
    }

    public ArrayList<Video> listAvailableVideos() {
        ArrayList<Video> available_videos = new ArrayList<>();
        for (Video video : videos) {
            if (video.isAvailable()) {
                available_videos.add(video);
            }
        }
        return available_videos;
    }

    public int countAllVideos(){
        return videos.size();
    }

    public Video searchVideoByTitle(String vid_title) {
        for (Video video : videos) {
            if (video.getVideoTitle().equalsIgnoreCase(vid_title)) {
                return video;
            }
        }
        return null;
    }


    // To navigate through the Main Menu
    // current video is assumed to be the first object in the list
    public Video getCurrentVideo() {
        if (current_index >= 0 && current_index < videos.size()){
            return videos.get(current_index);
        }
        return null;
    }

    public Video getNextVideo() {
        if (current_index < videos.size() - 1){
            current_index++;
            return videos.get(current_index);
        }
        return null;
    }

    public Video getPreviousVideo() {
        if (current_index > 0){
            current_index--;
            return videos.get(current_index);
        }
        return null;
    }
}