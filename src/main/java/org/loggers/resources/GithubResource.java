/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.loggers.resources;

import org.loggers.services.GithubService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Objects;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1-SNAPSHOT
 */
@Path("/github")
public class GithubResource {

    private static final GithubService githubService = GithubService.getInstance();

    @GET
    @Path("/")
    public String get() {
        // TODO: Implementation for HTTP GET request
        System.out.println("GET invoked");
        return "Hello from WSO2 MSF4J";
    }

    @GET
    @Path("/user/{username}")
    @Produces("application/json")
    public Response user(@PathParam("username") String username) {
        return Response.ok().entity(githubService.user(username)).build();
    }

    @GET
    @Path("/user/repositories/{username}")
    public Response repositories(@PathParam("username") String username) {
        return Response.ok().entity(githubService.repositories(username)).build();
    }

    @GET
    @Path("/user/commits/{username}")
    @Produces("application/json")
    public Response commits(@PathParam("username") String username) {
        Response.ResponseBuilder entity = null;
        try {
            entity = Response.ok().entity(githubService.commits(username));
        } catch (IOException e) {
            // error should be logged.
        }
        return Objects.requireNonNull(entity).build();
    }


    @GET
    @Path("/user/repositories/forks/{username}")
    public Response forks(@PathParam("username") String username) {
        return Response.ok().entity(githubService.forks(username)).build();
    }

    @POST
    @Path("/")
    public void post() {
        // TODO: Implementation for HTTP POST request
        System.out.println("POST invoked");
    }

    @PUT
    @Path("/")
    public void put() {
        // TODO: Implementation for HTTP PUT request
        System.out.println("PUT invoked");
    }

    @DELETE
    @Path("/")
    public void delete() {
        // TODO: Implementation for HTTP DELETE request
        System.out.println("DELETE invoked");
    }
}
