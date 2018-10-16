package org.loggers.beans;

public class LoggersConfiguration {

    private GithubConfiguration github;
    private StackoverflowConfiguration stackoverflow;
    private TwitterConfiguration twitter;

    public LoggersConfiguration() {
        // Default Constructor
    }

    public GithubConfiguration getGithub() {
        return github;
    }

    public void setGithub(GithubConfiguration github) {
        this.github = github;
    }

    public StackoverflowConfiguration getStackoverflow() {
        return stackoverflow;
    }

    public void setStackoverflow(StackoverflowConfiguration stackoverflow) {
        this.stackoverflow = stackoverflow;
    }

    public TwitterConfiguration getTwitter() {
        return twitter;
    }

    public void setTwitter(TwitterConfiguration twitter) {
        this.twitter = twitter;
    }

}
