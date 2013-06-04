
package textscrapper;

/**
 * This class is used to encapsulate the various details for the products found in shopping.com
 * @author Sourav Mohapatra
 */
public class Product {
    /**
     * Product Name member variable
     */
    private String productName;
    /**
     * Product Price member variable
     */
    private String productPrice;
    /**
     * Shipping Price member variable
     */
    private String shippingPrice;
    /**
     * Vendor Name member variable
     */
    private String vendorName;
    
    /**
     * 
     * @return - Product Name
     */
    public String getProductName() {
        return productName;
    }
    /**
     * 
     * @param productName - set Product Name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * 
     * @return - return product price
     */
    public String getProductPrice() {
        return productPrice;
    }
    /**
     * 
     * @param productPrice - set product price
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
    /**
     * 
     * @return - return shipping details of product
     */
    public String getShippingPrice() {
        return shippingPrice;
    }
    /**
     * 
     * @param shippingPrice - Set shipping details of product
     */

    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
    /**
     * 
     * @return - Returns vendor name of a product
     */
    public String getVendorName() {
        return vendorName;
    }
    /**
     * 
     * @param vendorName -Sets vendor name of a product
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    @Override
    /**
     * Overrides to String method so that individual product objects can be printed
     */
    public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("/******Product Details********/");
    sb.append("\n");
        sb.append("Product Name: ").append(this.productName);
    sb.append("\n");
        sb.append("Product Price: ").append(this.productPrice);
    sb.append("\n");
        sb.append("Shipping: ").append(this.shippingPrice);
    sb.append("\n");
        sb.append("Vendor Name: ").append(this.vendorName);
    sb.append("\n");
    
        return sb.toString();        
    }
    
    
}
