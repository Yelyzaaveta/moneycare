package Income;

/*
  @author Orynchuk
  @project moneycare
  @class Income
  @version 1.0.0
  @since 15.09.2025 - 14.25
*/

public class Income {
    private Long id;
    private String source;
    private String currency;
    private String amount;

    public Income() {}

    public Income(Long id, String source, String currency, String amount) {
        this.id = id;
        this.source = source;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
}
