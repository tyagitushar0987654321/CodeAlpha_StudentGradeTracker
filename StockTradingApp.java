import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StockTradingApp extends JFrame {
    private JTextField stockNameField;
    private JTextField quantityField;
    private JTextArea displayArea;
    private Map<String, Double> stockPrices;
    private Map<String, Integer> portfolio;

    public StockTradingApp() {
        setTitle("Stock Trading Simulator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        stockPrices = new HashMap<>();
        portfolio = new HashMap<>();

        // Initial stock prices
        stockPrices.put("AAPL", 150.0);
        stockPrices.put("GOOG", 2800.0);
        stockPrices.put("TSLA", 700.0);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Stock Name:"));
        stockNameField = new JTextField();
        inputPanel.add(stockNameField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(e -> buyStock());
        inputPanel.add(buyButton);

        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(e -> sellStock());
        inputPanel.add(sellButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        updateDisplay();
    }

    private void buyStock() {
        try {
            String name = stockNameField.getText().trim().toUpperCase();
            int qty = Integer.parseInt(quantityField.getText().trim());

            if (!name.isEmpty() && qty > 0) {
                portfolio.put(name, portfolio.getOrDefault(name, 0) + qty);
                updateDisplay();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sellStock() {
        try {
            String name = stockNameField.getText().trim().toUpperCase();
            int qty = Integer.parseInt(quantityField.getText().trim());

            if (!name.isEmpty() && qty > 0 && portfolio.getOrDefault(name, 0) >= qty) {
                portfolio.put(name, portfolio.get(name) - qty);

                if (portfolio.get(name) == 0) {
                    portfolio.remove(name);
                }
                updateDisplay();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Stock Prices:\n");
        for (String stock : stockPrices.keySet()) {
            sb.append(stock)
              .append(": $")
              .append(String.format("%.2f", stockPrices.get(stock)))
              .append("\n");
        }

        sb.append("\nYour Portfolio:\n");
        for (String stock : portfolio.keySet()) {
            sb.append(stock)
              .append(": ")
              .append(portfolio.get(stock))
              .append(" shares\n");
        }

        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockTradingApp().setVisible(true);
        });
    }
}