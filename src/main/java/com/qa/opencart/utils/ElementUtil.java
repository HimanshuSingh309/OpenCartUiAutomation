package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.FrameworkExceptions;
import com.qa.opencart.factory.DriverFactoryPage;

import io.qameta.allure.Step;

public class ElementUtil {

	// SRP

	private WebDriver driver;
	private JavaScriptUtils jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtils(driver);
	}
	private void isHighlight(WebElement element) {
		if (Boolean.parseBoolean(DriverFactoryPage.highlight)) {
			jsUtil.flash(element);
		}
	}

	public By getBy(String locatorType, String locatorValue) {
		By by = null;

		switch (locatorType.toLowerCase().trim()) {
		case "id":
			by = By.id(locatorValue);
			break;
		case "name":
			by = By.name(locatorValue);
			break;
		case "class":
			by = By.className(locatorValue);
			break;
		case "xpath":
			by = By.xpath(locatorValue);
			break;
		case "css":
			by = By.cssSelector(locatorValue);
			break;
		case "linktext":
			by = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			by = By.partialLinkText(locatorValue);
			break;
		case "tag":
			by = By.tagName(locatorValue);
			break;

		default:
			System.out.println("wrong locator type is passed..." + locatorType);
			throw new FrameworkExceptions("WRONG LOCATOR TYPE");
		}

		return by;

	}

	// locatorType = "id", locatorValue = "input-email", value = "tom@gmail.com"
	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}
	
	@Step("Entering values {1} to element {0}")
	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}
	
	@Step("Click on the element {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	public String doElementGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}

	public String doGetElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public WebElement getElement(By locator) {
		WebElement element =  driver.findElement(locator);
		isHighlight(element);
		return element;
	}

	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		isHighlight(element);
		return element;
	}

	public List<String> getElementsAttributeList(By locator, String attriName) {
		List<WebElement> eleList = getElements(locator);
		List<String> empAttriList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String elementAttri = e.getAttribute(attriName);
			if (elementAttri.length() != 0) {
				empAttriList.add(elementAttri);

			}
		}
		return empAttriList;
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> empTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String elementText = e.getText();
			if (elementText.length() != 0) {
				empTextList.add(elementText);

			}
		}
		return empTextList;

	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();

	}

	public void Search(By SearchField, By SuggestionsList, String SearchValue, String SuggestionValue)
		throws InterruptedException 
	{
		doSendKeys(SearchField, SearchValue);
		Thread.sleep(3000);
		List<WebElement> suggList = getElements(SuggestionsList);
		System.out.println(suggList.size());

		for (WebElement e : suggList) 
		{
			String suggNames = e.getText();
			System.out.println(suggNames);
			if (suggNames.contains(SuggestionValue)) 
			{
				e.click();
				System.out.println("Got the expected string----> Pass.");
				break;

			}

		}

	}

	public  void getProductDetails(By productDetails) {
		List<WebElement> productScrapper = driver.findElements(productDetails);
		for(int i = 1; i<=productScrapper.size()-1;i++) {
			WebElement text = productScrapper.get(i);
			String itemDetailsText = text.getText();
			System.out.println(itemDetailsText);
			System.out.println("**********************************************");
		}

}
	public  void getProductDetails(By description, By price) {
		List<WebElement> eleDescription = driver.findElements(description);
		List<WebElement> elePrice = driver.findElements(price);
		
		for (int i = 0; i < eleDescription.size() && i < elePrice.size(); i++) {
		    WebElement eledsc = eleDescription.get(i);
		    String textdsc = eledsc.getText();
		    System.out.println("Description: " + textdsc);

		    WebElement eleprice = elePrice.get(i);
		    String textprice = eleprice.getText();
		    System.out.println("Price: " + textprice);
		}
		
	}
	public  void clickOnLinks(By locator, String links) {
		List<WebElement> eleLinks = getElements(locator);
		int linksCount = eleLinks.size();
		System.out.println("Links count is: " + linksCount);
		for(WebElement e : eleLinks) {
			System.out.println(e.getText());
			if(e.getText().equals(links)) {
				e.click();
				break;
			}
		}
		
		

		
	}
	//*********** Select Utils*************
	
			private Select createSelect(By locator) {
				Select select = new Select(getElement(locator));
				return select;
			}
			public  void doSelectbyIndex(By locator, int Index) {
				createSelect(locator).selectByIndex(Index);
			}
			
			public  void doSelectbyVisibleText(By locator, String visibleText) {
				createSelect(locator).selectByVisibleText(visibleText);
			}
			public  void doSelectbyValue(By locator, String value) {
				createSelect(locator).selectByValue(value);
			}
			public void selectDropDownOptions(By locator, String dropdownValue) {
				List<WebElement> optionsList = createSelect(locator).getOptions();
				for(WebElement e : optionsList) {
					String text = e.getText();
					System.out.println(text);
					if(text.equals(dropdownValue)) {
						e.click();
						break;
					}
				}
			}
			public void getDropDownOptionsCount(By locator) {
				List<WebElement> optionsList = createSelect(locator).getOptions();
				int count = optionsList.size();
				System.out.println("Dropdown Options count is: " + count);
				
			}
			
			public  List<String> getDropDownOptions(By locator) {
				List<WebElement> optionsList = createSelect(locator).getOptions();
				List<String> optionsTextList = new ArrayList<String>();
				for(WebElement e : optionsList) {
					String text = e.getText();
					optionsTextList.add(text);
				}
				return optionsTextList;
			}
			public void selectDropDownValue(By locator, String dropdownValue) {
				List<WebElement> dropDownValues = getElements(locator);
				for(WebElement e : dropDownValues) {
					String text = e.getText();
					if(text.equals(dropdownValue)) {
						e.click();
						break;
					}
				}
			}
			
			public  void switchToAnyChildTabWithSpecificUrlAndCloseOthers(String targetURL) {
				 String parentWindowID = driver.getWindowHandle();
			     System.out.println("Parent Window ID" + parentWindowID);
		        // Get all window IDs
		        Set<String> allWindowsID = driver.getWindowHandles();
		        String targetWindowHandle = null;

		        for (String windowHandle : allWindowsID) {
		            driver.switchTo().window(windowHandle);
		           if(!windowHandle.equals(parentWindowID)) {
		            if (driver.getCurrentUrl().equals(targetURL)) {
		                targetWindowHandle = windowHandle;
		                System.out.println(driver.getCurrentUrl());
		            } else {
		            	System.out.println(driver.getCurrentUrl());
		                driver.close(); // Closing other tabs
		            }
		          }
		           else {
		        	   driver.close();
		           }
		        }
		        // Switching to the tab with the target URL
		        driver.switchTo().window(targetWindowHandle);
		        System.out.println("Now switching to the tab containing '" + targetURL + "': " + driver.getCurrentUrl());
		    }
			
//			public  void switchToAnyTabWithSpecificUrlAndCloseOthers(String targetURL) {
//				 String parentWindowID = driver.getWindowHandle();
//			     System.out.println("Parent Window ID" + parentWindowID);
//		        // Get all window IDs
//		        Set<String> allWindowsID = driver.getWindowHandles();
//		        String targetWindowHandle = null;
//
//		        for (String windowHandle : allWindowsID) {
//		            driver.switchTo().window(windowHandle);
//		           if(!windowHandle.equals(parentWindowID)) {
//		            if (driver.getCurrentUrl().equals(targetURL)) {
//		                targetWindowHandle = windowHandle;
//		                System.out.println(driver.getCurrentUrl());
//		            } else {
//		            	System.out.println(driver.getCurrentUrl());
//		                driver.close(); // Closing other tabs
//		            }
//		          }
//		           else {
//		        	   if (driver.getCurrentUrl().equals(targetURL)) {
//			                targetWindowHandle = windowHandle;
//			                System.out.println(driver.getCurrentUrl());
//			            } else {
//			            	System.out.println(driver.getCurrentUrl());
//			                driver.close(); // Closing other tabs
//			            }
//		           }
//		         
//		        }
//		        // Switching to the tab with the target URL
//		        driver.switchTo().window(targetWindowHandle);
//		        System.out.println("Now switching to the tab containing '" + targetURL + "': " + driver.getCurrentUrl());
//		    }
			
			public  void switchToAnyTabWAndCloseOthers(String targetURL) {
				 String parentWindowID = driver.getWindowHandle();
			     System.out.println("Parent Window ID" + parentWindowID);
		        // Get all window IDs
		        Set<String> allWindowsID = driver.getWindowHandles();
		        String targetWindowHandle = null;

		        for (String windowHandle : allWindowsID) {
		            driver.switchTo().window(windowHandle);
		           
		            if (driver.getCurrentUrl().equals(targetURL)) {
		                targetWindowHandle = windowHandle;
		                System.out.println(driver.getCurrentUrl());
		            } else {
		            	System.out.println(driver.getCurrentUrl());
		                driver.close(); // Closing other tabs
		            }
		         
		        }
		        // Switching to the tab with the target URL
		        driver.switchTo().window(targetWindowHandle);
		        System.out.println("Now switching to the tab containing '" + targetURL + "': " + driver.getCurrentUrl());
		    }
			
			public  WebElement retryingElement(By locator, int timeout) {
				
				WebElement element = null;
				int attempts = 0;
				
				while(attempts < timeout) {
					try {
						element = getElement(locator);
						System.out.println("Element found..." + locator + " in attempts " + attempts);
						break;
						
					} catch (NoSuchElementException e) {
						System.out.println("Element not found..." + locator + " in attempts " + attempts);
						try {
							Thread.sleep(500); // default polling time 500 ml seconds
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					attempts++;
					
				}
				if (element == null) {
					System.out.println("Element not found... tried for " + timeout + " times " + " with the interval of " + 500 + " milliseconds ");
					//throw new ele.AutomationException("No such element exception.");
				}
				isHighlight(element);
				return element;
				
				}
				
				public  WebElement retryingElement(By locator, int timeout, int pollingTime) {
					
					
					WebElement element = null;
					int attempts = 0;
					
					while(attempts < timeout) {
						try {
							element = getElement(locator);
							System.out.println("Element found..." + locator + " in attempts " + attempts);
							break;
							
						} catch (NoSuchElementException e) {
							System.out.println("Element not found..." + locator + " in attempts " + attempts);
							try {
								Thread.sleep(pollingTime); // default polling time 500 ml seconds
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						attempts++;
						
					}
					if (element == null) {
						System.out.println("Element not found... tried for " + timeout + " times " + " with the interval of " + 500 + " milliseconds ");
						//throw new ele.AutomationException("No such element exception.");
					}
					isHighlight(element);
					return element;
					
					}
				public boolean isPageLoaded(int timeout) {
					
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
					String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readState === 'complete'")).toString();
					return Boolean.parseBoolean(flag);
				}
				// *****************Actions utils ***************//

				public void doActionsSendKeys(By locator, String value) {
					Actions act = new Actions(driver);
					act.sendKeys(getElement(locator), value).perform();
				}

				public void doActionsClick(By locator) {
					Actions act = new Actions(driver);
					act.click(getElement(locator)).perform();
				}

				public void twoLevelMenuHandle(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
					Actions act = new Actions(driver);
					act.moveToElement(getElement(parentMenuLocator)).build().perform();
					Thread.sleep(1000);
					doClick(childMenuLocator);
				}

				public void fourLevelMenuHandle(By parentMenuLocator, By firstChildMenuLocaor, By secondChildMenuLocaor,
						By thirdChildMenuLocaor) throws InterruptedException {

					Actions act = new Actions(driver);

					doClick(parentMenuLocator);
					Thread.sleep(1000);

					act.moveToElement(getElement(firstChildMenuLocaor)).build().perform();

					Thread.sleep(1000);

					act.moveToElement(getElement(secondChildMenuLocaor)).build().perform();

					Thread.sleep(1000);

					doClick(thirdChildMenuLocaor);
				}

				public void doActionsSendKeysWithPause(By locator, String value) {
					Actions act = new Actions(driver);
					char val[] = value.toCharArray();
					for (char c : val) {
						act.sendKeys(getElement(locator), String.valueOf(c)).pause(500).build().perform();
					}
				}

				// ****************Wait Utils***************//

				/**
				 * An expectation for checking that an element is present on the DOM of a page.
				 * This does not necessarily mean that the element is visible on the page.
				 * 
				 * @param locator
				 * @param timeOut
				 * @return
				 */
				public WebElement waitForPresenceOfElement(By locator, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
					isHighlight(element);
					return element;
				}

				/**
				 * An expectation for checking that an element is present on the DOM of a page.
				 * This does not necessarily mean that the element is visible on the page.
				 * 
				 * @param locator
				 * @param timeOut
				 * @param intervalTime
				 * @return
				 */
				public WebElement waitForPresenceOfElement(By locator, int timeOut, int intervalTime) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
					WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
					isHighlight(element);
					return element;
				}

				/**
				 * An expectation for checking that an element is present on the DOM of a page
				 * and visible. Visibility means that the element is not only displayed but also
				 * has a height and width that is greater than 0.
				 * 
				 * @param locator
				 * @param timeOut
				 * @return
				 */
				@Step("Waiting for element {0} with timeout {1}")
				public WebElement waitForVisibilityOfElement(By locator, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
					isHighlight(element);
					return element;
				}

				public WebElement waitForVisibilityOfElement(By locator, int timeOut, int intervalTime) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
					WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
					isHighlight(element);
					return element;
				}

				public void doClickWithWait(By locator, int timeOut) {
					waitForVisibilityOfElement(locator, timeOut).click();
				}

				public void doSendKeysWithWait(By locator, String value, int timeOut) {
					waitForVisibilityOfElement(locator, timeOut).sendKeys(value);
				}

				/**
				 * An expectation for checking that all elements present on the web page that
				 * match the locator are visible. Visibility means that the elements are not
				 * only displayed but also have a height and width that is greater than 0.
				 * 
				 * @param locator
				 * @param timeOut
				 * @return
				 */
				public List<WebElement> waitForVisibilityOfElements(By locator, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

				}

				/**
				 * An expectation for checking that there is at least one element present on a
				 * web page.
				 * 
				 * @param locator
				 * @param timeOut
				 * @return
				 */
				public List<WebElement> waitForPresenceOfElements(By locator, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				}

				public String waitForTitleContains(String titleFraction, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

					try {
						if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
							return driver.getTitle();
						}
					} catch (TimeoutException e) {
						System.out.println(titleFraction + " title value is not present....");
						e.printStackTrace();
					}
					return null;

				}

				public String waitForTitleIs(String title, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

					try {
						if (wait.until(ExpectedConditions.titleIs(title))) {
							return driver.getTitle();
						}
					} catch (TimeoutException e) {
						System.out.println(title + " title value is not present....");
						e.printStackTrace();
					}
					return null;

				}
				
				@Step("Wait for the URL contains the {0}")
				public String waitForURLContains(String urlFraction, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

					try {
						if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
							return driver.getCurrentUrl();
						}
					} catch (TimeoutException e) {
						System.out.println(urlFraction + " url value is not present....");
						e.printStackTrace();
					}
					return null;

				}

				public String waitForURLToBe(String url, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

					try {
						if (wait.until(ExpectedConditions.urlToBe(url))) {
							return driver.getCurrentUrl();
						}
					} catch (TimeoutException e) {
						System.out.println(url + " url value is not present....");
						e.printStackTrace();
					}
					return null;

				}

				public Alert waitForJSAlert(int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					return wait.until(ExpectedConditions.alertIsPresent());
				}

				public void acceptJSAlert(int timeOut) {
					waitForJSAlert(timeOut).accept();
				}

				public void dismissJSAlert(int timeOut) {
					waitForJSAlert(timeOut).dismiss();
				}

				public String getJsAlertText(int timeOut) {
					return waitForJSAlert(timeOut).getText();
				}

				public void enterValueOnJsAlert(int timeOut, String value) {
					waitForJSAlert(timeOut).sendKeys(value);
				}

				public void waitForFrameByLocator(By frameLocator, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
				}

				public void waitForFrameByIndex(int frameIndex, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
				}

				public void waitForFrameByIDOrName(String IDOrName, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IDOrName));
				}

				public void waitForFrameByElement(WebElement frameElement, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
				}

				public boolean checkNewWindowExist(int timeOut, int expectedNumberOfWindows) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));

					try {
						if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
							return true;
						}
					} catch (TimeoutException e) {
						System.out.println("number of windows are not same....");
					}
					return false;
				}

				/**
				 * An expectation for checking an element is visible and enabled such that you
				 * can click it.
				 * 
				 * @param locator
				 * @param timeOut
				 */
				public void clickElementWhenReady(By locator, int timeOut) {
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
					} catch (TimeoutException e) {
						System.out.println("element is not clickable or enabled...");
					}
				}
				
				
				
				public WebElement waitForElementWithFluentWait(By locator, int timeOut, int intervalTime) {
					Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofSeconds(timeOut))
							.pollingEvery(Duration.ofSeconds(intervalTime))
							.withMessage("--time out is done...element is not found....")
							.ignoring(NoSuchElementException.class)
							.ignoring(ElementNotInteractableException.class);
							

					WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
					isHighlight(element);
					return element;
				}
				
				
				public void waitForFrameWithFluentWait(String frameIDORName, int timeOut, int intervalTime) {
					Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofSeconds(timeOut))
							.pollingEvery(Duration.ofSeconds(intervalTime))
							.withMessage("--time out is done...frame is not found....")
							.ignoring(NoSuchFrameException.class);

					 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDORName));
					 
				}
				
				
				public Alert waitForJSAlertWithFluentWait(int timeOut, int intervalTime) {
					Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
							.withTimeout(Duration.ofSeconds(timeOut))
							.pollingEvery(Duration.ofSeconds(intervalTime))
							.withMessage("--time out is done...alert is not appeared....")
							.ignoring(NoAlertPresentException.class);

					return wait.until(ExpectedConditions.alertIsPresent());
				}
}
