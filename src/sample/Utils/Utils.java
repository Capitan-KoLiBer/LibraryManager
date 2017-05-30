package sample.Utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.omg.CORBA.Environment;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public final static String main_path = System.getProperty("user.home");

    public static final class Initializator{

        public static void initFileSystem(){
            try {
                new File(main_path+"/LibraryManager").mkdirs();
                new File(main_path+"/LibraryManager/Books").mkdirs();
                new File(main_path+"/LibraryManager/BooksGraph").mkdirs();
                new File(main_path+"/LibraryManager/Members").mkdirs();
                new File(main_path+"/LibraryManager/MembersGraph").mkdirs();
                new File(main_path+"/LibraryManager/Data").mkdirs();
                new File(main_path+"/LibraryManager/Users").mkdirs();
                File language = new File(main_path+"/LibraryManager/Data","Language");
                if(!language.exists()){
                    language.createNewFile();
                    Filer.writeFile(language.getPath(),"1");
                }
            } catch (Exception ignored) {}
        }

    }

    public static final class Auth{

        public static void setUser(String username,String password,String name){
            try {
                String user_path = main_path+"/LibraryManager/Users/"+username;
                Filer.writeFile(user_path,password+"\t"+name);
            }catch (Exception ignored){}
        }

        public static String[] getUser(String username){
            try{
                String user_path = main_path+"/LibraryManager/Users/"+username;
                String data = Filer.readFile(user_path);
                return new String[]{username,data.split("\t")[0],data.split("\t")[1]};
            }catch (Exception e) {
                return null;
            }
        }

    }

    public static final class Dialogs{

        public static void showDialog(String title, String text, StackPane root_pane){
            JFXDialog message_dialog = new JFXDialog();
            GridPane dialog_pane = new GridPane();
            if(Language.getLanguage() != 1){
                dialog_pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
            dialog_pane.setPadding(new Insets(10));
            dialog_pane.setVgap(10);
            dialog_pane.setHgap(10);
            Label title_label = new Label(title);
            Label text_label = new Label(text);
            title_label.setStyle("-fx-font-weight: bold;-fx-font-size: 15");
            text_label.setStyle("-fx-font-weight: bold;-fx-font-size: 12");
            JFXButton ok_button = new JFXButton(Language.getLanguage() == 1 ? "Ok" : "باشه");
            ok_button.setStyle("-fx-font-weight: bold;-fx-background-color: #29b6f6 ; -jfx-button-type: RAISED;-fx-text-fill: white;");
            ok_button.setOnAction(value -> {
                message_dialog.close();
            });
            dialog_pane.add(title_label,0,0);
            dialog_pane.add(text_label,0,1);
            dialog_pane.add(ok_button,0,2);
            message_dialog.setPadding(new Insets(10));
            message_dialog.setContent(dialog_pane);
            message_dialog.show(root_pane);
        }

    }

    public static final class Language{

        public static int getLanguage(){
            try{
                FileInputStream reader = new FileInputStream(main_path+"/LibraryManager/Data/Language");
                char read = (char)reader.read();
                if(read == '0'){
                    return 0;
                }else{
                    return 1;
                }
            }catch (Exception e){
                return 0;
            }
        }

        public static void setLanguage(int lang){
            try{
                FileOutputStream writer = new FileOutputStream(main_path+"/LibraryManager/Data/Language");
                writer.write((lang+"").getBytes());
            }catch (Exception ignored){}
        }

    }

    public static final class Image{

        public static String getRandomBookImage(){
            Random r = new Random();
            return "book"+r.nextInt(4)+".png";
        }

        public static String getMemberImage(boolean isMale){
            if(isMale){
                return "male.png";
            }else{
                return "female.png";
            }
        }

    }

    public static final class Dater{

        public static String nowDate(){
            DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
            return formater.format(new Date());
        }

        public static String dateDistance(String date){
            try {
                DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
                Date before_date = formater.parse(date);
                Date now_date = new Date();
                long difference = now_date.getTime() - before_date.getTime();
                long differenceDates = difference / (24 * 60 * 60 * 1000);
                String dayDifference = Long.toString(differenceDates);
                return dayDifference;
            } catch (Exception e) {
                return null;
            }
        }

    }

    public static final class Filer{

        public static String readFile(String file_path){
            try{
                FileInputStream reader = new FileInputStream(file_path);
                String data = "";
                byte[] buffer = new byte[1024];
                int read_len;
                while((read_len = reader.read(buffer)) > 0){
                    data += new String(buffer,0,read_len);
                }
                return data;
            }catch (Exception e){
                return null;
            }
        }

        public static void writeFile(String file_path,String data){
            try{
                FileOutputStream writer = new FileOutputStream(file_path);
                writer.write(data.getBytes());
                writer.flush();
                writer.close();
            }catch (Exception ignored){}
        }

        public static void appendFile(String file_path,String data){
            try{
                FileWriter fwriter = new FileWriter(file_path,true);
                BufferedWriter writer = new BufferedWriter(fwriter);
                writer.write(data);
                writer.flush();
                writer.close();
                fwriter.flush();
                fwriter.close();
            }catch (Exception ignored){}
        }

        public static String baseEncode(String text){
            return new String(Base64.getEncoder().encode(text.getBytes()));
        }

        public static String baseDecode(String base){
            return new String(Base64.getDecoder().decode(base.getBytes()));
        }

    }

    public static final class Member{

        public static boolean isMember(String member_id){
            try {
                return new File(main_path+"/LibraryManager/Members/"+member_id).exists();
            }catch (Exception e){
                return false;
            }
        }

        public static boolean setMember(String member_name , String member_gender , String member_id , String member_date){
            try{
                File member_file = new File(main_path+"/LibraryManager/Members",member_id);
                if(!member_file.exists()){
                    member_file.createNewFile();
                    new File(main_path+"/LibraryManager/MembersGraph",member_id).createNewFile();
                }
                String data = member_name.replace("\t","\\t")+"\t"+member_gender+"\t"+member_date;
                Filer.writeFile(member_file.getPath(),data);
                return true;
            }catch (Exception ignored){
                return false;
            }
        }

        public static void deleteMember(String member_id){
            try{
                new File(main_path+"/LibraryManager/Members/"+member_id).delete();
                new File(main_path+"/LibraryManager/MembersGraph/"+member_id).delete();
            }catch (Exception ignored){}
        }

        public static ArrayList<HashMap<String,String>> getMembers(){
            // KEYS -> MEMBER_NAME , MEMBER_GENDER , MEMBER_ID , MEMBER_DATE
            try {
                ArrayList<HashMap<String,String>> members = new ArrayList<>();
                File members_folder = new File(main_path+"/LibraryManager/Members");
                for(File member_file : members_folder.listFiles()) {
                    String member_data = Filer.readFile(member_file.getPath());
                    // MEMBER_NAME\tMEMBER_GENDER\tMEMBER_DATE
                    HashMap<String,String> member_map = new HashMap<>();
                    member_map.put("MEMBER_NAME",member_data.split("\t")[0]);
                    member_map.put("MEMBER_GENDER",member_data.split("\t")[1]);
                    member_map.put("MEMBER_DATE",member_data.split("\t")[2]);
                    member_map.put("MEMBER_ID",member_file.getName());
                    members.add(member_map);
                }
                return members;
            }catch (Exception e){
                return null;
            }
        }

        public static HashMap<String,String> getMember(String member_id){
            // KEYS -> start date -> (start_date, book_name \t days_count )
            try {
                File member_graph = new File(main_path+"/LibraryManager/MembersGraph/"+member_id);
                String member_data = Filer.readFile(member_graph.getPath());
                HashMap<String,String> member_map = new HashMap<>();
                for(String book_reserved : member_data.split("\n")){
                    try {
                        String start_date = book_reserved.split("\t")[0];
                        String book_name = Filer.baseDecode(book_reserved.split("\t")[1]);
                        String days_count = book_reserved.split("\t")[2];
                        member_map.put(start_date,book_name+"\t"+days_count);
                    }catch (Exception ignored){}
                }
                return member_map;
            }catch (Exception e){
                return null;
            }
        }

    }

    public static final class Book{

        public static boolean setBook(String book_name, String book_author , String book_publisher){
            try{
                File book_file = new File(main_path+"/LibraryManager/Books",Filer.baseEncode(book_name));
                if(book_file.exists()){
                    // edit -> reserve -> set
                    String reserved = Filer.readFile(book_file.getPath()).split("\t")[2];
                    book_file.delete();
                    book_file.createNewFile();
                    String data = book_author.replace("\t","\\t")+"\t"+book_publisher.replace("\t","\\t")+"\t"+reserved;
                    Filer.writeFile(book_file.getPath(),data);
                }else{
                    book_file.createNewFile();
                    String data = book_author.replace("\t","\\t")+"\t"+book_publisher.replace("\t","\\t")+"\tF";
                    Filer.writeFile(book_file.getPath(),data);
                }
                return true;
            }catch (Exception ignored){
                return false;
            }
        }

        public static void deleteBook(String book_name){
            try{
                new File(main_path+"/LibraryManager/Books/"+Filer.baseEncode(book_name)).delete();
                new File(main_path+"/LibraryManager/BooksGraph/"+Filer.baseEncode(book_name)).delete();
            }catch (Exception ignored){}
        }

        public static ArrayList<HashMap<String , String>> getBooks(){
            // KEYS -> BOOK_NAME , BOOK_AUTHOR , BOOK_PUBLISHER , BOOK_RESERVED
            try {
                ArrayList<HashMap<String,String>> books = new ArrayList<>();
                File books_folder = new File(main_path+"/LibraryManager/Books");
                for(File book_file : books_folder.listFiles()) {
                    String book_data = Filer.readFile(book_file.getPath());
                    // MEMBER_NAME\tMEMBER_GENDER\tMEMBER_DATE
                    HashMap<String,String> book_map = new HashMap<>();
                    book_map.put("BOOK_AUTHOR",book_data.split("\t")[0]);
                    book_map.put("BOOK_PUBLISHER",book_data.split("\t")[1]);
                    book_map.put("BOOK_RESERVED",book_data.split("\t")[2]);
                    book_map.put("BOOK_NAME",Filer.baseDecode(book_file.getName()));
                    books.add(book_map);
                }
                return books;
            }catch (Exception e){
                return null;
            }
        }

        public static boolean serveBook(String book_name, boolean isRequest , String member_id){
            try{
                String book_path = main_path+"/LibraryManager/Books/"+Filer.baseEncode(book_name);
                String member_graph = main_path+"/LibraryManager/MembersGraph/"+member_id;
                String book_graph = main_path+"/LibraryManager/BooksGraph/"+Filer.baseEncode(book_name);
                String[] data = Filer.readFile(book_path).split("\t");
                if(isRequest){
                    Filer.writeFile(book_path,data[0]+"\t"+data[1]+"\tT"+member_id);
                    Filer.appendFile(member_graph,"\n"+Dater.nowDate()+"\t"+Filer.baseEncode(book_name));
                    Filer.appendFile(book_graph,"\n"+Dater.nowDate()+"\t"+member_id);
                }else{
                    Filer.writeFile(book_path,data[0]+"\t"+data[1]+"\tF");
                    String book_graph_data = Filer.readFile(book_graph);
                    String book_graph_data_last_line = book_graph_data.split("\n")[book_graph_data.split("\n").length-1];
                    member_graph = main_path+"/LibraryManager/MembersGraph/"+book_graph_data_last_line.split("\t")[1];
                    String date_distance = Dater.dateDistance(book_graph_data_last_line.split("\t")[0]);
                    Filer.appendFile(member_graph,"\t"+date_distance);
                    Filer.appendFile(book_graph,"\t"+date_distance);
                }
                return true;
            }catch (Exception e){
                return false;
            }
        }

        public static HashMap<String,String> getBook(String book_name){
            // KEYS -> start date -> (start_date, member_id \t days_count )
            try {
                File book_graph = new File(main_path+"/LibraryManager/BooksGraph/"+Filer.baseEncode(book_name));
                String book_data = Filer.readFile(book_graph.getPath());
                HashMap<String,String> book_map = new HashMap<>();
                for(String member_reserver : book_data.split("\n")){
                    try {
                        String start_date = member_reserver.split("\t")[0];
                        String member_id = member_reserver.split("\t")[1];
                        String days_count = member_reserver.split("\t")[2];
                        book_map.put(start_date,member_id+"\t"+days_count);
                    }catch (Exception ignored){}
                }
                return book_map;
            }catch (Exception e){
                return null;
            }
        }

    }

}
