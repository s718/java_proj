1. Added more discount rules.
   - off peak hours 
   - day 7 discounts)
   - Rounding is done after a discount price is subtracted with 2 decimal places for each ticket(showing).
 
2. Printing schedule
   - Added printing JSON format.
   - Discounted price is showing instead of ticket price for JSON and flat formats.

3. Fixed bugs and issues.
   - Fixed pom.xml issue
   - Fixed tests issues.

4. Changed where discounted price is calculated and stored.
    - Previously discount was calculated in Movie.java and was called each time to calculated.
    - Since a discount is related to showing like sequence, date of the month, or off peak hour, 
      it makes more sense to move it to Showing class calculated in the begining once and stored.

5. Changed how data is loaded to Theater class.
    - Previously Movie and Showing data were in Theater class constructor.
    - Data should be outside of Theater class and it should be loaded. I introduced data loading methods and was called in Main driver.
    - Input data is in JSON format string for future data coming in JSON format. I thought JSON format would be better than PVS format because there is a relationship between Movie and Showing. 
