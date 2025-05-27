package com.impacto.irecon.command.reconciliationdefinition.model;


import com.impacto.irecon.common.enums.SyncType;
import com.impacto.irecon.common.enums.TimeFrequency;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ApiBasedRequest extends CreateReconciliationRequest{

    private ApiSyncData sourceData;
    private ApiSyncData targetData;

    @Data
    public static class ApiSyncData {
        private String apiKey;
        private String apiSecret;
        private String apiUrl;
        private SyncConfiguration syncConfiguration;
    }

    @Data
    public static class SyncConfiguration {
        private LocalDateTime lastSynced;
        private SyncType syncType; // MANUAL or AUTOMATED
        private TimeFrequency frequency; // EVERY_HOUR, DAILY, etc.
        private LocalDateTime startTime;                                                                                                                                                                                                                                                                                                                                                                            
    }

}
