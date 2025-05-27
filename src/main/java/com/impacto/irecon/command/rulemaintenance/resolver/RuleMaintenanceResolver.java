//package com.impacto.irecon.command.rulemaintenance.resolver;
//
//
//import com.impacto.irecon.command.rulemaintenance.entity.RuleMaintenance;
//import com.impacto.irecon.command.rulemaintenance.service.RuleMaintenanceService;
//import graphql.kickstart.tools.GraphQLMutationResolver;
//import graphql.kickstart.tools.GraphQLQueryResolver;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RuleMaintenanceResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
//
//    private final RuleMaintenanceService ruleMaintenanceService;
//
//    public Iterable<RuleMaintenance> getAllRules() {
//        return ruleMaintenanceService.getAllRules();
//    }
//
//    public RuleMaintenance getRuleById(UUID id) {
//        return ruleMaintenanceService.getRuleById(id);
//    }
//
//    public RuleMaintenance createRule(RuleMaintenance ruleInput) {
//        RuleMaintenance createdRule = ruleMaintenanceService.createRule(ruleInput);
//        if (createdRule == null) {
//            log.info("the data is coming till here throw exception");
//        }
//        return createdRule;
//    }
//
//    public RuleMaintenance updateRule(UUID id, RuleMaintenance ruleInput) {
//        return ruleMaintenanceService.updateRule(id, ruleInput);
//    }
//
//    public Boolean deleteRule(UUID id) {
//        ruleMaintenanceService.deleteRule(id);
//        return true;
//    }
//}