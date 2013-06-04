package textscrapper;

import java.io.IOException;

/**
 *Represents the entry point to shopping.com scraper application. 
 *Contains the main method for invocations
 * @author Sourav
 */
public class TextScrapper {

    /**
     * Main method - To scrape shopping.com
     * @param args - Product search parameter, page number of the result
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            TextScrapperImpl impl = new TextScrapperImpl();//Instantiate a TextScrapperImpl object for scraping
            if(args.length<1)
            throw new InvalidInputException();// Throw InvalidInputException if number of params is less than 1
            
            impl.setSearch(args[0]); // set the search parameter
            impl.formSearchURL(); // search url formation
            if (args.length == 1) {
                impl.printTotalNoOfItems();//Print the number of results
            } else if (args.length == 2) {
                impl.setPageNum(Integer.parseInt(args[1]));//set page number
                impl.Scrape();//Scrape the website
                impl.printProductDetails();//Print individual product details
            } 

        } catch (InvalidInputException ex) {
            System.out.println(ex.toString());
        } catch (IOException ex) {
            String exception = ex.toString();
            if (exception.contains("503")) {
                System.err.println("shopping.com service is currently unavailable. Please try again later");
            } else if (exception.contains("504")) {
                System.err.println("shopping.com server gateway timed out. Please try after some time");
            } else {
                System.err.println("Request couldn't be completed for unknown reasons. Please try after some time");
            }

        }
        catch (Exception ex) {
        System.err.println(ex.toString());
        ex.printStackTrace();
        }
    }
}
