package pt.piskeirinho.akka.commons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Configs {
	public static final List<String> repositoriesURLs = Arrays.asList("http://repositorio.esepf.pt/oai/request",
			"http://digituma.uma.pt/oaiextended/request", "http://iconline.ipleiria.pt/oaiextended/driver",
			"http://repositorio.insa.pt/oaiextended/request", "http://repositorio.ipsantarem.pt/oaiextended/request");
	public static final String METADATA_PREFIX = "oai_dc";
	public static final String SET_SPEC = null;
	public static final int DETAULT_TIMEOUT = 10000;
	public static final int DEFAULT_RETRIES = 3;

	public static final Map<String, String> REQUEST_HEADERS;

	static {
		REQUEST_HEADERS = new HashMap<String, String>();
		REQUEST_HEADERS.put("Accept", "*/*");
		REQUEST_HEADERS.put("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
	}
	
	public static final int N_THREDS = 8;
	public static final int N_WORKERS = N_THREDS;
}
