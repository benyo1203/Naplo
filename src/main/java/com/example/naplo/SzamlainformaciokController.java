package com.example.naplo;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.Context;

public class SzamlainformaciokController {

    @FXML
    private TableView<AccountSummaryData> accountSummaryTable;

    @FXML
    private TableColumn<AccountSummaryData, String> idColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> aliasColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> currencyColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> balanceColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> plColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> marginUsedColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> marginAvailableColumn;
    @FXML
    private TableColumn<AccountSummaryData, String> withdrawalLimitColumn;

    @FXML
    protected void onHelloButtonClick() {
        try {
            Context ctx = new Context("https://api-fxpractice.oanda.com","4fa071e3847710466bf5cd85e0273666-9ea5092d4ecb91984e8408ee110b681d");
            AccountSummary summary = ctx.account.summary(new AccountID("101-004-30465381-001")).getAccount();

            // Create a new AccountSummaryData object
            AccountSummaryData accountSummaryData = new AccountSummaryData(
                    summary.getId(),
                    summary.getAlias(),
                    summary.getCurrency(),
                    summary.getBalance().toString(),
                    summary.getPl().toString(),
                    summary.getMarginUsed().toString(),
                    summary.getMarginAvailable().toString(),
                    summary.getWithdrawalLimit().toString()
            );

            // Add data to table
            accountSummaryTable.getItems().clear();
            accountSummaryTable.getItems().add(accountSummaryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        plColumn.setCellValueFactory(new PropertyValueFactory<>("pl"));
        marginUsedColumn.setCellValueFactory(new PropertyValueFactory<>("marginUsed"));
        marginAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("marginAvailable"));
        withdrawalLimitColumn.setCellValueFactory(new PropertyValueFactory<>("withdrawalLimit"));
    }
}
