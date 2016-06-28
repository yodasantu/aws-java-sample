package com.amazonaws.samples.jms;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

/**
 * Created by yaozhang on 6/28/2016.
 */
public class ExampleConfiguration {

    public static final String DEFAULT_QUEUE_NAME = "SQSJMSClientExampleQueue";

    public static final Region DEFAULT_REGION = Region.getRegion(Regions.US_EAST_1);

    private static String getParameter(String args[], int i) {
        if (i + 1 >= args.length) {
            throw new IllegalArgumentException("Missing parameter for " + args[i]);
        }
        return args[i + 1];
    }

    /**
     * Parse the command line and return the resulting config. If the config parsing fails
     * print the error and the usage message and then call System.exit
     *
     * @param app  the app to use when printing the usage string
     * @param args the command line arguments
     * @return the parsed config
     */
    public static ExampleConfiguration parseConfig(String app, String args[]) {
        try {
            return new ExampleConfiguration(args);
        } catch (IllegalArgumentException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.err.println();
            System.err.println("Usage: " + app + " [--queue <queue>] [--region <region>] [--credentials <credentials>] ");
            System.err.println("  or");
            System.err.println("       " + app + " <spring.xml>");
            System.exit(-1);
            return null;
        }
    }

    private ExampleConfiguration(String args[]) {
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("--queue")) {
                setQueueName(getParameter(args, i));
                i++;
            } else if (arg.equals("--region")) {
                String regionName = getParameter(args, i);
                try {
                    setRegion(Region.getRegion(Regions.fromName(regionName)));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unrecognized region " + regionName);
                }
                i++;
            } else if (arg.equals("--credentials")) {
                String credsFile = getParameter(args, i);
                try {
                    setCredentialsProvider(new PropertiesFileCredentialsProvider(credsFile));
                } catch (AmazonClientException e) {
                    throw new IllegalArgumentException("Error reading credentials from " + credsFile, e);
                }
                i++;
            } else {
                throw new IllegalArgumentException("Unrecognized option " + arg);
            }
        }
    }

    private String queueName = DEFAULT_QUEUE_NAME;
    private Region region = DEFAULT_REGION;
    private AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public AWSCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public void setCredentialsProvider(AWSCredentialsProvider credentialsProvider) {
        // Make sure they're usable first
        credentialsProvider.getCredentials();
        this.credentialsProvider = credentialsProvider;
    }
}
