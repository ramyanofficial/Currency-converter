package currency ;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverter {

    private static final String API_KEY = "efeb42129361d1fee9faa99a";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Currency Converter (No JSON Library)");
        System.out.println("-----------------------------------");
        
        System.out.print("Enter base currency code (e.g., USD, EUR): ");
        String fromCurrency = scanner.nextLine().toUpperCase().trim();
        
        System.out.print("Enter target currency code: ");
        String toCurrency = scanner.nextLine().toUpperCase().trim();
        
        double amount = getValidAmount(scanner);
        
        try {
            double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
            if (exchangeRate == -1) {
                System.out.println("Unable to get exchange rate. Please check your currencies and try again.");
                return;
            }
            
            printConversionResult(amount, fromCurrency, toCurrency, exchangeRate);
        } catch (IOException e) {
            System.out.println("Error occurred while fetching exchange rates: " + e.getMessage());
        }
        
        scanner.close();
    }

    private static double getValidAmount(Scanner scanner) {
        while (true) {
            System.out.print("Enter amount to convert: ");
            String amountInput = scanner.nextLine().replaceAll("[^\\d.]", "");
            
            try {
                return Double.parseDouble(amountInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a numeric value (e.g., 20 or 19.99)");
            }
        }
    }

    private static double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String urlStr = BASE_URL + fromCurrency;
        URL url = new URL(urlStr);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod("GET");
        
        int responseCode = request.getResponseCode();
        if (responseCode != 200) {
            return -1;
        }
        
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        
        // Parse response without JSON library
        String responseStr = response.toString();
        
        // Check if request was successful
        if (!responseStr.contains("\"result\":\"success\"")) {
            return -1;
        }
        
        // Extract the exchange rate using regex
        String ratePattern = "\"" + toCurrency + "\":([0-9.]+)";
        Pattern pattern = Pattern.compile(ratePattern);
        Matcher matcher = pattern.matcher(responseStr);
        
        if (matcher.find()) {
            try {
                return Double.parseDouble(matcher.group(1));
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        
        return -1;
    }

    private static void printConversionResult(double amount, String fromCurrency, 
                                           String toCurrency, double exchangeRate) {
        double convertedAmount = amount * exchangeRate;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        System.out.println("\nConversion Result:");
        System.out.println(df.format(amount) + " " + fromCurrency + " = " + 
                         df.format(convertedAmount) + " " + toCurrency);
        System.out.println("Exchange rate: 1 " + fromCurrency + " = " + 
                         df.format(exchangeRate) + " " + toCurrency);
    }
}