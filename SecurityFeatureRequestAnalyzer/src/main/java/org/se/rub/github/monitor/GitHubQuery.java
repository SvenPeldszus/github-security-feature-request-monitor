package org.se.rub.github.monitor;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonParser;

public class GitHubQuery implements Runnable {

	private static final String GITHUB_SEARCH_API = "https://api.github.com/search/";

	private static final Logger LOGGER = LogManager.getLogger(GitHubQuery.class);

	private final long lastQuery;

	private final HttpClient client;

	public GitHubQuery(final long lastQuery) {
		this.lastQuery = lastQuery;
		this.client = HttpClient.newBuilder()
				.version(Version.HTTP_1_1)
				.followRedirects(Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(20))
				.authenticator(Authenticator.getDefault())
				.build();
	}

	/**
	 * GitHub search: language:Java label:security label:enhancement
	 * reason:completed
	 */
	@Override
	public void run() {
		final var uri = URI
				.create(GITHUB_SEARCH_API + "issues?q=label:security+label:enhancement+language:Java+reason:completed");

		final var request = HttpRequest.newBuilder()
				.uri(uri)
				.timeout(Duration.ofMinutes(2))
				.header("Content-Type", "application/json")
				.GET()
				.build();

		try {
			final var response = this.client.send(request, BodyHandlers.ofString());
			final var jsonString = response.body();
			final var json = JsonParser.parseString(jsonString).getAsJsonObject();
			System.out.println("Query Result: " + json);
		} catch (final IOException | InterruptedException e) {
			LOGGER.error(e);
		}
	}

}
