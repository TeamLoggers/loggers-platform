package org.loggers.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.loggers.LoggersServer;
import org.loggers.beans.GithubConfiguration;
import org.omg.CORBA.OBJECT_NOT_EXIST;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Github service singleton.
 */
public class GithubService {

    private static final Logger logger = LogManager.getLogger(GithubService.class);
    private static GitHubClient client;
    private static GithubConfiguration configuration;
    private static GithubService instance;

    static {
        client = new GitHubClient();
    }

    private GithubService() {
        configuration = LoggersServer.configuration.getGithub();
        client.setOAuth2Token(configuration.getToken());
    }

    public static GithubService getInstance() {
        if(instance == null) instance = new GithubService();
        return instance;
    }

    public User user(String username) {
        User user = null;
        try {
            UserService userService = new UserService(client);
            user = userService.getUser();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    public List<Repository> repositories(String username) {
        RepositoryService repositoryService = new RepositoryService(client);
        List<Repository> repositories = null;
        try {
            repositories = repositoryService.getRepositories();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return repositories;
    }

    public Map<String, Integer> commits(String username) throws IOException {
        CommitService commitService = new CommitService(client);
        RepositoryService repositoryService = new RepositoryService(client);
        return repositoryService.getRepositories()
                .stream()
                .parallel()
                .filter(repository -> !repository.isFork())
                .collect(Collectors.toMap(Repository::getName, repository -> {
                    int i = 0;
                    try {
                        i = commitService.getCommits(repository).size();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                    return i;
                }));
    }

    /**
     * Returns the count of forks.
     * @param username
     * @return
     */
    public long forks(String username){
        RepositoryService repositoryService = new RepositoryService(client);
        long count = 0;
        try {
            count = repositoryService.getRepositories()
                    .stream()
                    .parallel()
                    .filter(Repository::isFork)
                    .count();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return count;
    }

}
