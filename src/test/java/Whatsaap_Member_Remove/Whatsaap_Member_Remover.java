package Whatsaap_Member_Remove;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class Whatsaap_Member_Remover {
    @Test
    public void Whatsapp_Member_Remover() throws InterruptedException {

        // Disable Notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(230));
        WebDriverWait wait7 = new WebDriverWait(driver, Duration.ofSeconds(7));
        Actions actions = new Actions(driver);

        driver.get("https://web.whatsapp.com/");
        driver.manage().window().maximize();

        String name = JOptionPane.showInputDialog(
                null,                                // No parent component
                "Enter the Contact name:",          // Message
                "Telegram Media Downloader",        // Title
                JOptionPane.INFORMATION_MESSAGE     // Message type
        );

        if (name == null || name.trim().isEmpty()) {
            System.out.println("No name entered. Exiting.");
            driver.quit(); // Quit the driver
            System.exit(0);  // Exit if no name is provided
        }

        // Validate the input and trim unnecessary spaces
        name = name.trim();
        System.out.println("Entered name: " + name);
        System.out.println("Thank you for using this WhatsApp Group Member Remover Tool! Coded by Mayank Jha. \n");

        // Click on Search
        Thread.sleep(2000);
        WebElement Search_Clicked = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"_aoq2 _ai01\"]//*[@class=\"_ah_-\"]")));
        actions.moveToElement(Search_Clicked).click().sendKeys(name).perform();

        // Click on Searched Item
        Thread.sleep(2000);
        WebElement Searched_Clicked = wait7.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class=\"x1n2onr6\"]//div[@class=\"_ak8l\"])[1]")));
        actions.moveToElement(Searched_Clicked).click().perform();

        // Click on Header and check if More option is there to show all contacts
        Thread.sleep(2000);
        WebElement Header_Clicked = wait7.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"_amie\"]")));
        actions.moveToElement(Header_Clicked).click().perform();

        // Wait for 2 second and scroll to the bottom
        try {
            Thread.sleep(2000);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            boolean isAtBottom = false;

            while (!isAtBottom) {
                js.executeScript("window.scrollBy(0, document.body.scrollHeight);");
                Thread.sleep(1000);
                Long scrollHeight = (Long) js.executeScript("return document.body.scrollHeight;");
                Long clientHeight = (Long) js.executeScript("return window.innerHeight;");
                Long scrollY = (Long) js.executeScript("return window.scrollY;");
                isAtBottom = (scrollHeight - scrollY) <= clientHeight;
            }

            System.out.println("Scrolled to the bottom of the page.");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        try {
            // Locate the element by XPath
            WebElement element = driver.findElement(By.xpath("//div[@class='_alzb  xnnlda6 xh8yej3 x8x1vt3 x78zum5 x6s0dn4 x16cd2qt x1z0qo99']"));

            // Check if the element is displayed
            if (element.isDisplayed()) {
                element.click();
                System.out.println("Element clicked successfully.");
            } else {
                System.out.println("Element is not visible.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found.");
        }

        // Get Member Name and Click on Remove
        try {
            List<WebElement> Members_List = wait7.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class=\"x1n2onr6 xyw6214 x78zum5 xdt5ytf x1iyjqo2 x1odjw0f x150wa6m\"]//div[@class=\"x10l6tqk xh8yej3 x1g42fcv\"]//div[@class=\"_ak8q\"]")));

            int removedMembersCount = 0;
            int totalMembers = Members_List.size();

            if (Members_List.size() > 0) {
                System.out.println("Members found: " + Members_List.size());
                for (int i = 0; i < Members_List.size(); i++) {
                    System.out.println("Member: " + (i + 1) + ". " + Members_List.get(i).getText());
                    String Member_Name = Members_List.get(i).getText();

                    // Skip Admin and remove members
                    if (i >= 1) {
                        actions.moveToElement(Members_List.get(i)).contextClick().perform();

                        WebElement Remove_Member = wait7.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label=\"Remove\"]")));
                        actions.moveToElement(Remove_Member).click().perform();

                        WebElement Remove_Member_Last = wait7.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()=\"Remove\"]")));
                        actions.moveToElement(Remove_Member_Last).click().perform();
                        System.out.println(Member_Name + " Removed \n");

                        removedMembersCount++;
                    }

                    Thread.sleep(3000);
                }

                // Show thank you popup with statistics
                JOptionPane.showMessageDialog(null,
                        "Thank you for using the WhatsApp Group Member Remover Tool!\n" +
                                "Total members: " + totalMembers + "\n" +
                                "Removed members: " + removedMembersCount,
                        "Operation Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Only Group Admin is there or No members found.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            //driver.quit();
        }
    }
}