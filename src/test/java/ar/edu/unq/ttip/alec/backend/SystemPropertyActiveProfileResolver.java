package ar.edu.unq.ttip.alec.backend;

import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

public class SystemPropertyActiveProfileResolver implements ActiveProfilesResolver {
    private final DefaultActiveProfilesResolver defaultActiveProfilesResolver = new DefaultActiveProfilesResolver();

    @Override
    public String[] resolve(Class<?> testClass) {
        final String springProfileKey = "spring.profiles.active";

        return System.getProperties().containsKey(springProfileKey)
            ? System.getProperty(springProfileKey).split("\\s*,\\s*")
            : defaultActiveProfilesResolver.resolve(testClass);
    }
}
