package com.ponsun.san;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class HazelcastConfigTest {
    @Test
    public void testLoadHazelcastConfig() throws IOException {
        ClassPathResource resource = new ClassPathResource("hazelcast/network-multicast.yml");
        assert(resource.exists());
    }
}
