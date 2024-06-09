//This class is used for creating a Video object

public class Video {
    private int video_number;
    private String video_title;
    private boolean available;
    private int borrower_number;
    private String borrower_name;

    //Create a video constructor
    public Video(int video_number, String video_title) {
        this.video_number = video_number;
        this.video_title = video_title;
        this.available = true; // In general, a newly added video is available to be borrowed
    }

    // Declare methods
    public int getVideoNumber() {
        return video_number;
    }

    public String getVideoTitle() {
        return video_title;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getBorrowerNumber() {
        return borrower_number;
    }

    public String getBorrowerName() {
        return borrower_name;
    }

    public void setVideoTitle(String video_title) {
        this.video_title = video_title;
    }

    public void borrowVideo(int borrower_number, String borrower_name) {
        this.available = false;
        this.borrower_number = borrower_number;
        this.borrower_name = borrower_name;
    }

    public String toString() {
        if (available) {
            return "Video Number: " + video_number + ", Video Title: '" 
                + video_title + "' is available";
        } else {
            return "Video Number: " + video_number + ", Video Title: " + video_title 
                + " is borrowed by " + borrower_name + " (Borrower Number: " + borrower_number + ")";
        }
    }
}