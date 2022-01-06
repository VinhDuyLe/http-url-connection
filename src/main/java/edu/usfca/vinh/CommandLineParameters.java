package edu.usfca.vinh;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class CommandLineParameters {
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        options.addOption("a", "abc", true, "First Parameter");
        options.addOption("x", "xyz", true, "Second Parameter");
        options.addOption("h", "help", false, "Shows this Help");

        try {
            CommandLine commandLine = parser.parse(options, args);

            System.out.println(commandLine.getOptionValue("a"));
            System.out.println(commandLine.getOptionValue("x"));

            if (commandLine.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("CommandLineParameters", options);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
