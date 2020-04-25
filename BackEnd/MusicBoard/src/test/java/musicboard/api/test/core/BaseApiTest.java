package musicboard.api.test.core;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public abstract class BaseApiTest {

    private final static String BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate;

    protected final static String JOINER = "/";

    @Before
    public final void init() {
        restTemplate = new RestTemplate(getClientHttpRequestFactory());
        clearRepository();
    }
    
    public RestTemplate getRestTemplate() {
		return restTemplate;
	}

    private String getUrl() {
        return BASE_URL + getRequestMapping();
    }
    
    protected final URI createUri() {
    	return(createUri(""));
    }

    protected final URI createUri(final String mapping) {
        URI uri;
        try {
            uri = new URI(getUrl() + JOINER + mapping);
            return uri;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected abstract String getRequestMapping();

    protected abstract void clearRepository();
    
    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() 
    {
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
              
        return clientHttpRequestFactory;
    }
     
    private HttpClient httpClient() 
    {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("admin", "admin"));
 
        return HttpClientBuilder
                    .create()
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build();
    }
}
