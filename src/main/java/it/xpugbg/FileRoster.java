package it.xpugbg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class FileRoster implements Roster {
    private ArrayList<Address> addresses = new ArrayList<Address>();

    public FileRoster(String fileName) {
        try {
            File rosterFile = new File(fileName);
            Scanner rosterFileReader = new Scanner(rosterFile);

            while (rosterFileReader.hasNextLine()) {
                String data = rosterFileReader.nextLine();
                try {
                    addresses.add(new InternetAddress(data));
                } catch (AddressException e) {
                    System.err.printf("Error parsing email address: %s\n", e);
                }
            }

            rosterFileReader.close();
        } catch (FileNotFoundException e) {
            System.err.printf("An error occurred: %s\n", e);
        }
    }

    @Override
    public boolean containsOneOf(Address[] from) {
        var fromAddress = Arrays.asList(from);
        return addresses.parallelStream().anyMatch(addressToSearch -> fromAddress.contains(addressToSearch));
    }

    @Override
    public Address[] getAddresses() {
        return addresses.toArray(new Address[addresses.size()]);
    }
}
