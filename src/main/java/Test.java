import java.util.ArrayList;

/**
 * Created by Наталья on 04.06.2017.
 */
public class Test {

  public static void main(String[] args) {

      ArrayList<String> list = new ArrayList();
//      ArrayList<UpTotal> list2 = new ArrayList<>();
//      ArrayList<String> list3 = new ArrayList<>();
//      File file = new File("geckodriver.exe");
//      final WebDriver driver = new FirefoxDriver();
//      System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
//      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//      driver.navigate().to("http://t.myscore.ru/#!/football/");
//
//      for (WebElement el : driver.findElements(By.cssSelector("[class^='table-main'] [href^='#']"))) {
//          list3.add(el.getAttribute("href"));
//      }
//      for(String str : list3){
//      driver.navigate().to("http://t.myscore.ru/"+str);
//     // driver.navigate().refresh();
//      for (WebElement el1 : driver.findElements(By.cssSelector("[class^=' stage-scheduled']"))) {
//          if (el1.getAttribute("id").startsWith("g")) {
//              list.add(el1.getAttribute("id"));
//          }
//      }

//          driver.navigate().to("http://t.myscore.ru/#!/match/fFR9xhyg/h2h;1");
//          for (WebElement el : driver.findElements(By.cssSelector("[class^='2']>[class^='score']"))) {
//              if (!el.getText().equals("0:0") && el.getText().length() == 3&&!el.getText().contains("-")) {
//                  System.out.println(Integer.parseInt(String.valueOf(el.getText().trim().charAt(0))) + Integer.parseInt(String.valueOf(el.getText().trim().charAt(2))));
//              }
//          }

      String str = "ghubauhbuyhdsa=2189218291";
      System.out.println(str.substring(str.indexOf('=')+1,str.length()));

      }

//
//      for(String str: list){
//
//          UpTotal sc = new UpTotal(str,driver);
//
//              if(sc.kf>1&&sc.kf<1.51) {
//                  list2.add(total(sc));
//              }
//      }
//
//
//    for(UpTotal sc : list2){
//    if(sc!=null){
//        try(FileWriter writer = new FileWriter("C:\\Users\\Administrator\\Desktop\\TEST\\match.txt", false)) {
//            writer.write(sc.str1);
//            writer.append('\n');
//            writer.flush();
//            writer.close();
//        }
//            catch(IOException e) {
//            e.printStackTrace();
//        }
//      }
//    }
//
//
//
//  }
//
//    private static UpTotal total(UpTotal sc1){
//      if(sc1.mid0>2.2){return sc1;}
//        else return null;
//    }
//
//

}
