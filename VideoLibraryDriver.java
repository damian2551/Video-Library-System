//This is the driver class for VideoLibrary

import java.util.*;
import java.io.*;

public class VideoLibraryDriver {
    static VideoLibrary videoLibrary = new VideoLibrary();
    static Scanner scan = new Scanner(System.in);
    static int valid_number; 
    
    // Main menu for user
    public static void MainMenu() {
        System.out.println("\nVideo Library System");
        System.out.println("1. Add New Video");
        System.out.println("2. Borrow Video");
        System.out.println("3. Modify Video Record");
        System.out.println("4. Delete Video Record");
        System.out.println("5. Search Video");
        System.out.println("6. Report of Available Videos");
        System.out.println("7. Navigate Videos");
        System.out.println("8. Exit");
        System.out.print("Enter your option (type the number only): ");
    }
    
    public static void main(String[] args) throws IOException{


        //read the input file
        String current_video = "VideoFile.input";
        Scanner scan_input = new Scanner(new File(current_video));
        
        while (scan_input.hasNext()){
            int video_number = scan_input.nextInt();
            String video_title = scan_input.nextLine().trim();
            Video video = new Video(video_number, video_title);
            videoLibrary.addVideo(video);
        }
        scan_input.close();
        System.out.println("Reading is done !!");
        System.out.println(displayVideoInput());

        //Option Menu for users:
        while (true) {
            MainMenu();
            int user_input = getCheckValidInput(scan);
            
            scan.nextLine();
            System.out.print("");
            
            switch (user_input) {
                case 1:
                    addVideo();
                    break;
                case 2:
                    borrowVideo();
                    break;
                case 3:
                    modifyVideoRecord();
                    break;
                case 4:
                    deleteVideoRecord();
                    break;
                case 5:
                    searchVideo();
                    break;
                case 6:
                    reportAvailableVideos();
                    break;
                case 7:
                    navigateVideos();
                    break;
                case 8:
                    System.out.println("Exiting the library... ");
                    System.out.println("You exited the library. See you next time!!");
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
    
    //display video input
    public static String displayVideoInput(){
        System.out.println("\n** Displaying the list: ");
        int num = 1;
        
        while (num <= videoLibrary.countAllVideos()){
            System.out.println(videoLibrary.getVideo(num));
            num ++;
        }
        return "";
    }

    //Check whether input is valid (input is not empty and is not a String type)
    public static int getCheckValidInput(Scanner scan){
        boolean Valid = true;
        while(Valid){
            
            String input = scan.nextLine().trim();
            if (input.isEmpty())
            {
                System.out.print("Please put an integer value: ");
                continue;
            }
            else {
                try {
                    valid_number = Integer.parseInt(input);
                    Valid = false;
                }
                catch (NumberFormatException e) {
                    System.out.print("Invalid number, pleaser reenter: ");
                }
            }
        }
        return valid_number;
    }               
    
    //1. Add video
    private static void addVideo() {
        System.out.print("Enter video number: ");
        int video_number = getCheckValidInput(scan);

        Video video_check = videoLibrary.getVideo(video_number);
        
        //if user enter the same video number existing in the system, 
        // they will be asked to re-enter another number.
        if (video_check != null && video_number == video_check.getVideoNumber()){
            System.out.println("This video number is already in the system. Please type another number again.");
            addVideo();
        }
        else{
                System.out.print("Enter video title: ");
                String video_title = scan.nextLine();
                Video video = new Video(video_number, video_title);
                videoLibrary.addVideo(video);
                System.out.println("Your video was added successfully.");
            }
        }
    
    //2. Borrow Video
    private static void borrowVideo() {
        System.out.print("Enter video number to borrow: ");
        int video_number = getCheckValidInput(scan);
    
        Video video = videoLibrary.getVideo(video_number);
        
        if (video != null && video.isAvailable()) {
            System.out.print("Enter borrower number: ");
            int borrower_number = getCheckValidInput(scan);
            
            System.out.print("Enter borrower name: ");
            String borrower_name = scan.nextLine();
            video.borrowVideo(borrower_number, borrower_name);
            System.out.println("The video is borrowed successfully by " + borrower_name + 
                " with the borrower number "+ borrower_number);
        } 

        else {
            System.out.println("The video you chose is not available for borrowing.");
            reportAvailableVideos();
            System.out.println();
            System.out.println("Please pick the above Video Number");
            System.out.println();
            borrowVideo();
        }
    }

    //3. Modify Video Record
    private static void modifyVideoRecord() {
        System.out.print("Enter video number to modify: ");
        int video_number = getCheckValidInput(scan);
        
        Video video = videoLibrary.getVideo(video_number);
        
        //allow to modify available videos in the system
        if (video != null && video.isAvailable()) {
            System.out.print("Enter a new video title: ");
            String new_title = scan.nextLine();
            videoLibrary.updateVideo(video_number, new_title);
            System.out.println("Video record was updated successfully.");
        } 
        
        else {
            System.out.println("Video not found in the system.");
            reportAvailableVideos(); // show users available videos which can be modified
            System.out.println();
            System.out.println("Please pick the above Video Number");
            System.out.println();
            modifyVideoRecord();
        }
    }

    //4. Delete Video Record
    private static void deleteVideoRecord() {
        System.out.print("Enter video number to delete: ");
        int video_number = getCheckValidInput(scan);


        Video video = videoLibrary.getVideo(video_number);
        
        //allow to delete available videos in the system
        if(video != null && video_number == video.getVideoNumber() && video.isAvailable()){
            videoLibrary.removeVideo(video_number);
            System.out.println("Video record deleted successfully.");
        }

        else {
            System.out.println("The video number is not found to be deleted.");
            reportAvailableVideos();
            System.out.println();
            System.out.println("Please pick the above Video Number");
            System.out.println();
        }
    }

    //5. Search Video
    private static void searchVideo() {
        System.out.print("Enter video title to search: ");
        String video_title = scan.nextLine();
        Video video = videoLibrary.searchVideoByTitle(video_title);
        
        // any videos in the system can be searched, regardless of their status
        if (video != null) {
            System.out.println("Video is found: " + video);
        } 
        else {
            System.out.println("Video not found.");
            searchVideo();
        }
    }

    //6. Report of Available Videos
    private static void reportAvailableVideos() {
        System.out.println("\n These are the available videos:");
        for (Video video : videoLibrary.listAvailableVideos()) {
            System.out.println(video);
        }
    }

    //Menu to navigate to different videos
    private static void navigateVideos() {
        while (true) {
            System.out.println("\nNavigation Menu");
            System.out.println("1. Current Video");
            System.out.println("2. Next Video");
            System.out.println("3. Previous Video");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your option ( type the number only): ");
            int user_input = getCheckValidInput(scan);
            
            switch (user_input) {
                case 1:
                    Video current_video = videoLibrary.getCurrentVideo();
                    if (current_video != null) {
                        System.out.println("Current Video: " + current_video);
                    } 
                    else {
                        System.out.println("No current video.");
                    }
                    break;
                case 2:
                    Video next_video = videoLibrary.getNextVideo();
                    if (next_video != null) {
                        System.out.println("Next Video: " + next_video);
                    } 
                    else {
                        System.out.println("No more videos.");
                    }
                    break;
                case 3:
                    Video previous_video = videoLibrary.getPreviousVideo();
                    if (previous_video != null) {
                        System.out.println("Previous Video: " + previous_video);
                    } 
                    else {
                        System.out.println("No previous video.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }
}