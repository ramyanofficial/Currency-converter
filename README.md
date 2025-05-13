# Currency-converter
The Currency Converter project is a Java-based console application that converts an amount from 
one currency to another using live exchange rates from the ExchangeRate-API. This project is unique 
in that it does not rely on any external JSON libraries to parse API responses; instead, it uses basic 
string operations and regular expressions. 
Key Features: 
• User Interaction: The application prompts the user to input a base currency (e.g., USD), a 
target currency (e.g., INR), and an amount to convert. It validates numeric input for the 
amount and formats the output for clarity. 
• Live API Integration: The application fetches real-time exchange rates from the 
ExchangeRate-API using the user-provided base currency. The API key and base URL are 
preconfigured within the code. 
• No JSON Parsing Library: Instead of using libraries like Gson or Jackson, it manually parses 
the JSON response using regular expressions. This helps keep the project lightweight and 
suitable for basic or learning environments. 
• Error Handling: The application includes basic error handling for network issues, invalid 
input, and malformed or unsuccessful API responses. 
• Formatted Output: Conversion results are displayed with two decimal points using 
DecimalFormat, enhancing readability and user experience. 
How It Works: 
1. User Input: Takes the base and target currency codes and the amount. 
2. API Request: Constructs the API URL based on the base currency and sends an HTTP GET 
request. 
3. Response Processing: Reads the response line-by-line and builds a string. It checks for 
success and extracts the target currency rate using a regex pattern. 
4. Conversion & Display: Multiplies the amount by the exchange rate and displays the result 
along with the rate. 
This project is ideal for beginners learning about HTTP requests, string parsing, and real-time data 
processing in Java without relying on external libraries.
