//16.04.2016


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;


public class MyScore implements Runnable{
    private ConcurrentSkipListMap<String,Integer> map = new ConcurrentSkipListMap<>();
    private ArrayList<Match> matches  = new ArrayList();
    private File file = new File("chromedriver.exe");
    private String URL= "jdbc:mysql://localhost:3306/url";
    private String USER = "root";
    private String ID = "root";
    public void run() {
        final WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    while(true){
        driver.navigate().to("http://t.myscore.ru/#!/football/live");
        driver.navigate().refresh();
        for (WebElement el : driver.findElements(By.cssSelector("[class^=' stage-live']"))) {
            String str = el.getAttribute("id");
            if(str.startsWith("g")) {
                Integer time = 0;
                String test_time = el.findElement(By.cssSelector("[class^='cell_aa timer']")).getText().trim();
                if (test_time.contains("е") || test_time.contains("+")) {
                    time = 45;
                } else if (test_time.length() < 4 && test_time.length() > 0) {
                    time = Integer.valueOf(test_time);
                }
               map.put(str,time);
            }
        }
        System.out.println(map);
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
//            int fstscore = entry.getValue().get(entry.getValue().lastKey()).lastKey();
//            int scndscore = entry.getValue().get(entry.getValue().lastKey()).get(entry.getValue().get(entry.getValue().lastKey()).lastKey());
//            if (entry.getValue().lastKey() > Main.time1 && entry.getValue().lastKey() < Main.time2 && fstscore == 0 && scndscore == 0) {
//                makeMatch(driver, entry.getKey(), 0, 0,0);
//            }
//            if(entry.getValue().lastKey() < 30){
//                if(fstscore + scndscore  > 1){
//                    lowTotal(driver, entry.getKey(), fstscore+scndscore);
//                }
//            }
//            if(entry.getValue().lastKey() >= 30 && entry.getValue().lastKey() < 60){
//            switch(fstscore+scndscore) {
//                case 0:
//                case 1:
//                case 2:
//
//            }
//            }
//            if(entry.getValue().lastKey() >= 60 && entry.getValue().lastKey() < 90){
//            }
            if(entry.getValue()>30){
                Match match = new Match(entry.getKey(), driver, entry.getValue());
                matches.add(match);
            }
        }
        deleteDB();
        for(Match mtch:matches){
        if(mtch.TOTAL!=null){
            //System.out.print(mtch.URL);
            addDB(mtch.URL,mtch.TOTAL);
        }
        }
        try {
            driver.navigate().to("http://t.myscore.ru/");
            map.clear();
            matches.clear();
            System.out.println("Sleep");
            Thread.sleep(420000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
    private void lowTotal(WebDriver driver1,String url){
        int max1 = 0;
        int max2 = 0;
        int max3 = 0;
        int scorenow = 0;
        driver1.navigate().to("http://t.myscore.ru/#!/match/" + url.substring(4, url.length()) + "/h2h;1");
        driver1.navigate().refresh();
        try {
            WebElement el = driver1.findElement(By.cssSelector("[class^='score']"));
            if(el.getText().length()==3){
                scorenow = Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
            }
        }catch(NoSuchElementException ex){
            scorenow = 0;
        }
        for (WebElement el : driver1.findElements(By.cssSelector("[class^='1']>[class^='score']"))) {
        if(Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)))>max1){
            max1 = Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
        }
        }
        for (WebElement el : driver1.findElements(By.cssSelector("[class^='2']>[class^='score']"))) {
            if(Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)))>max1){
                max2 = Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
            }
        }
        for (WebElement el : driver1.findElements(By.cssSelector("[class^='3']>[class^='score']"))) {
            if(Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)))>max1){
                max3 = Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
            }
        }
        if((max1+max2+max3)/3-1>scorenow){
            addDB("http://t.myscore.ru/#!/match/"+url.substring(4,url.length())+"/h2h","Тотал "+Math.round((max1+max2+max3)/3)+" меньше");
        }
    }//TODO LOWTOTAL

    private void deleteDB(){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL,USER,ID); Statement statement = connection.createStatement()){
            statement.execute("TRUNCATE TABLE url_match");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addDB(String match,String total){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL,USER,ID); Statement statement = connection.createStatement()){
            statement.execute("INSERT INTO url_match (match_url,total) VALUES ('"+match+"','"+total+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    }


