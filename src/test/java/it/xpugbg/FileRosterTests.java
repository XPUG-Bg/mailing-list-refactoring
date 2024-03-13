package it.xpugbg;

import static org.junit.jupiter.api.Assertions.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.jupiter.api.Test;

public class FileRosterTests {
  @Test
  public void create_instance() {
    var testRoster = new FileRoster("src/test/resources/addresses.txt");

    assertNotNull(testRoster);
  }

  @Test
  public void create_instance_handle_not_found_file() {
    var testRoster = new FileRoster("src/test/resources/not-found.txt");

    assertNotNull(testRoster);
  }

  @Test
  public void reads_addresses_from_file() {
    var testRoster = new FileRoster("src/test/resources/addresses.txt");

    assertEquals(3, testRoster.getAddresses().length);
  }

  @Test
  public void reads_addresses_from_file_and_skips_invalid_addresses() {
    var testRoster = new FileRoster("src/test/resources/addresses-invalid.txt");

    assertEquals(2, testRoster.getAddresses().length);
  }

  @Test
  public void checks_provided_addresses_isContained() throws AddressException {
    var testRoster = new FileRoster("src/test/resources/addresses.txt");
    var fromAddresses = new InternetAddress[] { new InternetAddress("test@test.com") };

    assertTrue(testRoster.containsOneOf(fromAddresses));
  }

  @Test
  public void checks_provided_addresses_not_isContained() throws AddressException {
    var testRoster = new FileRoster("src/test/resources/addresses.txt");
    var fromAddresses = new InternetAddress[] { new InternetAddress("test+2@test.com") };

    assertFalse(testRoster.containsOneOf(fromAddresses));
  }
}
