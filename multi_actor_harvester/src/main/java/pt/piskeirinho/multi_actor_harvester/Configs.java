package pt.piskeirinho.multi_actor_harvester;

import java.util.Arrays;
import java.util.List;

public final class Configs {
	public static final List<String> repositoriesURLs = Arrays.asList(
			"http://arca.igc.gulbenkian.pt/oaiextended/request",
			"http://digituma.uma.pt/oaiextended/request",
			"http://iconline.ipleiria.pt/oaiextended/request",
			"http://repositorio.insa.pt/oaiextended/request",
			"http://repositorio.hff.min-saude.pt/oaiextended/request");
	public static final String METADATA_PREFIX = "oai_dc";
	public static final String SET_SPEC = null;
	public static final int DETAULT_TIMEOUT = 10000;
	public static final int DEFAULT_RETRIES = 3;
}
