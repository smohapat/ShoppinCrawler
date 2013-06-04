
package textscrapper;
/**
 * File: InvalidInputException.java
 * @author: Sourav Mohapatra
 * Description - Custom exception class to handle invalid command line inputs 
 */
public class InvalidInputException extends Exception{

    /**
     * Constructor
     */
    public InvalidInputException() {
    super();
    }
    @Override
    /**
     * Override toString method so that the exception can be printed
     * 
     */
        public String toString() {
            return "Invalid Input";
        }
}
