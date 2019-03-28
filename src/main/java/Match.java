import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Match implements Runnable {
    String URL;
    String TOTAL = null;
    private int SCORE;
    private int TIME;
    private WebDriver driver;
    private double sum;
    private double kol;


    Match(String url,WebDriver driver1,int time){
    URL = "http://t.myscore.ru/#!/match/"+url.substring(4,url.length())+"/h2h";
    driver = driver1;
    TIME = time;
    run();
    }

    @Override
    public void run(){
        driver.navigate().to(URL);
        driver.navigate().refresh();
        try {
            WebElement el = driver.findElement(By.cssSelector("[class^='score']"));
            if(el.getText().length()==3){
                SCORE = Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
            }
        }catch(NoSuchElementException ex){
            SCORE = 0;
        }

    for (WebElement el : driver.findElements(By.cssSelector("[class^='1']>[class^='score']"))) {
        if (el.getText().length() == 3&&!el.getText().equals("0:0")&&el.getText().trim().charAt(1)==':') {
            sum = sum + Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
            kol++;
        }
    }

        double mean12 = make(sum, kol);

        sum = 0;
        kol = 0;

       for (WebElement el : driver.findElements(By.cssSelector("[class^='2']>[class^='score']"))){
            if (el.getText().length() == 3&&!el.getText().equals("0:0")&&el.getText().trim().charAt(1)==':') {
                sum = sum + Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
                kol++;
            }
        }
        mean12 = (mean12 +make(sum,kol))/2;

        sum=0;
        kol=0;

        for (WebElement el : driver.findElements(By.cssSelector("[class^='3']>[class^='score']"))){
            if (el.getText().length() == 3&&!el.getText().equals("0:0")&&el.getText().trim().charAt(1)==':') {
                sum = sum + Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
                kol++;
            }
        }
        double mean3 = make(sum, kol);

        double kf;
        if(mean12 > mean3){
        kf = mean12 / mean3;
        }
        else{
        kf = mean3 / mean12;
        }
        double k = (mean3 + mean12) / 2;
        if(k >1.79&& kf >1&& kf <1.54&&TIME<70){
            k =(70* k -(k -0.5)*TIME)/70;
            if(SCORE< k){
                if(0.51< k && k <0.9){TOTAL="Тотал 0.5 больше";}
                else if(0.9< k && k <1.4){TOTAL="Тотал 1 больше";}
                else if(1.4< k && k <1.8){TOTAL="Тотал 1.5 больше";}
                else if(1.8< k){TOTAL="Тотал 2 больше";}
            }


        }

    }

    private static double make(double sum1, double kol1){
        double res;
        res=(sum1-(sum1/kol1)*(5-kol1))/5;
        return res;
    }


}
