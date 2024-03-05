package it.xpugbg;

import javax.mail.Address;

public interface Roster {
    boolean containsOneOf(Address[] from);

    Address[] getAddresses();
}
