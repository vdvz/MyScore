import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

public class S1x implements Runnable{
    private  File file = new File("chromedriver.exe");
    private  ConcurrentSkipListMap<String,String> map = new ConcurrentSkipListMap<>();
    String IDEN = "root";
    String USER = "root";
    String URL = "jdbc:mysql://localhost:3306/url";
    @Override
    public void run(){
        final WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        while(true) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Driver driver1 = new FabricMySQLDriver();
            DriverManager.registerDriver(driver1);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, IDEN); Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery("select * from url.match");
            while(set.next()!=set.isLast()) {
            map.put(set.getString(1),set.getString(2));

            }
            map.put(set.getString(1),set.getString(2));
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Map.Entry<String,String> entry : map.entrySet()){
            driver.navigate().to(entry.getKey());

            try {if (Double.parseDouble(driver.findElement(By.id(entry.getValue())).getAttribute("data-coef")) > 1.25) {
                get("user_id=83948894&message=" + entry.getKey());
                deleteDB(entry.getKey());
            }
            }catch(NoSuchElementException | MalformedURLException ex){
            }
        }
            try {
                Thread.sleep(180000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    //метод отправки сообщения
    private void get(String PARAMS) throws MalformedURLException {

        URL url = new URL("https://api.vk.com/method/" + "messages.send?");
        HttpURLConnection connection;

        try {

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Language", "en,ru");
            connection.setReadTimeout(250);
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            String ACCESS_TOKEN = "&access_token=8ff6ca29394ff1938be2ff8c945c9dd547753dd524db4862f6b56439dfe649fc3135e08b2cf1357f80a4c";
            wr.write(PARAMS+ ACCESS_TOKEN);
            wr.flush();
            wr.close();

            if(!(HttpURLConnection.HTTP_OK == connection.getResponseCode())){
                System.out.println(connection.getResponseCode());//TO_DO CHECK ERRORS
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteDB(String match){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL,USER,IDEN); Statement statement = connection.createStatement()){
            statement.execute("DELETE FROM match WHERE `1x`='{"+match+"}';");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
