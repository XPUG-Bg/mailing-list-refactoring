package it.xpugbg;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.jvnet.mock_javamail.Mailbox;

public class MailingListServerTests {
  @Test
  public void it_starts() {
    MailingListServer.main(new String[0]);
  }

  @Test
  public void process_should_retrieveMessage_and_forwardMessage()
      throws AddressException, NoSuchProviderException, MessagingException, IOException {
    // Arrange
    var hostInfo = new HostInformation("test.com", "test.com", "list.address", "password", "list.address", "password");
    var listAddress = "list.address@test.com";

    var sourceInbox = Mailbox.get(listAddress);
    var targetInbox = Mailbox.get("user-2@test.com");

    var mockRoster = new Roster() {
      @Override
      public boolean containsOneOf(Address[] from) {
        return true;
      }

      @Override
      public Address[] getAddresses() {
        return new Address[] { targetInbox.getAddress() };
      }
    };

    var mailSession = Session.getDefaultInstance(System.getProperties(), null);

    var testMessage = new MimeMessage(mailSession);
    testMessage.setFrom(new InternetAddress("user-1@test.com"));
    testMessage.setSubject("Test Message");
    testMessage.setText("Some Test Mail Message");

    sourceInbox.add(testMessage);

    var mailStore = mailSession.getStore("pop3");
    mailStore.connect(hostInfo.pop3Host(), -1, hostInfo.pop3User(), hostInfo.pop3Password());
    var mailFolder = mailStore.getDefaultFolder();

    // Act
    MailingListServer.process(hostInfo, listAddress, mockRoster, mailSession, mailStore, mailFolder);

    // Assert
    // should remove original message
    assertEquals(0, sourceInbox.size());

    // should receive forwarded message
    assertEquals(1, targetInbox.size());
    assertEquals("[list] Test Message", targetInbox.get(0).getSubject());
    assertArrayEquals(new Address[] { sourceInbox.getAddress(), targetInbox.getAddress() },
        targetInbox.get(0).getAllRecipients());
  }
}
