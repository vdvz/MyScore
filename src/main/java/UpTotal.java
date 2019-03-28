import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Наталья on 07.08.2017.
 */
public class UpTotal implements Runnable {
    double mid1,mid2,mid0,kf;
    double kol=0,sum=0,sum2=0,kol2=0,sum3=0,kol3=0;
    String score;
    String URL;
    WebDriver driver1;
    String str1;
    UpTotal(String str, WebDriver driver){
    str1=str;
    URL = "http://t.myscore.ru/#!/match/"+str.substring(4,str.length())+"/h2h";
    driver1= driver;
    run();
    }


    @Override
    public void run() {

        driver1.navigate().to(URL);
        driver1.navigate().refresh();
        try {
            score = driver1.findElement(By.cssSelector("[class^='score']")).getText();
        }catch(NoSuchElementException ex){
        score = "0-0";
        }

        for (WebElement el : driver1.findElements(By.cssSelector("[class^='1']>[class^='score']"))){
            if (!el.getText().equals("0:0")&&el.getText().length() == 3) {
                sum = sum + Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
                kol++;
            }
        }
        mid1 = make(sum,kol);

        for (WebElement el : driver1.findElements(By.cssSelector("[class^='2']>[class^='score']"))){
            if (!el.getText().equals("0:0")&&el.getText().length() == 3) {
                sum2 = sum2 + Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
                kol2++;
            }
        }
        mid2 = make(sum2,kol2);


        for (WebElement el : driver1.findElements(By.cssSelector("[class^='3']>[class^='score']"))){
            if (!el.getText().equals("0:0")&&el.getText().length() == 3) {
                sum3 = sum3 + Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2)));
                kol3++;
            }
        }
        mid0=make(sum3,kol3);

        kf = setkf(mid0, mid1, mid2);
    }

    private static double make(double sum1, double kol1){
        double res;
        res=(sum1-(sum1/kol1)*(5-kol1))/5;
        return res;
    }

    private static double setkf(double mid0, double mid1, double mid2){
        if(mid0>(mid1+mid2)/2){
        return mid0*2/(mid1+mid2);}
        else return (mid1+mid2)/(2*mid0);
    }

}
