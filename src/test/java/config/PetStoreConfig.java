package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({"file:src/test/resources/petstoreconfig.properties"})
public interface PetStoreConfig extends Config {
    PetStoreConfig petStoreConfig = ConfigFactory.create(PetStoreConfig.class);

    String baseURI();
}
