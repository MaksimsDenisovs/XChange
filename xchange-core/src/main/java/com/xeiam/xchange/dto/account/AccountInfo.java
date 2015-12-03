package com.xeiam.xchange.dto.account;

import com.xeiam.xchange.currency.Currency;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * DTO representing account information
 * </p>
 * <p>
 * Account information is anything particular associated with the user's login
 * </p>
 */
public final class AccountInfo {

  /**
   * The name on the account
   */
  private final String username;

  /**
   * The current fee this account must pay.  Null if there is no such fee.
   */
  private final BigDecimal tradingFee;

  /**
   * The wallets owned by this account
   */
  private final Map<String, Wallet> wallets;


  /**
   * @see #AccountInfo(String,BigDecimal,Collection)
   */
  public AccountInfo(Wallet... wallets) {

    // TODO when refactoring for separate feature interfaces, change this constructor to require at least two wallets
    this(null, (BigDecimal)null, wallets);
  }

  /**
   * @see #AccountInfo(String,BigDecimal,Collection)
   */
  public AccountInfo(Collection<Wallet> wallets) {

    this(null, (BigDecimal)null, wallets);
  }

  /**
   * @see #AccountInfo(String,BigDecimal,Collection)
   */
  public AccountInfo(String username, Wallet... wallets) {

    this(username, null, wallets);
  }

  /**
   * @see #AccountInfo(String,BigDecimal,Collection)
   */
  public AccountInfo(String username, Collection<Wallet> wallets) {

    this(username, null, wallets);
  }

  /**
   * Constructs an {@link AccountInfo}.
   *
   * @param username the user name.
   * @param tradingFee the trading fee.
   * @param wallets the user's wallets
   */
  public AccountInfo(String username, BigDecimal tradingFee, Collection<Wallet> wallets) {

    this.username = username;
    this.tradingFee = tradingFee;

    if (wallets.size() == 0) {
      this.wallets = Collections.emptyMap();
    } else if (wallets.size() == 1) {
      Wallet wallet = wallets.iterator().next();
      this.wallets = Collections.singletonMap(wallet.getId(), wallet);
    } else {
      this.wallets = new HashMap<String,Wallet>();
      for (Wallet wallet : wallets) {
        if (this.wallets.containsKey(wallet.getId()))
          throw new IllegalArgumentException("duplicate wallets passed to AccountInfo");
        this.wallets.put(wallet.getId(), wallet);
      }
    }

  }

  /**
   * @see #AccountInfo(String,BigDecimal,Collection)
   */
  public AccountInfo(String username, BigDecimal tradingFee, Wallet... wallets) {

    this(username, tradingFee, Arrays.asList(wallets));
  }

  /**
   * Gets all wallets in this account
   */
  public Map<String,Wallet> getWallets() {

    return Collections.unmodifiableMap(wallets);
  }

  /**
   * Gets wallet for accounts which don't use multiple wallets with ids
   */
  public Wallet getWallet() {

    if (wallets.size() != 1)
      throw new UnsupportedOperationException(wallets.size() + " wallets in account");

    return getWallet(null);
  }

  /**
   * Gets the wallet with a specific id
   */
  public Wallet getWallet(String id) {

    return wallets.get(id);
  }

  /**
   * @return The user name
   */
  public String getUsername() {

    return username;
  }

  /**
   * Returns the current trading fee
   *
   * @return The trading fee
   */
  public BigDecimal getTradingFee() {

    return tradingFee;
  }

  @Override
  public String toString() {

    return "AccountInfo [username=" + username + ", tradingFee=" + tradingFee + ", wallets=" + wallets + "]";
  }
}
