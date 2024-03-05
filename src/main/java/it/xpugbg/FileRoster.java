package it.xpugbg;

import javax.mail.Address;

public class FileRoster implements Roster {
    public FileRoster(String fileName) {
    }

    @Override
    public boolean containsOneOf(Address[] from) {
        return false;
    }

    @Override
    public Address[] getAddresses() {
        return new Address[0];
    }
}
