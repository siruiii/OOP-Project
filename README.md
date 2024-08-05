# In-store Food Ordering System
CS470 Object-Oriented Programming Project

## Team member
- Coco Li kl3267@nyu.edu
- Sirui Wang sw5546@nyu.edu
- Annabella Lee pl2220@nyu.edu

## UML Diagrams
please see [the wiki](https://github.com/siruiii/OOP-Project/wiki)

## Project Description
In-store food ordering system accepted orders from customers in the store location. Customers are able to pick in store dinning or takeouts based on their needs. Takeouts will add additional fee to the total bill amount at checkout. The menu itself is getting loaded in the system by having a file(.txt) from which the program will read the menu item and store it in the application. Customers are required to select the size of the items on the menu before putting them into the cart, selecting items will increase the total bill amount in the cart. Menu also offers customers the option to search for specific items. Customers can search for specific items using keywords or categories and refine their search with filters for price or rating. After making their selections, they can review their shopping cart, where they have the option to edit or delete items, or reset the entire cart if needed. When ready, customers can proceed to the checkout to complete their purchase. The customers can view their purchase as a summary and choose from several payment options. They can also enter the promo code or coupons for discounts. Once the order is confirmed, the statue of the order and its time taken are shown on the screen and the customers are choose whether to print the receipt or not. After the order is complete, the customers could submit their rating on a 0 to 5 star scale and detailed feedback.

## List of features
### 1. Main page
The first page customers see when they access the system. 
Options: In store dinning or takeouts are the only 2 options on the page
For the simplicity of our program, both of the options will direct you to the same menu, but selecting takeouts will add an additional takeout fee at checkout.
### 2. Menu page
The menu is getting loaded in the system by having a file(.txt) from which the program will read the menu item and store it in the application
Display food and drinks options
Customers are able to selected small/medium/large for both food and drinks
Add to shopping cart: there will be a plus sign for all the items shown on the menu, customers can use it to add the selected item into the shopping cart.
Search button: there will be a sign to direct the customer to the search page.
Shopping cart button: user will be directed to the shopping cart page
### 3. Search Page
Item Search: Users can search for products using keywords or categories.
Product Filter: Users can narrow down search results by filtering options like price or rating.
Search History: Users can view previously searched items.
### 4. Shopping Cart Page
Review Shopping Cart: Users can view a summary of selected items, including quantities, sizes, prices, and total cost.
Edit item: User can modify the quantities or sizes for selected items.
Delete Item: User can remove unwanted items from the shopping cart.
Reset Shopping Cart: Users can remove all items from the shopping cart.
Checkout Button: User will proceed to the payment page.
### 5. Payment Page
Order summary:Show a detailed summary of the items selected (quantities, prices, total cost)
Payment method: Offer multiple payment options such as credit/debit cards, mobile payments, gift cards or cash
Promotion Code/Coupons: Allow customers to apply any available discounts or promotional codes
Confirmation & Receipt: Confirm payment and display an option to print or email the receipt
### 6. Track and rate Order
Order statue: Show real-time updates on the order status, such as received, in-preperation, ready to pick-up, complete
Estimate Time: Provide an estimated time for when the order will be ready or delivered
Rating & Feedback: Allow customers to rate their order and experience using a star rating system and to leave additional comments or suggestions regarding their order or overall experience
