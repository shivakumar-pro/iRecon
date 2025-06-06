package com.impacto.irecon.command.reconciliationdefinition.service.impl;

import com.impacto.irecon.command.reconciliationdefinition.dto.ComparisonResponse;
import com.impacto.irecon.command.reconciliationdefinition.dto.ComparisonResult;
import com.impacto.irecon.command.reconciliationdefinition.dto.ComparisonSummary;
import com.impacto.irecon.command.reconciliationdefinition.dto.FieldDifference;
import com.impacto.irecon.command.reconciliationdefinition.dto.FieldMapping;
import com.impacto.irecon.command.reconciliationdefinition.model.ComparisonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataComparisonService {

    public ComparisonResponse compareData(ComparisonRequest request) {
        long startTime = System.currentTimeMillis();
        String comparisonId = "CMP_" + System.currentTimeMillis();

        try {
            // Validate request
            validateRequest(request);

            // Perform comparison
            List<ComparisonResult> results = performComparison(request);

            // Calculate statistics
            ComparisonStats stats = calculateStatistics(results, request.getSourceData().size());

            // Calculate processing time
            long processingTime = System.currentTimeMillis() - startTime;

            return ComparisonResponse.builder()
                    .comparisonId(comparisonId)
                    .totalRecords(request.getSourceData().size())
                    .matchedRecords(stats.getMatched())
                    .mismatchedRecords(stats.getMismatched())
                    .missingInTarget(stats.getMissingInTarget())
                    .missingInSource(stats.getMissingInSource())
                    .results(results)
                    .processedAt(Instant.now().toString())
                    .processingTimeMs(processingTime)
                    .summary(calculateSummary(results, request))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Comparison failed: " + e.getMessage(), e);
        }
    }

    private void validateRequest(ComparisonRequest request) {
        if (request.getSourceData() == null || request.getSourceData().isEmpty()) {
            throw new IllegalArgumentException("Source data cannot be empty");
        }

        if (request.getTargetData() == null || request.getTargetData().isEmpty()) {
            throw new IllegalArgumentException("Target data cannot be empty");
        }

        if (request.getFieldMapping() == null || request.getFieldMapping().isEmpty()) {
            throw new IllegalArgumentException("Field mapping cannot be empty");
        }

        if (request.getComparisonConfig() == null ||
                request.getComparisonConfig().getPrimaryKeyMapping() == null) {
            throw new IllegalArgumentException("Primary key mapping is required");
        }
    }

    private List<ComparisonResult> performComparison(ComparisonRequest request) {
        List<ComparisonResult> results = new ArrayList<>();

        String sourceIdField = request.getComparisonConfig().getPrimaryKeyMapping().getSourceField();
        String targetIdField = request.getComparisonConfig().getPrimaryKeyMapping().getTargetField();

        // Create target data map for faster lookup
        Map<String, Map<String, Object>> targetDataMap = request.getTargetData().stream()
                .collect(Collectors.toMap(
                        record -> String.valueOf(record.get(targetIdField)),
                        record -> record,
                        (existing, replacement) -> existing // Handle duplicates
                ));

        // Compare each source record
        for (Map<String, Object> sourceRecord : request.getSourceData()) {
            String sourceId = String.valueOf(sourceRecord.get(sourceIdField));
            Map<String, Object> targetRecord = targetDataMap.get(sourceId);

            ComparisonResult result = compareRecords(
                    sourceRecord,
                    targetRecord,
                    sourceId,
                    request.getComparisonConfig().getFieldMappings()
            );

            results.add(result);
        }

        // Check for records missing in source (present only in target)
        Set<String> sourceIds = request.getSourceData().stream()
                .map(record -> String.valueOf(record.get(sourceIdField)))
                .collect(Collectors.toSet());

        for (Map<String, Object> targetRecord : request.getTargetData()) {
            String targetId = String.valueOf(targetRecord.get(targetIdField));
            if (!sourceIds.contains(targetId)) {
                results.add(ComparisonResult.builder()
                        .sourceId(null)
                        .targetId(targetId)
                        .status("missing_in_source")
                        .matchedFields(new ArrayList<>())
                        .differences(new ArrayList<>())
                        .build());
            }
        }

        return results;
    }

    private ComparisonResult compareRecords(Map<String, Object> sourceRecord,
                                          Map<String, Object> targetRecord,
                                          String sourceId,
                                          List<FieldMapping> fieldMappings) {
        if (targetRecord == null) {
            return ComparisonResult.builder()
                    .sourceId(sourceId)
                    .targetId(null)
                    .status("missing_in_target")
                    .matchedFields(new ArrayList<>())
                    .differences(new ArrayList<>())
                    .build();
        }

        List<String> matchedFields = new ArrayList<>();
        List<FieldDifference> differences = new ArrayList<>();

        // Compare mapped fields
        for (FieldMapping mapping : fieldMappings) {
            String sourceField = mapping.getSourceField();
            String targetField = mapping.getTargetField();

            Object sourceValue = sourceRecord.get(sourceField);
            Object targetValue = targetRecord.get(targetField);

            boolean isMatch;
//            if (mapping.getReferenceNumberMatch().getStartPosition() != null && mapping.getReferenceNumberMatch().getLength() != null) {
//                isMatch = compareWithPartialMatch(sourceValue, targetValue, mapping.getReferenceNumberMatch().getStartPosition(), mapping.getReferenceNumberMatch().getLength());
//            } else {
//            }
            isMatch = isEqual(sourceValue, targetValue);

            if (isMatch) {
                matchedFields.add(sourceField);
            } else {
                FieldDifference difference = FieldDifference.builder()
                        .field(sourceField)
                        .sourceField(sourceField)
                        .targetField(targetField)
                        .sourceValue(sourceValue)
                        .targetValue(targetValue)
                        .variance(calculateVariance(sourceValue, targetValue))
                        .build();

                differences.add(difference);
            }
        }

        String status = differences.isEmpty() ? "match" : "mismatch";

        return ComparisonResult.builder()
                .sourceId(sourceId)
                .targetId(sourceId) // Assuming same ID after mapping
                .status(status)
                .matchedFields(matchedFields)
                .differences(differences)
                .build();
    }

    private boolean compareWithPartialMatch(Object value1, Object value2, int startPosition, int length) {
        if (value1 == null && value2 == null) return true;
        if (value1 == null || value2 == null) return false;

        String str1 = String.valueOf(value1).trim();
        String str2 = String.valueOf(value2).trim();

        // If target string is shorter than start position, no match
        if (str2.length() <= startPosition) return false;

        // Extract substring from target value
        String targetSubstring = str2.substring(startPosition, 
            Math.min(startPosition + length, str2.length()));

        // Compare source value with extracted substring
        return str1.equals(targetSubstring);
    }

    private boolean isEqual(Object value1, Object value2) {
        if (value1 == null && value2 == null) return true;
        if (value1 == null || value2 == null) return false;

        // Convert to strings for comparison to handle different data types
        String str1 = String.valueOf(value1).trim();
        String str2 = String.valueOf(value2).trim();

        return str1.equals(str2);
    }

    private Object calculateVariance(Object sourceValue, Object targetValue) {
        try {
            if (sourceValue instanceof Number && targetValue instanceof Number) {
                double source = ((Number) sourceValue).doubleValue();
                double target = ((Number) targetValue).doubleValue();
                return source - target;
            }
        } catch (Exception e) {
            // Ignore numeric conversion errors
        }
        return null;
    }

    private ComparisonStats calculateStatistics(List<ComparisonResult> results, int totalSourceRecords) {
        int matched = 0;
        int mismatched = 0;
        int missingInTarget = 0;
        int missingInSource = 0;

        for (ComparisonResult result : results) {
            switch (result.getStatus()) {
                case "match":
                    matched++;
                    break;
                case "mismatch":
                    mismatched++;
                    break;
                case "missing_in_target":
                    missingInTarget++;
                    break;
                case "missing_in_source":
                    missingInSource++;
                    break;
            }
        }

        return new ComparisonStats(matched, mismatched, missingInTarget, missingInSource);
    }

    private ComparisonSummary calculateSummary(List<ComparisonResult> results, ComparisonRequest request) {
        int totalRecords = results.size();
        int accurateRecords = (int) results.stream().filter(r -> "match".equals(r.getStatus())).count();

        double accuracyPercentage = totalRecords > 0 ? (double) accurateRecords / totalRecords * 100 : 0;

        // Calculate most common differences
        Map<String, Long> differenceFrequency = results.stream()
                .flatMap(result -> result.getDifferences().stream())
                .collect(Collectors.groupingBy(FieldDifference::getField, Collectors.counting()));

        List<String> mostCommonDifferences = differenceFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Calculate field accuracy
        Map<String, Double> fieldAccuracy = new HashMap<>();
        for (String field : request.getFieldMapping().keySet()) {
            long fieldMatches = results.stream()
                    .filter(result -> result.getMatchedFields().contains(field))
                    .count();

            double accuracy = totalRecords > 0 ? (double) fieldMatches / totalRecords * 100 : 0;
            fieldAccuracy.put(field, accuracy);
        }

        return ComparisonSummary.builder()
                .accuracyPercentage(accuracyPercentage)
                .mostCommonDifferences(mostCommonDifferences)
                .fieldAccuracy(fieldAccuracy)
                .build();
    }

    // Helper class for statistics
    @Data
    @AllArgsConstructor
    private static class ComparisonStats {
        private int matched;
        private int mismatched;
        private int missingInTarget;
        private int missingInSource;
    }
}