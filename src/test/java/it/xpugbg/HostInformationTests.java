package it.xpugbg;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HostInformationTests {

  @Test
  public void create_instance_with_valid_arguments() {
    // act
    var hostInformation = new HostInformation("pop3Host", "smtpHost", "pop3User", "pop3Password", "smtpUser",
        "smtpPassword");

    // assert
    assertNotNull(hostInformation);
    assertEquals("pop3Host", hostInformation.pop3Host());
    assertEquals("smtpHost", hostInformation.smtpHost());
    assertEquals("pop3User", hostInformation.pop3User());
    assertEquals("pop3Password", hostInformation.pop3Password());
    assertEquals("smtpUser", hostInformation.smtpUser());
    assertEquals("smtpPassword", hostInformation.smtpPassword());
  }
}
