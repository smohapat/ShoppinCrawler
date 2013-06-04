/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textscrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * File: TextScrapperImpl.java
 * 
 * @author: Sourav Mohapatra 
 * Description - This class contains methods to crawls
 *          the shopping.com web site and returns results based on a search
 *          query It does so by parsing the source HTML of shopping.com using
 *          JSOUP HTML Parser v1.7.1 library Library Source : http://jsoup.org/
 */
public class TextScrapperImpl {
    /**
     * Base shopping.com url string
     */
	private final String baseUrlString = "http://www.shopping.com";
	/**
	 * Variable represents search parameter
	 */
	private String search;
	/**
	 * Represents the well formed search URL string
	 */
	private String searchUrlString;
	/**
	 * List of products. Represents composition(has-a) relationship 
	 */
	private List<Product> productList;
	/**
	 * Variable represents page number entered by the user
	 */
	private int pageNum;
	/**
	 * Represents total number of results returned by search
	 */
	private String totalNoOfItems;

	/**
	 * Default constructor to initialize member variables
	 */
	public TextScrapperImpl() {

		productList = new ArrayList<Product>();
		pageNum = 1;
		totalNoOfItems = null;
	}
    /**
     * returns the page number of a search
     * @return - Page number of the search
     */
	public int getPageNum() {
		return pageNum;
	}
    /**
     * Sets the page number of a search
     * @param pageNum - page number of the search
     */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * Formats and encodes the search string entered by the user so that it can be used for scraping
	 * 
	 * @param search - Search parameter entered by the user
	 * @throws UnsupportedEncodingException - This exception is raised when there is encoding failure
	 */
	public void setSearch(String search) throws UnsupportedEncodingException {
		this.search = search.replaceAll(" ", "%20");
		this.search = URLEncoder.encode(this.search, "UTF-8");
	}
    /**
     * This method is used to form the search url based on the search parameter entered 
     * by the user. This is achieved by concatenating the base shopping.com url with the
     * formatted search parameter and page number entered by the user
     * @throws MalformedURLException - thrown when the url is malformed
     */
	public void formSearchURL() throws MalformedURLException {

		searchUrlString = baseUrlString + "/" + search + "/products~PG-"
				+ pageNum + "?KW=" + search;

	}
    /**
     * This method is used to iterate through a list of products and print individual products  
     */
	public void printProductDetails() {

		Iterator<Product> itr = productList.iterator();//iterator for list products
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}

	}
    /**
     * This method is used to fetch number of results returned from a search operation
     * It does so by establishing a connection to the shopping.com search url using JSOUP connect method
     * and scraping the results using JSOUP HTML Parser.
     * @throws IOException
     */
	public void printTotalNoOfItems() throws IOException {

		Document doc = Jsoup.connect(searchUrlString).followRedirects(true)
				.execute().parse();//establish connection
		Element noofItems = doc.getElementsByClass("numTotalResults").first();//Locate the number of results in tag
		String[] noofitemsArray = noofItems.text().split(" ");// Extract the result
		totalNoOfItems = noofitemsArray[noofitemsArray.length - 1];
		System.out.println("The search returned " + totalNoOfItems
				+ " number of results");//print result
	}
    /**
     * This method is used to scrape shoppin.com for product name, price, shipping and vendor details
     * based on search input from user. It does so by establishing a connection to the shopping.com 
     * search url using JSOUP connect method and scraping the results using JSOUP HTML Parse. It first identifies
     * grids for individual products and then iterates through the grid elements for details. In case any informations is not found,
     * it sets the message to 'unavailable'
     * @throws IOException
     */
	public void Scrape() throws IOException {

		Document doc = Jsoup.connect(searchUrlString).get();//establish connection
		int index = 1;//set the index of product to 1
		Elements gridItemBtm = doc.getElementsByClass("gridItemBtm");//Identify individual grids for each result
        //Iterate through the grids
		for (Element grid : gridItemBtm) {
			Product product = new Product();//Instantiate a Product object for every set of products
			Element title = doc.getElementById("nameQA" + (index));//Scrape the title of the product
			if (title == null) {
				title = grid.getElementById("nameQA0");
			}
			if (title != null) {
				product.setProductName(title.attr("title"));
			} else {
				product.setProductName("Product Name unavailable");
			}

			Element price = grid.getElementsByClass("productPrice").first();//Scrape the price of the product

			if (price.children().hasClass("toSalePrice") && price != null) {
				product.setProductPrice(price.getElementsByClass("toSalePrice")
						.text().split(" ")[0]);
			} else if (price.children().tagName("a").isEmpty() && price != null) {
				product.setProductPrice(price.text().split(" ")[0]);
			} else if (price != null) {
				product.setProductPrice(price.children().tagName("a").text()
						.split(" ")[0]);
			} else {
				product.setProductPrice("Price Unavailable");
			}

			Element shipping = doc.getElementsByClass("taxShippingArea")//Scrape the shipping details of the product
					.first();
			if (shipping != null) {
				product.setShippingPrice(shipping.children().get(0).text());
			} else {
				product.setShippingPrice("Shipping information unavailable");
			}

			Element merchant = doc.getElementById("merchNameQA" + index);//Scrape the vendor name of the product
			if (merchant != null) {
				product.setVendorName("Vendor : " + merchant.text());
			} else {
				product.setVendorName("Vendor information unavailable");

			}
			index++;
			productList.add(product);//add to the arraylist of products

		}

	}

}
