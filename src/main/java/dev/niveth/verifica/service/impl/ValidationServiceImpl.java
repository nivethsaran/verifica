package dev.niveth.verifica.service.impl;

import com.google.common.net.InternetDomainName;
import dev.niveth.verifica.constants.Constants;
import dev.niveth.verifica.model.Response;
import dev.niveth.verifica.service.ValidationService;
import dev.niveth.verifica.util.RestClientUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.URISyntaxException;
import java.util.*;


@Service
public class ValidationServiceImpl implements ValidationService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ValidationServiceImpl.class);

    private final RestClientUtil restClientUtil = new RestClientUtil();

    @Value("${verifica.deta.url}")
    private String detaUrl;

    @Value("${verifica.deta.secret-key}")
    private String detaSecretKey;

    @Value("${verifica.gravatar.url}")
    private String gravatarUrl;

    @Override
    public Response verifyAndPopulateResponse(String email) {
        Response response = new Response();
        List<String> errors = new ArrayList<>();
        List<String> mxDomains = new ArrayList<>();
        response.setEmail(email);
        try {
            validateEmailAddress(email);
            response.setValidSyntax(true);
            String domain = email.split("@")[1];
            String username = email.split("@")[0];
            response.setDomain(domain);
            response.setUsername(username);
            mxDomains = lookUpMXRecord(domain, errors);
            response.setDomainStatus(!mxDomains.isEmpty() ? Constants.DOMAIN_STATUS_VALID : Constants.DOMAIN_STATUS_INVALID);
            response.setIsDisposable(disposableDomainVerification(domain, errors, response.isValidSyntax()));
            response.setGravatarUrl(getGravatarUrl(email));
        } catch (AddressException e) {
            errors.add(e.getMessage());
            response.setEmail(email);
            response.setDomain(Constants.NA);
            response.setUsername(Constants.NA);
            response.setValidSyntax(false);
            response.setGravatarUrl(Constants.NA);
            response.setDomainStatus(Constants.NA);
            response.setIsDisposable(Constants.NA);
            LOGGER.error("Address Exception {}", e.getMessage(), e);
        }
        response.setMxRecords(mxDomains);
        response.setError(errors);
        return response;
    }

    @Override
    public Response verifyDomainAndPopulateResponse(String domain) {
        Response response = new Response();
        List<String> errors = new ArrayList<>();
        List<String> mxDomains = new ArrayList<>();
        response.setDomain(domain);
        try {
            validateDomainName(domain);
            response.setValidSyntax(true);
            mxDomains = lookUpMXRecord(domain, errors);
            response.setDomainStatus(!mxDomains.isEmpty() ? Constants.DOMAIN_STATUS_VALID : Constants.DOMAIN_STATUS_INVALID);
            response.setIsDisposable(disposableDomainVerification(domain, errors, response.isValidSyntax()));
        } catch (AddressException e) {
            errors.add(e.getMessage());
            response.setValidSyntax(false);
            response.setDomainStatus(Constants.NA);
            response.setIsDisposable(Constants.NA);
            LOGGER.error("Address Exception {}", e.getMessage(), e);
        }
        response.setMxRecords(mxDomains);
        response.setError(errors);
        return response;
    }

    /**
     * Helper function to perform syntax validation of the email
     *
     * @param email Input by the user
     * @throws AddressException In the scenario of an invalid email, an Address Exception is thrown
     */
    public void validateEmailAddress(String email) throws AddressException {
        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
    }

    /**
     * Helper function to perform syntax validation of the email
     *
     * @param domain Input by the user
     * @throws AddressException In the scenario of an invalid email, an Address Exception is thrown
     */
    @SuppressWarnings("UnstableApiUsage")
    public void validateDomainName(String domain) throws AddressException {
        if(!InternetDomainName.isValid(domain) || !domain.contains("."))
        {
            throw new AddressException("Invalid Domain Name");
        }
    }

    /**
     * This functions validates if an email has a valid gravatar icon and returns the icon url
     *
     * @param email Email input by the use
     * @return Valid Gravatar url
     */
    private String getGravatarUrl(String email) {
        String md5Hex = DigestUtils.md5Hex(email);
        HttpHeaders headers = new HttpHeaders();
        try {
            ResponseEntity<String> gravatarResponse = restClientUtil.getRequestDispatcherForString(gravatarUrl + md5Hex + "?d=404", headers);
            if (gravatarResponse.getStatusCodeValue() == 200) {
                return gravatarUrl + md5Hex;
            } else {
                return Constants.GRAVATAR_NOT_AVAILABLE;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Constants.GRAVATAR_NOT_AVAILABLE;
        }
    }

    /**
     * Uses a list of emails stored in the database to check if the email domain is disposable or not
     *
     * @param domain the domain input by the user
     * @param errors the list that stores the errors
     * @return DISPOSABLE , NON DISPOSABLE, UNKNOWN
     */
    private String disposableDomainVerification(String domain, List<String> errors, Boolean isValidSyntax) {
        try {
            String disposableCheckUrl = getDetaUrl(domain, Constants.DETABASE_DISPOSABLE);
            String whitelistCheckUrl = getDetaUrl(domain, Constants.DETABASE_WHITELIST);
            String reviewListUrl = getDetaUrl("", Constants.DETABASE_REVIEWLIST);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", detaSecretKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> detaResponse = restClientUtil.getRequestDispatcherForString(disposableCheckUrl, headers);

            if (detaResponse.getStatusCodeValue() == 200) {
                return Constants.DISPOSABLE;
            } else if (detaResponse.getStatusCodeValue() == 404) {

                detaResponse = restClientUtil.getRequestDispatcherForString(whitelistCheckUrl, headers);

                if (detaResponse.getStatusCodeValue() == 200) {
                    return Constants.NON_DISPOSABLE;
                } else if (detaResponse.getStatusCodeValue() == 404) {
                    String requestBody = "{\"item\":{\"key\":\"" + domain + "\"}}";
                    LOGGER.info(requestBody);
                    if(Boolean.TRUE.equals(isValidSyntax)) restClientUtil.postRequestDispatcher(reviewListUrl, headers, requestBody);
                    return Constants.UNKNOWN;
                }
            }
        } catch (URISyntaxException e) {
            errors.add("Internal Server Error");
            LOGGER.error("URI Syntax Exception {}", e.getMessage(), e);
        }
        return Constants.NA;
    }

    /**
     * Uses the domain to do a nslookup of all MX records
     *
     * @param domain the domain input by the user
     * @param errors the list that stores the errors
     * @return A list of all domains in the mx records
     */
    private List<String> lookUpMXRecord(String domain, List<String> errors) {
        List<String> mxDomains = new ArrayList<>();
        try {
            Hashtable<String, String> env = new Hashtable<>(); //no sonar
            env.put(Constants.JAVA_NAMING_FACTORY, Constants.DNS_CONTEXT_FACTORY);
            env.put(Constants.JAVA_NAMING_PROVIDER, Constants.JAVA_PROVIDER_URL);
            DirContext context = new InitialDirContext(env);
            Attributes attributes = context.getAttributes(domain, new String[]{Constants.MX});
            Attribute attribute = attributes.get(Constants.MX);
            if (attribute != null) {
                LOGGER.info("MX records:");
                if (attribute.size() == 0) {
                    return mxDomains;
                }
                for (Enumeration<?> values = attribute.getAll(); values.hasMoreElements(); ) {
                    String entry = values.nextElement().toString();
                    LOGGER.info("MX LookUp Entry: {} ", entry);
                    mxDomains.add(entry.split(" ")[1]);
                }
            }
        } catch (NamingException e) {
            LOGGER.error("Naming Exception {}", e.getMessage(), e);
            errors.add(e.getMessage());
        }
        return mxDomains;
    }

    /**
     * Returns the deta url to hit for the Deta HTTP API
     *
     * @param domain   domain of the email input by the user
     * @param baseName Name of the base that is being queried
     * @return A functioning Deta Base Url
     */
    private String getDetaUrl(String domain, String baseName) {
        return domain.equals("") ? detaUrl + baseName + Constants.PATH_SEPARATOR + Constants.DETABASE_PATH_ITEM : detaUrl + baseName + Constants.PATH_SEPARATOR + Constants.DETABASE_PATH_ITEM + Constants.PATH_SEPARATOR + domain;
    }
}
